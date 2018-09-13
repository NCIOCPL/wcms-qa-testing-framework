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
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}
	
	@Test(dataProvider = "DiseaseListingPage", groups = { "Analytics" })
	public void testDiseaseListingPageLoad(String path, String contentType) {
		System.out.println(contentType + " Dynamic Listing page load event: ");
		getDynamicListingLoadBeacon(path);
			
		doCommonClassAssertions(beacon, analyticsPageLoad, path);
		//Disease Listing Page
		//evar10 -> results per page (regex)
		//evar20 -> disease|<type>|treatment|<name>|total results (regex)
		///about-cancer/treatment/clinical-trials/disease/breast-cancer
		///about-cancer/treatment/clinical-trials/disease/breast-cancer/treatment
		///about-cancer/treatment/clinical-trials/disease/breast-cancer/treatment/trastuzumab
		logger.log(LogStatus.PASS, contentType + " load values are correct.");
	}

	@Test(dataProvider = "InterventionListingPage", groups = { "Analytics" })
	public void testInterventionListingPageLoad(String path, String contentType) {
		System.out.println(contentType + " Dynamic Listing page load event: ");
		getDynamicListingLoadBeacon(path);
			
		doCommonClassAssertions(beacon, analyticsPageLoad, path);
		//Intervention Listing Page
		//evar10 -> results per page (regex)
		//evar20 -> intervention|<intervention type>|??|total results (regex)
		///about-cancer/treatment/clinical-trials/intervention/trastuzumab
		///about-cancer/treatment/clinical-trials/intervention/trastuzumab/treatment
		logger.log(LogStatus.PASS, contentType + " load values are correct.");
	}
	
	@Test(dataProvider = "ManualListingPage", groups = { "Analytics" })
	public void testManualListingPageLoad(String path, String contentType) {
		System.out.println(contentType + " Dynamic Listing page load event: ");
		getDynamicListingLoadBeacon(path);
			
		doCommonClassAssertions(beacon, analyticsPageLoad, path);
		//Manual Listing Page
		//evar10 -> results per page
		//evar20 -> manual parameters|total results (regex)
		///about-cancer/treatment/clinical-trials/kidney-cancer (edited)		
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
	 * Go to the results page and retrieve the beacon request object.
	 * @param queryParams
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
	 * @param beacon
	 * @param analyticsPageLoad
	 * @param path
	 */
	private void doCommonClassAssertions(Beacon beacon, AnalyticsPageLoad analyticsPageLoad, String path) {
		doCommonLoadAssertions(beacon, analyticsPageLoad, path);
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Custom");
		Assert.assertEquals(beacon.eVars.get(11), "clinicaltrials_custom");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_custom");
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}
	
}