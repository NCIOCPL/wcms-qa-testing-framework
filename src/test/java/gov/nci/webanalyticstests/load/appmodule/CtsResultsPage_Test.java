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

public class CtsResultsPage_Test extends AnalyticsTestLoadBase {

	/**
	 * The following page / content types are covered by this test class:
	 * - CTS Results Page - Advanced search
	 * - CTS Results Page - Basic search
	 */		
	
	// TODO: Handle broken common variables
	// TODO: Add custom variables:
	/***
		Advanced:
		prop15 t:a:loc:lcnty:lst:tp
		prop17 c3211|all|all|all|55|none
		prop18 csc|united states|ny|none
		*prop19 all|none|none
		*prop20 iv|none|none|none
		eVar15 t:a:loc:lcnty:lst:tp
		eVar17 c3211|all|all|all|55|none
		eVar18 csc|united states|ny|none
		*eVar19 all|none|none
		*eVar20 iv|none|none|none
		
		Basic: 
		prop15 a:q:loc:z
		prop17 keyword|immunotherapy|99
		prop18 zip|37914|none
		eVar15 a:q:loc:z
		eVar17 keyword|immunotherapy|99
		eVar18 zip|37914|none
	 */
	
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "CTSResultsPage";
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}
	
	/// CTS Advanced Results page loads return expected values
	@Test(dataProvider = "CtsAdvancedResultsPageLoad", groups = { "Analytics" })
	public void testCtsAdvancedResultsPageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println("Advanced " + contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			beacon = getBeacon();
			
			String[] pathNoQuery = path.split("\\?");			
			doCommonLoadAssertions(beacon, analyticsPageLoad, pathNoQuery[0]);
			Assert.assertEquals(beacon.props.get(11), "clinicaltrials_advanced");
			Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Advanced");
			Assert.assertEquals(beacon.eVars.get(11), "clinicaltrials_advanced");
			Assert.assertEquals(beacon.eVars.get(62), "Clinical Trials: Advanced");
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	/// CTS Basic Results page loads return expected values
	@Test(dataProvider = "CtsBasicResultsPageLoad", groups = { "Analytics" })
	public void testCtBasicResultsPageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println("Basic " + contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			beacon = getBeacon();
			
			String[] pathNoQuery = path.split("\\?");			
			doCommonLoadAssertions(beacon, analyticsPageLoad, pathNoQuery[0]);
			Assert.assertEquals(beacon.props.get(11), "clinicaltrials_basic");
			Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Basic");
			Assert.assertEquals(beacon.eVars.get(11), "clinicaltrials_basic");
			Assert.assertEquals(beacon.eVars.get(62), "Clinical Trials: Basic");
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	@DataProvider(name = "CtsAdvancedResultsPageLoad")
	public Iterator<Object[]> getCtsAdvancedResultsPageLoadData() {
		return getFilteredPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME, "SearchType", "Advanced");
	}
	
	@DataProvider(name = "CtsBasicResultsPageLoad")
	public Iterator<Object[]> getCtsBasicResultsPageLoadData() {
		return getFilteredPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME, "SearchType", "Basic");
	}
	
}