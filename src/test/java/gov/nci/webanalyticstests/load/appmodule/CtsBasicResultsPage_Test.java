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

public class CtsBasicResultsPage_Test extends AnalyticsTestLoadBase {

	/**
	 * The following page / content types are covered by this test class:
	 * - CTS Results Page - Basic search
	 */		
	
	// Test paths
	//	/about-cancer/treatment/clinical-trials/search/r	CTS Results Page	Basic
	//	/about-cancer/treatment/clinical-trials/search/r?q=&t=&a=&z=&rl=1	CTS Results Page	Basic
	//	/about-cancer/treatment/clinical-trials/search/r?q=immunotherapy&t=&a=99&z=37914&rl=1	CTS Results Page	Basic

	
	// TODO: Handle broken common variables
	// TODO: Add custom variables:
	/***
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
	
	@DataProvider(name = "CtsBasicResultsPageLoad")
	public Iterator<Object[]> getCtsBasicResultsPageLoadData() {
		return getFilteredPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME, "SearchType", "Basic");
	}
	
}