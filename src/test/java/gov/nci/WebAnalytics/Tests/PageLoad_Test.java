package gov.nci.WebAnalytics.Tests;

import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.AnalyticsBase;
import gov.nci.WebAnalytics.PageLoad;

public class PageLoad_Test extends AnalyticsTestBase {

	private PageLoad pageLoad;
	
	@BeforeMethod(groups = { "Analytics" }) 
	private void beforeMethod() {
		pageLoad = new PageLoad(driver);
	}	

	// TODO: refactor has..() methods
	// TODO: fix hasEvent() arg
	/// Load and click events have been captured
	@Test(groups = { "Analytics" }, priority = 1)
	public void testHarAndBeacons() {
		pageLoad.gotoPageTypes();
		harList = getHarUrlList(proxy);
		loadBeacons = pageLoad.getLoadBeacons(harList);
		Assert.assertTrue(harList.size() > 0);
		Assert.assertTrue(loadBeacons.size() > 0);
		logger.log(LogStatus.PASS, "Load events have been captured.");
	}
	
	/// Click event numbers match with their descriptors
	@Test(groups = { "Analytics" }, priority = 2)
	public void testLoadGeneral() {
		pageLoad.doPageLoadActions();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));		
		for(AnalyticsBase beacon : loadBeacons) {
			Assert.assertTrue(beacon.events[0].contains("event1"));
			Assert.assertTrue(beacon.events[1].contains("event47"));
		}		
		logger.log(LogStatus.PASS, "Load event values are correct.");				
	}
	
	/// Home-and-back navigation returns expected values
	@Test(groups = { "Analytics" }, priority = 3)
	public void testHomeAndBack() throws MalformedURLException {
		// For debugging purposes only..
		pageLoad.goHomeAndBack();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));		
		AnalyticsBase firstLoadBeacon = loadBeacons.get(0);
		// Temporary / debugging tests
		Assert.assertTrue(firstLoadBeacon.channel.equals("NCI Homepage") || firstLoadBeacon.channel.contains("Research"));
		Assert.assertFalse(firstLoadBeacon.channel.contains("some other string"));
		Assert.assertTrue(firstLoadBeacon.events[0].contains("1"));
		logger.log(LogStatus.PASS, "Home-and-back nav values are correct.");		
	}
	
	/// Home navigation returns expected values
	@Test(groups = { "Analytics" })
	public void testHomeLoad() {
		pageLoad.gotoHomePage();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 3, "/"));
		Assert.assertTrue(hasProp(loadBeacons, 6, "Comprehensive Cancer Information"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "NCI Homepage"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/"));
		Assert.assertTrue(haseVar(loadBeacons, 44, "NCI Homepage"));		
		logger.log(LogStatus.PASS, "Home page nav values are correct.");
	}
	
	/// CTHP navigation returns expected values
	@Test(groups = { "Analytics" })
	public void testCTHPPatientLoad() {
		pageLoad.gotoCTHPPatient();
		loadBeacons = pageLoad.getLoadBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasEvent(loadBeacons, "event1"));
		Assert.assertTrue(hasEvent(loadBeacons, "event47"));
		Assert.assertTrue(hasProp(loadBeacons, 8, "english"));
		Assert.assertTrue(hasProp(loadBeacons, 6, "Bladder cancer"));
		Assert.assertTrue(hasProp(loadBeacons, 10, "Bladder Cancer—Patient Version - National Cancer Institute"));
		Assert.assertTrue(hasProp(loadBeacons, 44, "Cancer Types Landing Page"));
		Assert.assertTrue(haseVar(loadBeacons, 1, "www.cancer.gov/types/bladder"));
		Assert.assertTrue(haseVar(loadBeacons, 44, "Cancer Types Landing Page"));

	}	
	
	
}
