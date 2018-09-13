package gov.nci.webanalyticstests.load.appmodule;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.load.AnalyticsTestLoadBase;

public class DynamicListingPage_Test extends AnalyticsTestLoadBase {

	/**
	 * The following page / content types are covered by this test class:
	 * - Disease Dynamic Listing Page
	 * - Intervention Dynamic Listing Page
	 * - Manually-created Dynamic Listing Page
	 */
	
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "DynamicListingPage";
	private final String REGEX_ITEMS_PER_PAGE = "^\\d{1,4}$";
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}
	
	@Test(dataProvider = "DiseaseListingPage", groups = { "Analytics" })
	public void testDiseaseListingPageLoad(String path, String contentType) {
		System.out.println(contentType + " Dynamic Listing page load event: ");
		getDynamicListingLoadBeacon(path);
		
		doCommonClassAssertions(path);
		String total = analyticsPageLoad.getDynamiListingTotal();
		/*
		//Disease Listing Page
		//evar20 -> disease|<type>|treatment|<name>|total results (regex)
		/about-cancer/treatment/clinical-trials/disease/breast-cancer
			disease|breast-cancer|none|none|758
		/about-cancer/treatment/clinical-trials/disease/breast-cancer/treatment
			disease|breast-cancer|treatment|none|465
		/about-cancer/treatment/clinical-trials/disease/breast-cancer/treatment/trastuzumab
			disease|breast-cancer|treatment|trastuzumab|47
		*/
		logger.log(LogStatus.PASS, contentType + " load values are correct.");
	}

	@Test(dataProvider = "InterventionListingPage", groups = { "Analytics" })
	public void testInterventionListingPageLoad(String path, String contentType) {
		System.out.println(contentType + " Dynamic Listing page load event: ");
		getDynamicListingLoadBeacon(path);
			
		doCommonClassAssertions(path);
		String total = analyticsPageLoad.getDynamiListingTotal();
		/*
		//Intervention Listing Page
		//evar20 -> intervention|<intervention type>|??|total results (regex)
		/about-cancer/treatment/clinical-trials/intervention/trastuzumab
			intervention|trastuzumab|none|59
		/about-cancer/treatment/clinical-trials/intervention/trastuzumab/treatment
		 	intervention|trastuzumab|treatment|59
		 */
		logger.log(LogStatus.PASS, contentType + " load values are correct.");
	}
	
	@Test(dataProvider = "ManualListingPage", groups = { "Analytics" })
	public void testManualListingPageLoad(String path, String contentType) {
		System.out.println(contentType + " Dynamic Listing page load event: ");
		getDynamicListingLoadBeacon(path);
			
		doCommonClassAssertions(path);
		String total = analyticsPageLoad.getDynamiListingTotal();
		Assert.assertEquals(beacon.eVars.get(20), "manual parameters|" + total);
		logger.log(LogStatus.PASS, contentType + " load values are correct.");
	}
	
	/******************** Data Providers ********************/
	
	@DataProvider(name = "DiseaseListingPage")
	public Iterator<Object[]> getDiseaseListingPageLoadData() {
		return getFilteredPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME, "ContentType", "Disease");
	}

	@DataProvider(name = "InterventionListingPage")
	public Iterator<Object[]> getInterventionListingPageLoadData() {
		return getFilteredPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME, "ContentType", "Intervention");
	}

	@DataProvider(name = "ManualListingPage")
	public Iterator<Object[]> getManualListingPageLoadData() {
		return getFilteredPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME, "ContentType", "Manual");
	}


	/**
	 * Go to the listing page and retrieve the beacon request object.
	 * @param path
	 */
	private void getDynamicListingLoadBeacon(String path) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			beacon = getBeacon();
		} catch (Exception ex) {
			Assert.fail("Error loading listing page path: " + path);
			ex.printStackTrace();
		}
	}
	
	/**
	 * Shared Assert() calls for DynamicListPage_Test
	 * @param path
	 */
	private void doCommonClassAssertions(String path) {
		doCommonLoadAssertions(beacon, analyticsPageLoad, path);
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Custom");
		Assert.assertTrue(beacon.eVars.get(10).matches(REGEX_ITEMS_PER_PAGE));		
		Assert.assertEquals(beacon.eVars.get(11), "clinicaltrials_custom");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_custom");
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}
	
}