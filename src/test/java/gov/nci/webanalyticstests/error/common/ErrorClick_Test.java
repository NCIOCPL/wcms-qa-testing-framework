package gov.nci.webanalyticstests.error.common;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.error.pages.PageNotFound;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;
import gov.nci.Utilities.ExcelManager;

public class ErrorClick_Test extends AnalyticsTestClickBase {

	private final String TESTDATA_SHEET_NAME = "SitewideSearch";

	private PageNotFound pageNotFound;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsSwsData");
	}

	// ==================== Test methods ==================== //

	// Verify analytics click values when searching from error page
	@Test(dataProvider = "DefinitionTerms", groups = { "Analytics" })
	public void testErrorPageSearch(String searchTerm) {
		try {
			driver.get(config.getPageURL("PageNotFound"));
			pageNotFound = new PageNotFound(driver);
			pageNotFound.setSitewideSearchKeyword(searchTerm);
			pageNotFound.selectEnglish();
			pageNotFound.clickSearchButton();
			System.out.println("Sitewide search term: " + searchTerm);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, searchTerm);
			Assert.assertEquals(beacon.linkName, "PageNotFound");
			Assert.assertEquals(beacon.props.get(11), "pagenotfoundsearch");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "DefinitionTerms")
	public Iterator<Object[]> readDefinitionTerm_Data() {
		return getFilteredSearchTerms("Definition", TESTDATA_SHEET_NAME);
	}

	/**
	 * Get an iterable collection of test objects given a data sheet name and column
	 * 
	 * @param columnName
	 * @param sheetName
	 */
	private Iterator<Object[]> getFilteredSearchTerms(String columnName, String sheetName) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(sheetName); rowNum++) {
			String searchTerm = excelReader.getCellData(sheetName, columnName, rowNum);
			if (!searchTerm.isEmpty()) {
				Object ob[] = { searchTerm };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 * @param searchTerm
	 */
	private void doCommonClassAssertions(Beacon beacon, String searchTerm) {
		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(2));
		Assert.assertEquals(beacon.props.get(14), searchTerm.toLowerCase());
		Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));
		Assert.assertTrue(beacon.eVars.get(13).matches("^\\+\\d{1,2}$"));
		Assert.assertEquals(beacon.eVars.get(14), beacon.props.get(14));
	}

}
