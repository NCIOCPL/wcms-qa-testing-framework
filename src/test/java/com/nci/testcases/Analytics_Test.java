package com.nci.testcases;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

import com.nci.Utilities.BrowserManager;
import gov.nci.WebAnalytics.AnalyticsClick;
import gov.nci.WebAnalytics.AnalyticsLoad;
import gov.nci.WebAnalytics.AnalyticsBase;

import com.relevantcodes.extentreports.LogStatus;
import net.lightbody.bmp.proxy.CaptureType;

import org.apache.http.NameValuePair;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Analytics_Test extends AnalyticsTestBase {

	// TODO: clean up loadEvents / clickEvents objects
	// TODO: build a 'beacon params' object or something like that
	// TODO: refactor doBrowserActions()
	// TODO: Work out what we need to fire off on click/resize/other events
	// 		- Do we need to create a new HAR with each call? 
	//		- How do we differentiate between load and click calls?	
	// TODO: get the logger to actually work
	// TODO: Add LinkXxx properties in AnalyticsClickEvents only
	// TODO: Build negative tests - also 
	// TODO: Build test for test	
	AnalyticsLoad loadEvents;
	AnalyticsClick clickEvents;
	List<String> harList = new ArrayList<String>();
	List<AnalyticsLoad> loadBeacons = new ArrayList<AnalyticsLoad>();
	List<AnalyticsClick> clickBeacons = new ArrayList<AnalyticsClick>();
	
	//region setup
	@BeforeClass(groups = { "Analytics" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getPageURL("HomePage");
		System.out.println("PageURL: " + pageURL);
						
		// setupProxy(driver);
		initializeProxy(pageURL);
		
		// Initialize driver and open browser
		driver = BrowserManager.startProxyBrowser(browser, pageURL, proxy);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		

		// Create our load and click analytics objects
		loadEvents = new AnalyticsLoad(driver);
		clickEvents = new AnalyticsClick(driver);
		
		// Add entries to the HAR log
		doBrowserActions();
		harList = AnalyticsBase.getHarUrlList(proxy);
		loadBeacons = AnalyticsLoad.getLoadBeacons(harList);
		clickBeacons = AnalyticsClick.getClickBeaons(harList);		
		System.out.println("Analytics setup done");
	}
	
	/**
	 * Start and configure BrowserMob Proxy for Selenium.<br/>
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @throws RuntimeException
	 */
	private void initializeProxy(String url) throws RuntimeException {

		// Start the proxy
		System.out.println("=== Starting BrowserMobProxy ===");		
	    proxy.start();

	    // Enable more detailed HAR capture, if desired (see CaptureType for the complete list)
	    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

	    // Create a new HAR with a label matching the hostname
	    proxy.newHar(url);	    
		System.out.println("=== Started BrowserMobProxy successfully ===");
	}
	//endregion setup
	
	//region browseractions
	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */
	private void doBrowserActions() throws RuntimeException {
		navigateSite();
		resizeBrowser();
		//doSiteWideSearch();
		//doAdvancedCTSearch();
		//doBasicCTSearch();
		//useDictionary();
		//navigateError();
		//navigateRATs();		
	}
	
	/// Click around pages
	public void navigateSite() {
				
		// Click on a feature card
		clickEvents.clickFeatureCard();
		driver.navigate().back();
		
		// Click on the MegaMenu
		clickEvents.clickMegaMenu();		
		driver.navigate().back();
		
	}

	// Resize browser
	public void resizeBrowser() {
		Dimension small = new Dimension(300, 800);
		Dimension med = new Dimension(700, 800);
		Dimension large = new Dimension(1100, 800);
		Dimension xlarge = new Dimension(1600, 800);
				
		driver.manage().window().setSize(xlarge);
		driver.manage().window().setSize(large);		
		driver.manage().window().setSize(med);
		driver.manage().window().setSize(small);
		driver.manage().window().maximize();
	}
	//endregion browseractions
	
	//region tests
	/// "NCIAnalytics" elements are present in HTML
	@Test(groups = { "Analytics" }, priority = 1)
	public void veriFySAccount() {
		String sAccountBlob = loadEvents.getSitewideSearchWAFunction();
		Assert.assertTrue(sAccountBlob.contains(AnalyticsLoad.NCI_FUNCTIONS_NAME));
		logger.log(LogStatus.PASS, "NCIAnalytics attribute is present on search form.");
	}
	
	/// Load and click events have been captured
	@Test(groups = { "Analytics" }, priority = 1)
	public void verifyHar() {
		Assert.assertTrue(harList.size() > 0);
		Assert.assertTrue(loadBeacons.size() > 0);
		Assert.assertTrue(clickBeacons.size() > 0);
		logger.log(LogStatus.PASS, "Load and click events have been captured.");
	}	

	/// Debugging statement
	@Test(groups = { "Analytics" })
	public void debugHar() {
		System.out.println("=== Start debug testEvents() ===");

		for(String har : harList) {
			System.out.println(har);
		}

		System.out.println("=== End debug testEvents() ===");		
		Assert.assertTrue(1 == 1);
	}
		
	/// Click event numbers match with their descriptors
	@Test(groups = { "Analytics" })
	public void testLoadEvents() {

		for(AnalyticsLoad beacon : loadBeacons) {
			Assert.assertTrue(beacon == beacon);
		}
		
	}
	
	
	/// Click event numbers match with their descriptors
	@Test(groups = { "Analytics" })
	public void testClickEvents() {

		for(String har : harList) {
			Assert.assertTrue(har.contains("nci"));
			logger.log(LogStatus.PASS, "Pass => " + "Verify 'nci' value...");
			
			if(har.contains("pev2=FeatureCardClick")) {
				Assert.assertTrue(har.contains("events=event27"));				
			}

			if(har.contains("pev2=MegaMenuClick")) {
				Assert.assertTrue(har.contains("events=event26"));			
			}
		}
		
	}	
	
	/// Resize events match with their descriptors
	@Test(groups = { "Analytics" })
	public void testResizeEvents() {
		List<String> localHar = harList;
		
		for(String har : localHar) {
			if(har.contains("pev2=ResizedToMobile")) {
				Assert.assertTrue(har.contains("events=event7"));			
			}
			if(har.contains("pev2=ResizedToTablet")) {
				Assert.assertTrue(har.contains("events=event7"));			
			}
			if(har.contains("pev2=ResizedToDesktop")) {
				Assert.assertTrue(har.contains("events=event7"));			
			}
			if(har.contains("pev2=ResizedToExtra wide")) {
				Assert.assertTrue(har.contains("events=event7"));			
			}			
		}
		
	}
	
	/// Temporary method to test beacon object
	@Test(groups = { "Analytics" })
	public void testObject() throws MalformedURLException {
		// For debugging purposes only...
		AnalyticsLoad firstLoadBeacon = loadBeacons.get(0);

		// for each beacon ... logic goes here
		Assert.assertTrue(firstLoadBeacon.channel.equals("NCI Homepage"));
		Assert.assertFalse(firstLoadBeacon.channel.contains("some other string"));
		Assert.assertTrue(firstLoadBeacon.events[0].contains("1"));

	}
	
	/// Temporary method to verify that my new changes are picked up
	@Test(groups = { "Analytics" })
	public void testInt() {
		int j = 1;
		Assert.assertTrue(j + 1 == 2);
	}

	@Test(groups = { "Analytics" })
	public void testString() {
		String K = "potassium";
		Assert.assertEquals(K, "potassium");
	}
	
	//endregion tests
	
}
