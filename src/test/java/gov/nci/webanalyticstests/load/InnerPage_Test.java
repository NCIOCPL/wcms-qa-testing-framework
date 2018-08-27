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
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class InnerPage_Test extends AnalyticsTestLoadBase {

	/**
	 * The following page / content types are covered by this test class:
	 * - Article (English and Spanish)
	 * - General (English and Spanish)
	 */		
	
	// TODO: more test cases
	// TODO: create URLs
	// TODO: handle these: 
	/***
	 * Other values needed:
	 *  Example from General=https://www-qa.cancer.gov/about-nci/visit:
		channel: about NCI
		suites: ncidevelopment, ncienterprise-dev
		prop25: 01/01/1980
		prop26: 2018|8|22|15
		prop29: 3:02 PM|Wednesday
		prop42: Normal
		prop48: 12pct|12pct|3796px|/
		prop61: www.cancer.gov/
		prop64: 12|0
		prop65: 3
		eVar1: www.cancer.gov/about-nci/visit
		eVar5: Extra wide
		Hierarchy: 1 www-qa.cancer.gov|about-nci|visit
	 */
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "InnerPage";
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}
	
	/// Inner page loads return expected values
	@Test(dataProvider = "InnerPageLoad", groups = { "Analytics" })
	public void testHomePageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			beacon = getBeacon();
			System.out.println(contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			DoCommonLoadAssertions(beacon, analyticsPageLoad, path);
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	@DataProvider(name = "InnerPageLoad")
	public Iterator<Object[]> getInnerPageLoadData() {
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