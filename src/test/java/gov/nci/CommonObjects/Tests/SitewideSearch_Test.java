package gov.nci.CommonObjects.Tests;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import gov.nci.Utilities.BrowserManager;
import gov.nci.Utilities.ExcelManager;
import gov.nci.clinicaltrials.BaseClass;
import gov.nci.commonobjects.SitewideSearch;

public class SitewideSearch_Test extends BaseClass {

	public static final String TESTDATA_SHEET_NAME = "SitewideSearch";

	SitewideSearch search;
	String testDataFilePath;

	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("HomePage");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, config, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		search = new SitewideSearch(driver, logger);
		testDataFilePath = config.getProperty("SiteWideSearchTestData");
	}

	// Verify the Search result page URL to contain "results"
	@Test(dataProvider = "Search", groups = { "Smoke" })
	public void verifySearchResultsPageURL(String keyword) {
		search.search(keyword);
		Assert.assertTrue(driver.getCurrentUrl().endsWith("results"));
		System.out.println("Search Result Page URL: " + driver.getCurrentUrl());
		logger.log(LogStatus.PASS, "Verify that URL of Search Results page is correct");
	}

	/********************** Data Providers **********************/

	@DataProvider(name = "Search")
	public Iterator<Object[]> readSearchData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String cancerType = excelReader.getCellData(TESTDATA_SHEET_NAME, "Keyword1", rowNum);
			Object ob[] = { cancerType };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	@DataProvider(name = "SearchWithinSearch")
	public Iterator<Object[]> readSearchWithinSearchData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String keyword1 = excelReader.getCellData(TESTDATA_SHEET_NAME, "Keyword1", rowNum);
			String keyword2 = excelReader.getCellData(TESTDATA_SHEET_NAME, "Keyword2", rowNum);
			Object ob[] = { keyword1, keyword2 };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	/********************** Common Functions **********************/
	// Verify Search Results page common validation
	public void verifySearchResultsPage() {
		// Verify Search Results Page Title
		String pageTitle = search.getSearchResultsPageTitle();
		Assert.assertTrue(pageTitle.contains(search.SEARCH_RESULT_PAGE_TITLE));

		// Verify Search Results Page H1 Title
		String h1Title = search.getSearchResultsH1Title();
		Assert.assertTrue(h1Title.contains(search.getSearchResultsH1Title()));

		// Verify the Search result page URL to contain "result"
		Assert.assertTrue(driver.getCurrentUrl().endsWith("results"));
		System.out.println("Search Result Page URL: " + driver.getCurrentUrl());

		// Verify the Search Within Search Box
		Assert.assertTrue(search.getSearchWithinSearchBox().isDisplayed(), "Search Within Search Box is not displayed");
		Assert.assertTrue(search.getSearchWithinSearchButton().isDisplayed(),
				"Search Within Search Button is not displayed");
	}

}
