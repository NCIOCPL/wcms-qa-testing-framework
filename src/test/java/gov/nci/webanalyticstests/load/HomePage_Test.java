package gov.nci.webanalyticstests.load;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gov.nci.Utilities.ExcelManager;
import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;

// TODO: add other values, heir, channel, suite
// TODO: verify has() logic in AnalyticsRequest
// TODO: tests for engagement & event47
// TODO: regexes for dynamic values
// TODO: common asserts
public class HomePage_Test extends AnalyticsTestLoadBase {	
	
	/**
	 * The following page / content types are covered by this test class:
	 * - Site home (English)
	 * - Spanish homepage
	 * - Microsite homepage
	 */
	
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "HomePage";
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}
	
	/// Homepage load events return expected values
	@Test(dataProvider = "HomePageLoad", groups = { "Analytics" })
	public void testHomePageLoad(String path, String contentType) {
		try {
			// Go to our load URL
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			beacon = getBeacon();
			System.out.println(contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			
			// Do assertions
			Assert.assertTrue(beacon.hasEvent(1));
			Assert.assertTrue(beacon.hasEvent(47));
			Assert.assertTrue(beacon.hasProp(1, driver.getCurrentUrl()));
			Assert.assertTrue(beacon.hasProp(3, path));
			Assert.assertTrue(beacon.hasProp(6, analyticsPageLoad.getMetaTitle()));
			Assert.assertTrue(beacon.hasProp(8, analyticsPageLoad.getLanguageName()));
			Assert.assertTrue(beacon.hasProp(10, analyticsPageLoad.getPageTitle()));
			Assert.assertTrue(beacon.hasProp(44, analyticsPageLoad.getMetaIsPartOf()));
			Assert.assertTrue(beacon.haseVar(2, analyticsPageLoad.getLanguageName()));
			Assert.assertTrue(beacon.haseVar(44, analyticsPageLoad.getMetaIsPartOf()));
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}
	
	/// Homepage refresh load event returns expected values
	@Test(groups = { "Analytics" })
	public void testRefresh() throws MalformedURLException {
		try {
			System.out.println("Home page and back load event:");
			driver.get(config.goHome());
			driver.navigate().refresh();
			beacon = getBeacon();
			Assert.assertTrue(beacon.hasProp(3, "/"));
			Assert.assertTrue(beacon.hasProp(6, "Comprehensive Cancer Information"));
			Assert.assertTrue(beacon.hasProp(44, "NCI Homepage"));
			Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/"));
			Assert.assertTrue(beacon.haseVar(44, "NCI Homepage"));
			logger.log(LogStatus.PASS, "Home-and-back nav values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading Home-page-and-back.");
			e.printStackTrace();
		}
	}	
	
	/// Home-and-back pageload returns expected values
	@Test(groups = { "Analytics" })
	public void testHomeAndBack() throws MalformedURLException {
		try {
			System.out.println("Home page and back load event:");
			driver.get(config.goHome());
			driver.get(config.getPageURL("SpanishPage"));
			driver.get(config.goHome());
			beacon = getBeacon();
			Assert.assertTrue(beacon.hasProp(3, "/"));
			Assert.assertTrue(beacon.hasProp(6, "Comprehensive Cancer Information"));
			Assert.assertTrue(beacon.hasProp(44, "NCI Homepage"));
			Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/"));
			Assert.assertTrue(beacon.haseVar(44, "NCI Homepage"));
			logger.log(LogStatus.PASS, "Home-and-back nav values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading Home-page-and-back.");
			e.printStackTrace();
		}
	}
		
	@DataProvider(name = "HomePageLoad")
	public Iterator<Object[]> getHomePageLoadData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String path = excelReader.getCellData(TESTDATA_SHEET_NAME, "Path", rowNum);
			String contentType = excelReader.getCellData(TESTDATA_SHEET_NAME, "ContentType", rowNum);
			Object ob[] = { path, contentType };
			myObjects.add(ob);
		}		
		return myObjects.iterator();
	}
	
}