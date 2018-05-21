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

public class Sandbox_Test extends AnalyticsTest {

	/// Temporary methods to verify that my new changes are picked up
	@Test(groups = { "Analytics" })
	public void testString() {
		String str = "sandboxy";
		Assert.assertNotEquals("sandbox", str);
	}	
	
	@Test(groups = { "Analytics" })
	public void testInt() {
		int j = 1;
		Assert.assertTrue(j + 1 == 2);
	}

	@Test(groups = { "Analytics" })
	public void testInt2() {
		int k = 1;
		Assert.assertNotEquals(k,  2);
	}	
	
	@Test(groups = { "Analytics" })
	public void testRsString() {
		String str = "clickEvent";
		Assert.assertEquals("clickEvent", str);
	}	

	@Test(groups = { "Analytics" })
	public void testBool() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
	}
	
}
