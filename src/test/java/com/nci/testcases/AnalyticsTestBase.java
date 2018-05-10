package com.nci.testcases;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.nci.Utilities.ConfigReader;
import com.nci.Utilities.ScreenShot;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;

public class AnalyticsTestBase {

	// private static Logger log=
	// LogManager.getLogger(BaseClass.class.getName());
	public static ExtentReports report;
	public static ExtentTest logger;
	public static WebDriver driver;
	public String pageURL;
	ConfigReader config = new ConfigReader();
    BrowserMobProxy proxy = new BrowserMobProxyServer();	

	@BeforeTest(groups = { "Analytics" })
	@Parameters	
	public void beforeTest() {
		// log.info("Starting a new test");
		System.out.println(this.getClass().getSimpleName());
		String fileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());
		String extentReportPath = config.getExtentReportPath();
		System.out.println("Logger Path:" + extentReportPath);
		report = new ExtentReports(extentReportPath + config.getProperty("Environment") + "-" + fileName + ".html");
		System.out.println("Report Path: ");
		report.addSystemInfo("Environment", config.getProperty("Environment"));
	}

	@BeforeClass(groups = { "Analytics" })
	public void beforeClass() {
		logger = report.startTest(this.getClass().getSimpleName());
	}

	@AfterMethod(groups = { "Analytics" })
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = ScreenShot.captureScreenshot(driver, result.getName());
			String image = logger.addScreenCapture(screenshotPath);
			// logger.addScreenCapture("./test-output/ExtentReport/");
			logger.log(LogStatus.FAIL, image + "Fail => " + result.getName());
			driver.get(pageURL);
		}

		if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Skipped => " + result.getName());
			driver.get(pageURL);
		}
	}

	@AfterClass(groups = { "Analytics" })
	public void afterClass() {
		System.out.println("***********Quiting Driver*********");
		driver.quit();
		report.endTest(logger);
	}

	@AfterTest(groups = { "Analytics" })
	public void afterTest() {
		report.flush();
		// report.close();
		// log.info("Test ends here");
	}

}
