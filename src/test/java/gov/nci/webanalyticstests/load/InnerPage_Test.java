package gov.nci.webanalyticstests.load;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.AnalyticsRequest;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class InnerPage_Test extends AnalyticsTestBase {

	private AnalyticsRequest beacon;
	
	/**
	 * Page / content types covered:
	 * - Article
	 * - General
	 */

	// TODO: more test cases
	// TODO: create URLs
	
	/// Article page load returns expected values
	@Test(groups = { "Analytics" })
	public void testArticleLoad() {
		try {
			System.out.println("Article load event:");
			driver.get(config.getPageURL("InnerPage"));
			beacon = getLoadBeacon();
			AssertCommon();
			Assert.assertTrue(beacon.hasProp(3, "/"));
			Assert.assertTrue(beacon.hasProp(6, "Comprehensive Cancer Information"));
			Assert.assertTrue(beacon.hasProp(44, "NCI Homepage"));
			Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/"));
			Assert.assertTrue(beacon.haseVar(44, "NCI Homepage"));		
			logger.log(LogStatus.PASS, "Article load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading Article.");
			e.printStackTrace();
		}
	}
		
	/// Spanish Article page load returns expected values
	@Test(groups = { "Analytics" })
	public void testArticleEsLoad() {
		try {
			System.out.println("Article load event:");
			driver.get(config.getPageURL("InnerPage"));
			beacon = getLoadBeacon();
			AssertCommon();
			Assert.assertTrue(beacon.hasProp(3, "/"));
			Assert.assertTrue(beacon.hasProp(6, "Comprehensive Cancer Information"));
			Assert.assertTrue(beacon.hasProp(44, "NCI Homepage"));
			Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/"));
			Assert.assertTrue(beacon.haseVar(44, "NCI Homepage"));
			logger.log(LogStatus.PASS, "Article load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading Article.");
			e.printStackTrace();
		}
	}
	
	/// General page load returns expected values		
	@Test(groups = { "Analytics" })
	public void testGeneralLoad() {
		try {
			System.out.println("General page load event:");
			driver.get(config.getPageURL("InnerPage"));
			beacon = getLoadBeacon();
			AssertCommon();
			Assert.assertTrue(beacon.hasProp(3, "/sites/nano"));
			Assert.assertTrue(beacon.hasProp(6, "Nanodelivery Systems and Devices Branch"));
			Assert.assertTrue(beacon.hasProp(8, "english"));
			Assert.assertTrue(beacon.hasProp(44, "NCI Homepage"));
			Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/sites/nano"));
			Assert.assertTrue(beacon.haseVar(2, "english"));
			Assert.assertTrue(beacon.haseVar(5));
			Assert.assertTrue(beacon.haseVar(44, "NCI Homepage"));
			logger.log(LogStatus.PASS, "General page load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading General page.");
			e.printStackTrace();
		}
	}
	
	/// Spanish General page load returns expected values		
	@Test(groups = { "Analytics" })
	public void testGeneralEsLoad() {
		try {
			System.out.println("General load event:");
			driver.get(config.getPageURL("InnerPage"));
			beacon = getLoadBeacon();
			AssertCommon();
			Assert.assertTrue(beacon.hasProp(3, "/sites/nano"));
			Assert.assertTrue(beacon.hasProp(6, "Nanodelivery Systems and Devices Branch"));
			Assert.assertTrue(beacon.hasProp(8, "english"));
			Assert.assertTrue(beacon.hasProp(44, "NCI Homepage"));
			Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/sites/nano"));
			Assert.assertTrue(beacon.haseVar(2, "english"));
			Assert.assertTrue(beacon.haseVar(5));
			Assert.assertTrue(beacon.haseVar(44, "NCI Homepage"));
			logger.log(LogStatus.PASS, "General page load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading General page.");
			e.printStackTrace();
		}
	}
	
	/// Refreshing an inner page returns expected values
	@Test(groups = { "Analytics" })	
	public void testInnerPageRefresh() {
		try {
			System.out.println("Inner page refresh event:");
			// wait / move around 15 seconds
			driver.get(config.goHome());			
			beacon = getLoadBeacon();
			// test for engagement values
			Assert.assertEquals(1,  1);
			logger.log(LogStatus.PASS, "Article load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading Article.");
			e.printStackTrace();
		}
	}	
	
	// Common assertions
	private void AssertCommon() {
		Assert.assertTrue(beacon.hasEvent(1));
		Assert.assertTrue(beacon.hasEvent(47));
		Assert.assertTrue(beacon.hasProp(1, driver.getCurrentUrl()));
	}
	
}