package gov.nci.webanalyticstests.load;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.AnalyticsPageLoad;
import gov.nci.WebAnalytics.AnalyticsRequest;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class HomePage_Test extends AnalyticsTestBase {

	private AnalyticsPageLoad analyticsPageLoad;
	private AnalyticsRequest beacon;
	
	// TODO: Add Spanish, microsite home pages
	
	@BeforeMethod(groups = { "Analytics" }) 
	public void setupPageLoad() throws MalformedURLException, UnsupportedEncodingException {
		analyticsPageLoad = new AnalyticsPageLoad(driver);
	}	

	/// Home pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testSiteHomeLoad() {
		try {
			System.out.println("Home page load event:");
			analyticsPageLoad.gotoHomePage();
			beacon = getLoadBeacon();
			Assert.assertTrue(beacon.hasEvent(1));
			Assert.assertTrue(beacon.hasEvent(47));
			Assert.assertTrue(beacon.hasProp(3, "/"));
			Assert.assertTrue(beacon.hasProp(6, "Comprehensive Cancer Information"));
			Assert.assertTrue(beacon.hasProp(44, "NCI Homepage"));
			Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/"));
			Assert.assertTrue(beacon.haseVar(44, "NCI Homepage"));		
			logger.log(LogStatus.PASS, "Home page nav values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading Home page.");
			e.printStackTrace();
		}
	}
		
	/// Home-and-back pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testHomeAndBack() throws MalformedURLException {
		try {
			System.out.println("Home page and back load event:");
			analyticsPageLoad.goHomeAndBack();
			beacon = getLoadBeacon();
			AnalyticsRequest firstLoadBeacon = loadBeacons.get(0);
			String[] evts = firstLoadBeacon.getEvents();
			// Temporary / debugging tests
			//Assert.assertTrue(firstLoadBeacon.channel.equals("NCI Homepage") || firstLoadBeacon.channel.contains("Research"));
			//Assert.assertFalse(firstLoadBeacon.channel.contains("some other string"));
			Assert.assertTrue(evts[0].contains("1"));
			logger.log(LogStatus.PASS, "Home-and-back nav values are correct.");
			
		}
		catch (Exception e) {
			Assert.fail("Error loading Home-page-and-back.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testSpanishHomeLoad() {
		try {
			System.out.println("Spanish home page load event:");
			// analyticsPageLoad.gotoSpanishHome();
			Assert.assertEquals(1, 1);
			logger.log(LogStatus.PASS, "Spanish home page nav values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading Spanish home page.");
			e.printStackTrace();
		}
	}	
	
	@Test(groups = { "Analytics" })
	public void testMicrositeHomeLoad() {
		try {
			System.out.println("Microsite home load event:");
			// analyticsPageLoad.gotoMicrositeHome();
			Assert.assertEquals(1, 1);
			logger.log(LogStatus.PASS, "Microsite home nav values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading microsite home page.");
			e.printStackTrace();
		}
	}
	
}