package gov.nci.webanalyticstests.load.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.load.AnalyticsTestLoadBase;

public class CtsBasicSearchPage_Test extends AnalyticsTestLoadBase {

	/**
	 * This test class covers Clinical Trial Basic Search pages only
	 */

	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;
	private final String PATH = "/about-cancer/treatment/clinical-trials/search";
	private final String CTS_CONTENT_TYPE = "Clinical Trials: Basic";
	
	/// CTS Basic Search page loads return expected values
	@Test(groups = { "Analytics" })
	public void testCTSBasicSearchPageLoad() {
		try {
			driver.get(config.goHome() + PATH);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println(CTS_CONTENT_TYPE + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			beacon = getBeacon();
			DoCommonLoadAssertions(beacon, analyticsPageLoad, PATH);
			Assert.assertEquals(beacon.props.get(62), CTS_CONTENT_TYPE);
			Assert.assertEquals(beacon.eVars.get(62), CTS_CONTENT_TYPE);
			logger.log(LogStatus.PASS, CTS_CONTENT_TYPE + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + CTS_CONTENT_TYPE);
			e.printStackTrace();
		}
	}
	
}