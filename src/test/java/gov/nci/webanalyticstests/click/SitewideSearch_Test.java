package gov.nci.webanalyticstests.click;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.SitewideSearchForm;
import gov.nci.error.pages.PageNotFound;
import gov.nci.sitewidesearch.pages.SitewideSearchResults;
import gov.nci.Utilities.ExcelManager;
import gov.nci.WebAnalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class SitewideSearch_Test extends AnalyticsTestClickBase {
	
	private SitewideSearchForm swSearchForm;
	private SitewideSearchResults swSearchResults;
	private PageNotFound pageNotFound;
	private Beacon beacon;
	
	private final String TESTDATA_SHEET_NAME = "SitewideSearch";	
	private final String TESTDATA_SHEET_NAME_ES = "SitewideSearchEs";	
	private String testDataFilePath;
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("SitewideSearchData");
	}
	
	@BeforeMethod(groups = { "Analytics" }) 
	public void setupTestMethod() {
		/// Do these before each test
	}

	// Verify analytics click values for sitewide cancer term search
	@Test(dataProvider = "CancerTerms", groups = { "Analytics" })
	public void testSitewideSearch(String searchTerm) {
		try {
			driver.get(config.goHome());
			swSearchForm = new SitewideSearchForm(driver);
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
		    beacon = getClickBeacon();
			System.out.println("Sitewide search term: " + searchTerm);
			AssertCommon(searchTerm);
			Assert.assertTrue(beacon.hasProp(11, "sitewide"), "Search type value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values for sitewide Spanish term search
	@Test(dataProvider = "CancerTermsEs", groups = { "Analytics" })
	public void testSitewideSearchEspanol(String searchTerm) {
		try {
			driver.get(config.getPageURL("SpanishPage"));
			swSearchForm = new SitewideSearchForm(driver);			
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
		    beacon = getClickBeacon(); 
			System.out.println("Sitewide search term: " + searchTerm);
			AssertCommon(searchTerm);
			Assert.assertTrue(beacon.hasProp(8, "spanish"), "Prop language value incorrect.");
			Assert.assertTrue(beacon.hasProp(11, "sitewide"), "Search type value incorrect.");
			Assert.assertTrue(beacon.haseVar(11, "sitewide"), "Search type value incorrect.");
			Assert.assertTrue(beacon.haseVar(2, "spanish"), "eVar language value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}

	// Verify analytics click values for sitewide search w/o results
	@Test(dataProvider = "NoMatchTerms", groups = { "Analytics" })
	public void testSitewideSearchNoMatch(String searchTerm) {
		try {
			driver.get(config.goHome());
			swSearchForm = new SitewideSearchForm(driver);
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
		    beacon = getClickBeacon();
			System.out.println("Sitewide search term: " + searchTerm);
			AssertCommon(searchTerm);
			Assert.assertTrue(beacon.hasProp(11, "sitewide"), "Search type value incorrect.");
			Assert.assertTrue(beacon.haseVar(11, "sitewide"), "Search type value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values for microsite-wide search
	@Test(dataProvider = "CancerTerms", groups = { "Analytics" })
	public void testMicroSitewideSearch(String searchTerm) {
		try {
			driver.get(config.getPageURL("MicroSite"));
			swSearchForm = new SitewideSearchForm(driver);			
		    swSearchForm.setSitewideSearchKeyword(searchTerm);
		    swSearchForm.clickSearchButton();
		    beacon = getClickBeacon();
			System.out.println("Sitewide search term: " + searchTerm);
			AssertCommon(searchTerm);
			Assert.assertTrue(beacon.hasProp(11, "sitewide"), "Search type value incorrect.");
			Assert.assertTrue(beacon.haseVar(11, "sitewide"), "Search type value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values when searching from sitewide search results page
	@Test(dataProvider = "DefinitionTerms", groups = { "Analytics" })
	public void testSearchWithinResults(String searchTerm) {
		try {
			driver.get(config.getPageURL("SitewideResultsPage"));
			swSearchResults = new SitewideSearchResults(driver);
			swSearchResults.doWithinSearch();
			swSearchResults.setSitewideSearchKeyword(searchTerm);
		    swSearchResults.clickSearchButton();
		    beacon = getClickBeacon();
			System.out.println("Sitewide search term: " + searchTerm);
			AssertCommon(searchTerm);
			Assert.assertTrue(beacon.hasProp(11, "sitewide_bottom_withinresults"), "Search type value incorrect.");
			Assert.assertTrue(beacon.haseVar(11, "sitewide_bottom_withinresults"), "Search type value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values when searching from sitewide search results page
	@Test(dataProvider = "DefinitionTerms", groups = { "Analytics" })
	public void testSearchNewFromResults(String searchTerm) {
		try {
			driver.get(config.getPageURL("SitewideResultsPage"));
			swSearchResults = new SitewideSearchResults(driver);			
			swSearchResults.setSitewideSearchKeyword(searchTerm);
		    swSearchResults.clickSearchButton();
		    beacon = getClickBeacon();
			System.out.println("Sitewide search term: " + searchTerm);
			AssertCommon(searchTerm);
			Assert.assertTrue(beacon.hasProp(11, "sitewide_bottom_new"), "Search type value incorrect.");
			Assert.assertTrue(beacon.haseVar(11, "sitewide_bottom_new"), "Search type value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values when searching from error page
	@Test(dataProvider = "DefinitionTerms", groups = { "Analytics" })
	public void testErrorPageSearch(String searchTerm) {
		try {
			driver.get(config.getPageURL("PageNotFound"));
			pageNotFound = new PageNotFound(driver);
			pageNotFound.setSitewideSearchKeyword(searchTerm);
			pageNotFound.selectEnglish();
			pageNotFound.clickSearchButton();
		    beacon = getClickBeacon();
			System.out.println("Sitewide search term: " + searchTerm);
			AssertCommon(searchTerm);
			Assert.assertTrue(beacon.hasProp(11, "pagenotfoundsearch"), "Search type value incorrect.");			
			Assert.assertTrue(beacon.haseVar(11, "pagenotfoundsearch"), "Search type value incorrect.");
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}

	// Do common assertions
	private void AssertCommon(String searchTerm) {
		Assert.assertTrue(beacon.hasEvent(2), "Event value incorrect.");
		Assert.assertTrue(beacon.hasProp(4, "d=pev1"), "Value of prop4 incorrect.");
		Assert.assertTrue(beacon.hasProp(14, searchTerm), "Search term value does not match.");
		Assert.assertTrue(beacon.hasProp(67, "D=pageName"), "Dynamic pageName value incorrect.");
		Assert.assertTrue(beacon.haseVar(13), "eVar13 value incorrect.");
		Assert.assertTrue(beacon.haseVar(14, searchTerm), "Search type value incorrect.");
	}
	
	/******************** Data Providers ****************/
	@DataProvider(name = "CancerTerms")
	public Iterator<Object[]> readCancerTerm_Data() {
		return getDataIteratorObject("CancerTerm", TESTDATA_SHEET_NAME);
	}

	@DataProvider(name = "CancerTermsEs")
	public Iterator<Object[]> readCancerTermEs_Data() {
		return getDataIteratorObject("CancerTerm", TESTDATA_SHEET_NAME_ES);
	}

	@DataProvider(name = "DefinitionTerms")
	public Iterator<Object[]> readDefinitionTerm_Data() {
		return getDataIteratorObject("Definition", TESTDATA_SHEET_NAME);
	}
	
	@DataProvider(name = "NoMatchTerms")
	public Iterator<Object[]> readNoMatchTerm_Data() {
		return getDataIteratorObject("NoMatch", TESTDATA_SHEET_NAME);
	}
	
	/**
	 * Get an iterable collection of test objects given a data sheet name and column
	 * @param columnName
	 * @param sheetName
	 */	
	private Iterator<Object[]> getDataIteratorObject(String columnName, String sheetName) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(sheetName); rowNum++) {
			String searchTerm = excelReader.getCellData(sheetName, columnName, rowNum);
			if(!searchTerm.isEmpty()) {
				Object ob[] = { searchTerm };
				myObjects.add(ob);
			}
		}
		return myObjects.iterator();
	}
}
