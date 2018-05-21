package gov.nci.WebAnalytics.Tests;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nci.Utilities.BrowserManager;
import com.nci.Utilities.ConfigReader;
import com.nci.Utilities.ScreenShot;
import com.nci.testcases.BaseClass;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import gov.nci.WebAnalytics.AnalyticsBase;
import gov.nci.WebAnalytics.AnalyticsClick;
import gov.nci.WebAnalytics.AnalyticsLoad;
import gov.nci.WebAnalytics.MegaMenu;
import gov.nci.WebAnalytics.PageLoad;
import gov.nci.WebAnalytics.Resize;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.proxy.CaptureType;

public class MegaMenu_Test extends AnalyticsTest {
	
	// Analytics objects
	public MegaMenu megaMenu;

	/// Load and click events have been captured
	@Test(groups = { "Analytics" })
	public void verifyHar() {
		megaMenu = new MegaMenu(driver);		
		megaMenu.doMegaMenuActions();
		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsLoad> loadBeacons = AnalyticsLoad.getLoadBeacons(harList);
		List<AnalyticsClick> clickBeacons = AnalyticsClick.getClickBeacons(harList);		
				
		Assert.assertTrue(harList.size() > 0);
		Assert.assertTrue(loadBeacons.size() > 0);
		Assert.assertTrue(clickBeacons.size() > 0);
		
		System.out.println("=== Start debug testEvents() ===");
		for(String har : harList) {
			System.out.println(har);
		}
		System.out.println("=== End debug testEvents() ===");				
		
		logger.log(LogStatus.PASS, "Load and click events have been captured.");				
	}	
	
	/// Click event numbers match with their descriptors
	@Test(groups = { "Analytics" })
	public void testClickEvents() {
		megaMenu = new MegaMenu(driver);		
		megaMenu.doMegaMenuActions();

		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsClick> clickBeacons = AnalyticsClick.getClickBeacons(harList);
		
		for(AnalyticsClick beacon : clickBeacons) {
			if(beacon.linkName == "FeatureCardClick") {
				Assert.assertTrue(beacon.events[0].contains("event27"));
			}
			if(beacon.linkName == "MegaMenuClick") {
				Assert.assertTrue(beacon.events[0].contains("event27"));
			}
		}
		
		logger.log(LogStatus.PASS, "Click event values are correct.");		
	}
	
}
