package gov.nci.webanalyticstests.load.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.load.AnalyticsTestLoadBase;

public class CtsAdvancedResultsPage_Test extends AnalyticsTestLoadBase {

	/**
	 * The CTS Results Page for Advanced search is covered by this test class.
	 */		

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String PARAMS_CANCERTYPE = "?t=C3167&a=&q=&loc=0&tt=&tp=&tid=&in=&lo=&rl=2";
	
	// Test paths
	//	/about-cancer/treatment/clinical-trials/search/r?t=C3211&a=55&q=&loc=2&lcnty=United+States&lst=NY&lcty=&tt=&tp=IV&tid=&in=&lo=&rl=2	CTS Results Page	Advanced	
	
	// TODO: Handle broken common variables
	// TODO: Add custom variables:
	/***
		Advanced:
		prop15 t:a:loc:lcnty:lst:tp
		prop17 c3211|all|all|all|55|none
		prop18 csc|united states|ny|none
		prop19 all|none|none
		prop20 iv|none|none|none
		eVar15 t:a:loc:lcnty:lst:tp
		eVar17 c3211|all|all|all|55|none
		eVar18 csc|united states|ny|none
		eVar19 all|none|none
		eVar20 iv|none|none|none
	 */
	
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
		
	@Test(groups = { "Analytics" })
	public void testCtsAdvancedResultsPageLoad() {
		getAdvResultsLoadBeacon(PARAMS_CANCERTYPE);
		doCommonClassAssertions(beacon, analyticsPageLoad, PATH);			
		Assert.assertEquals(beacon.props.get(15), "t");
		Assert.assertEquals(beacon.props.get(17), "c3167|all|all|all|none|none");
		Assert.assertEquals(beacon.props.get(18), "all");
		Assert.assertEquals(beacon.props.get(19), "all|none|none");
		Assert.assertEquals(beacon.props.get(20), "all|none|none|none");
		logger.log(LogStatus.PASS, " load values are correct.");
	}

	/**
	 * Go to the results page and retrieve the beacon request object.
	 * @param queryParams
	 */
	private void getAdvResultsLoadBeacon(String queryParams) {
		try {
			driver.get(config.goHome() + PATH + queryParams);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println("Advanced " + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			beacon = getBeacon();
		} catch (Exception ex) {
			Assert.fail("Error loading advanced CTS results. ");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Shared assertions for all tests in this class.
	 * @param beacon
	 * @param analyticsPageLoad
	 * @param path
	 */
	private void doCommonClassAssertions(Beacon beacon, AnalyticsPageLoad analyticsPageLoad, String path) {
		doCommonLoadAssertions(beacon, analyticsPageLoad, path);
		Assert.assertEquals(beacon.props.get(11), "clinicaltrials_advanced");
		Assert.assertEquals(beacon.props.get(62), "Clinical Trials: Advanced");
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertEquals(beacon.eVars.get(15), beacon.props.get(15));
		Assert.assertEquals(beacon.eVars.get(17), beacon.props.get(17));
		Assert.assertEquals(beacon.eVars.get(18), beacon.props.get(18));
		Assert.assertEquals(beacon.eVars.get(19), beacon.props.get(19));
		Assert.assertEquals(beacon.eVars.get(20), beacon.props.get(20));
		Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
	}
}