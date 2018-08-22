package gov.nci.webanalyticstests.load;

import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.Utilities.ExcelManager;
import gov.nci.WebAnalytics.AnalyticsRequest;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class InnerPage_Test extends AnalyticsTestBase {

	/**
	 * The following page / content types are covered by this test class:
	 * - Article (English and Spanish)
	 * - General (English and Spanish)
	 */		
	
	// TODO: more test cases
	// TODO: create URLs
	private AnalyticsRequest beacon;
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "InnerPage";
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}
		
	/// Inner page loads return expected values
	@Test(dataProvider = "InnerPageLoad", groups = { "Analytics" })
	public void testInnerPageLoad(String path, String title, String contentType, String language ) {
		try {
			System.out.println(contentType + " page load (" + language + "):");
			driver.get(config.goHome() + path);
			beacon = getLoadBeacon();
			Assert.assertTrue(beacon.hasEvent(1));
			Assert.assertTrue(beacon.hasEvent(47));			
			Assert.assertTrue(beacon.hasProp(1, driver.getCurrentUrl()));			
			Assert.assertTrue(beacon.hasProp(3, path)); // arg
			Assert.assertTrue(beacon.hasProp(6, title)); // title - get from og:title
			Assert.assertTrue(beacon.hasProp(8, language)); 
			//Assert.assertTrue(beacon.hasProp(44, "NCI Homepage"));
			//Assert.assertTrue(beacon.haseVar(1, "www.cancer.gov/"));
			Assert.assertTrue(beacon.haseVar(2, language)); 
			//Assert.assertTrue(beacon.haseVar(44, "NCI Homepage"));
			/***
			 * Other values needed:
			 *  Example from General=https://www-qa.cancer.gov/about-nci/visit:
				channel: about NCI
				suites: ncidevelopment, ncienterprise-dev
				prop10: Visitor Information - National Cancer Institute
				prop25: 01/01/1980
				prop26: 2018|8|22|15
				prop29: 3:02 PM|Wednesday
				prop42: Normal
				prop44: Visitor Information
				prop48: 12pct|12pct|3796px|/
				prop61: www.cancer.gov/
				prop64: 12|0
				prop65: 3
				eVar1: www.cancer.gov/about-nci/visit
				eVar5: Extra wide
				eVar44: Visitor Information
				Hierarchy: 1 www-qa.cancer.gov|about-nci|visit
			 */
			logger.log(LogStatus.PASS, "Article load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading Article.");
			e.printStackTrace();
		}
	}

	@DataProvider(name = "InnerPageLoad")
	public Iterator<Object[]> getInnerPageLoadData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String path = excelReader.getCellData(TESTDATA_SHEET_NAME, "Path", rowNum);
			String title = excelReader.getCellData(TESTDATA_SHEET_NAME, "Title", rowNum);
			String contentType = excelReader.getCellData(TESTDATA_SHEET_NAME, "ContentType", rowNum);
			String language = excelReader.getCellData(TESTDATA_SHEET_NAME, "Language", rowNum);
			Object ob[] = { path, title, contentType, language };
			myObjects.add(ob);
		}		
		return myObjects.iterator();
	}	
	
}