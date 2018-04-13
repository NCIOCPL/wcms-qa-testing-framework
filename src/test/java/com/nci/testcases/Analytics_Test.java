package com.nci.testcases;

import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;

import com.nci.Utilities.BrowserManager;
import com.relevantcodes.extentreports.LogStatus;

import gov.nci.WebAnalytics.AnalyticsBase;
import gov.nci.WebAnalytics.AnalyticsClickEvents;
import gov.nci.WebAnalytics.AnalyticsLoadEvents;

public class Analytics_Test extends BaseClass {

	// WebDriver driver;
	AnalyticsLoadEvents analyticsLoad;
	AnalyticsClickEvents analyticsClick;
	
	// New instance of BrowserMob proxy
    BrowserMobProxy proxy = new BrowserMobProxyServer();
	
	// HAR data object
	/** A HAR (HTTP Archive) is a file format that can be used by HTTP monitoring 
	 * tools to export collected data. 
	 * BrowserMob Proxy allows us to manipulate HTTP requests and responses, capture 
	 * HTTP content, and export performance data as a HAR file. */
	Har har;

	@BeforeClass(groups = { "Smoke" })
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
		analyticsLoad = new AnalyticsLoadEvents(driver);
		analyticsClick = new AnalyticsClickEvents(driver);
				
		getHarObject();
		System.out.println("Analytics setup done");
	}

	/**
	 * Configure BrowserMob Proxy for Selenium.<br>
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @throws RuntimeException
	 */
	private void getHarObject() throws RuntimeException {
		
	    // Get the HAR data and print to console for now
	    // TODO: Break this out into actual tests
	    // TODO: Start tracking click events
	    har = proxy.getHar();
	    List<HarEntry> entries = har.getLog().getEntries();
    	System.out.println("Entry count (debug): " + entries.size());
	    for (HarEntry entry : entries) {
	    	if(entry.getRequest().getUrl().contains(AnalyticsBase.TRACKING_SERVER))
			{
	    		String result = entry.getRequest().getUrl();
	    		try {
					result = URLDecoder.decode(result, "UTF-8");
				} catch (Exception e) {
					result = "bleah";
				} 
				System.out.println(result);
			}
	    }  
	    
		System.out.println("BMP proxy setup done");
	}
	
	/**
	 * Start and configure BrowserMob Proxy for Selenium.<br/>
	 * Modified from https://github.com/lightbody/browsermob-proxy#using-with-selenium
	 * @throws RuntimeException
	 */
	// public void setupProxy(WebDriver driver) throws RuntimeException {	 
	private void initializeProxy(String url) throws RuntimeException {
	    // Start the proxy
	    proxy.start();

	    // Enable more detailed HAR capture, if desired (see CaptureType for the complete list)
	    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

	    // Create a new HAR with a label matching the hostname
	    proxy.newHar(url);	   
	}
	
	
	/******** Begin testing section ********/	
	
	/// Check for NCIAnalytics in HTML
	@Test(groups = { "Smoke" }, priority = 1)
	public void veriFySAccount() {
		String sAccountBlob = analyticsLoad.getSitewideSearchWAFunction();
		//System.out.println("onsubmit attribute: " + sAccountBlob);
		Assert.assertTrue(sAccountBlob.contains(AnalyticsLoadEvents.NCI_FUNCTIONS_NAME));
		logger.log(LogStatus.PASS, "NCIAnalytics attribute is present on search form.");
	}

}
