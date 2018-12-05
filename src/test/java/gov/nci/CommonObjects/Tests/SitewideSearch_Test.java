package gov.nci.CommonObjects.Tests;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

	public static final String SITEWIDE_SEARCH_SHEET_NAME = "SitewideSearch";

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
		testDataFilePath = config.getProperty("SiteWideSearchData");
	}

	@Test(dataProvider = "Search", groups = { "Smoke" })
	public void verifySitewideSearch(String keyword) {

		System.out.println("Search Keyword: " + keyword);
		search.search(keyword);

		// Verify Search Results page common validation
		verifySearchResultsPage(keyword);

		logger.log(LogStatus.PASS,
				"Verify that when a keyword is searched, Search Result page is displayed with following validations: "
						+ "Page Title, H1 Title, URL ending with 'results', results text");
	}

	/********************** Data Providers **********************/

	@DataProvider(name = "Search")
	public Iterator<Object[]> readSearchData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(SITEWIDE_SEARCH_SHEET_NAME); rowNum++) {
			String cancerType = excelReader.getCellData(SITEWIDE_SEARCH_SHEET_NAME, "Keyword1", rowNum);
			Object ob[] = { cancerType };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	@DataProvider(name = "SearchWithinSearch")
	public Iterator<Object[]> readSearchWithinSearchData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(SITEWIDE_SEARCH_SHEET_NAME); rowNum++) {
			String keyword1 = excelReader.getCellData(SITEWIDE_SEARCH_SHEET_NAME, "Keyword1", rowNum);
			String keyword2 = excelReader.getCellData(SITEWIDE_SEARCH_SHEET_NAME, "Keyword2", rowNum);
			Object ob[] = { keyword1, keyword2 };

			myObjects.add(ob);

		}
		return myObjects.iterator();

	}

	/********************** Common Functions **********************/
	// Verify Search Results page common validation
	public void verifySearchResultsPage(String keyword) {
		// Verify Search Results Page Title
		String pageTitle = search.getSearchResultsPageTitle();
		Assert.assertTrue(pageTitle.contains(search.SEARCH_RESULT_PAGE_TITLE));

		// Verify Search Results Page H1 Title
		String h1Title = search.getSearchResultsH1Title();
		Assert.assertTrue(h1Title.contains(search.SEARCH_RESULT_H1_TITLE));

		// Verify the Search result page URL to contain "result"
		Assert.assertTrue(driver.getCurrentUrl().endsWith("results"));
		System.out.println("Search Result Page URL: " + driver.getCurrentUrl());

		// Verify that the Result Text contains search keyword
		WebElement ele = driver.findElement(By.xpath("//h3[@id='ui-id-2']"));
		System.out.println("Element Value ***********: " + ele.getText());
		Assert.assertTrue(ele.getText().equals("Results for: " + keyword));

		// Verify that the Top Search Result Text contains search keyword
		WebElement topResultText = driver.findElement(By.xpath("(//h4)[1]"));
		System.out.println("Top Result Text Value ***********: " + topResultText.getText());
		Assert.assertTrue(topResultText.getText().contains("Results 1\u201310 of"));
		Assert.assertTrue(topResultText.getText().contains("for: " + keyword));

		// Verify that the bottom Search Result Text contains search keyword
		WebElement bottomResultText = driver.findElement(By.xpath("(//h4)[2]"));
		System.out.println("Bottom Result Text Value ***********: " + bottomResultText.getText());
		Assert.assertTrue(bottomResultText.getText().contains("results found for: " + keyword));

		// Verify the Search Within Search Box
		Assert.assertTrue(search.getSearchWithinSearchBox().isDisplayed(), "Search Within Search Box is not displayed");
		Assert.assertTrue(search.getSearchWithinSearchButton().isDisplayed(),
				"Search Within Search Button is not displayed");
	}

}
