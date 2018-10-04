package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.List;

import gov.nci.clinicalTrial.common.Checkbox;
import gov.nci.clinicalTrial.pages.AdvanceSearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BasicResultsClick_Test extends AnalyticsTestClickBase {

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String BASIC_PAGE_1ST = "?rl=1";
	private final String BASIC_PAGE_2ND = "?loc=0&rl=1&pn=2&ni=10";
	private final String CHECKBOX_ITEM_SELECTOR = ".cts-results-container input";

	// There is no "BasicSearchResults" page object class; so we're reusing
	// AdvanceSearchResults for now
	private AdvanceSearchResults searchResults;

	// ==================== Setup methods ==================== //

	/**
	 * Navigate, suppress chat, create advancedSearch object for each test.
	 * 
	 * @param queryParams
	 */
	private void setupTestMethod(String queryParams) {
		driver.get(config.goHome() + PATH + queryParams);

		try {
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			searchResults = new AdvanceSearchResults(driver, chatPrompt);

			// Clear checkboxes from previous session
			Checkbox.uncheckAll(driver);
			driver.manage().deleteAllCookies();
			driver.navigate().refresh();
		} catch (Exception ex) {
			Assert.fail("Error loading Basic CTS results url: " + PATH + queryParams);
			ex.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test Basic 'Start Over' click event
	@Test(groups = { "Analytics" })
	public void testBasicStartOverClick() {
		System.out.println("Test Basic 'Start Over' click event:");
		setupTestMethod(BASIC_PAGE_1ST);

		try {
			searchResults.clickStartOverNoNav();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(49));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|start over");
			logger.log(LogStatus.PASS, "Test Basic 'Start Over' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Basic print error click event
	@Test(groups = { "Analytics" })
	public void testPrintError() {
		System.out.println("Test Basic print error click event:");
		setupTestMethod(BASIC_PAGE_1ST);

		try {
			searchResults.clickPrintButtonNoUrlChange();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
			Assert.assertEquals(beacon.props.get(75), "printselected|noneselected");
			logger.log(LogStatus.PASS, "Test Basic print error click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Basic 'print one item' click event
	@Test(groups = { "Analytics" })
	public void testPrintOneItem() {
		System.out.println("Test Basic 'print one item' click event:");
		setupTestMethod(BASIC_PAGE_1ST);

		try {
			List<String> attrList = searchResults.getFieldAttributeCollection(CHECKBOX_ITEM_SELECTOR, "id");
			Checkbox checkbox = new Checkbox(driver, CHECKBOX_ITEM_SELECTOR);
			;
			checkbox.checkCheckbox(attrList.get(1));
			searchResults.clickPrintButton();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(48));
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_noselectall_1_1");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS, "Test Basic 'print one item' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Basic 'print multiple items' click event
	@Test(groups = { "Analytics" })
	public void testPrintMultiItems() {
		System.out.println("Test Basic 'print multiple items' click event:");
		setupTestMethod(BASIC_PAGE_1ST);

		try {
			List<String> attrList = searchResults.getFieldAttributeCollection(CHECKBOX_ITEM_SELECTOR, "id");
			Checkbox checkbox = new Checkbox(driver, CHECKBOX_ITEM_SELECTOR);
			checkbox.checkCheckbox(attrList.get(1));
			checkbox.checkCheckbox(attrList.get(2));
			searchResults.clickPrintButton();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(48));
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_noselectall_2_1");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS, "Test Basic 'print multiple items' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Basic 'select all' print click event
	// TODO: this method is making error, multiItems, and oneItem break
	// @Test(groups = { "Analytics" })
	public void testPrintAllItemsOnPage() {
		System.out.println("Test Basic 'select all' print click event:");
		setupTestMethod(BASIC_PAGE_2ND);

		try {
			Checkbox checkbox = new Checkbox(driver, CHECKBOX_ITEM_SELECTOR);
			searchResults.clickOnSelectAllCheckBox();
			searchResults.clickPrintButton();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(48));
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_selectall_10_2");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS, "Test Basic 'select all' print click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Basic result ranking click event
	@Test(groups = { "Analytics" })
	public void testBasicLinkRanking() {
		System.out.println("Test Basic result ranking click event:");
		setupTestMethod(BASIC_PAGE_2ND);

		try {
			searchResults.clickResultLinkByIndex(0);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(42));
			Assert.assertEquals(beacon.props.get(12), "clinicaltrials_basic");
			Assert.assertEquals(beacon.props.get(13), "1|page 2");
			Assert.assertEquals(beacon.props.get(12), beacon.eVars.get(12));
			logger.log(LogStatus.PASS, "Test Basic result ranking click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for BasicResultsClick_Test
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}

}
