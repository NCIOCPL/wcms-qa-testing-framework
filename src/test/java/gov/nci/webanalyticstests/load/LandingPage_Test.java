package gov.nci.webanalyticstests.load;

import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.Utilities.ExcelManager;
import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;

public class LandingPage_Test extends AnalyticsTestLoadBase {

	/**
	 * The following page types / content are covered by this test class:
	 * - Topic (English and Spanish)
	 * - News and Events page (English and Spanish)
	 */
	
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "LandingPage";
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}
	
	/// Landing page loads return expected values
	@Test(dataProvider = "LandingPageLoad", groups = { "Analytics" })
	public void testHomePageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			beacon = getBeacon();
			System.out.println(contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
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

	@DataProvider(name = "LandingPageLoad")
	public Iterator<Object[]> getLandingPageLoadData() {
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