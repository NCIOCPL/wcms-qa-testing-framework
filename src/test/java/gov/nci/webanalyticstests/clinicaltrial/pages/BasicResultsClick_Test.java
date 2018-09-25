package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import gov.nci.clinicalTrial.common.Checkbox;
import gov.nci.clinicalTrial.pages.AdvanceSearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.commonobjects.Checkboxes;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BasicResultsClick_Test extends AnalyticsTestClickBase {

	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String BASIC_PAGE_1ST = "?rl=1";
	private final String BASIC_PAGE_2ND = "?loc=0&rl=1&pn=2&ni=10";

	// There is no "BasicSearchResults" page object class; so we're reusing
	// AdvanceSearchResults for now
	private AdvanceSearchResults searchResults;
	private Actions action;

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
			searchResults.clearCheckBoxes();
			action = new Actions(driver);
			action.pause(1000);
		} catch (Exception ex) {
			Assert.fail("Error loading Basic CTS results url: " + PATH + queryParams);
			ex.printStackTrace();
		}
	}

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
			searchResults.clickPrintButton();
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
	// @Test(groups = { "Analytics" }, priority = 1)
	public void testPrintOneItem() {
		System.out.println("Test Basic 'print one item' click event:");
		setupTestMethod(BASIC_PAGE_1ST);

		try {
			Checkboxes checkboxes = new Checkboxes(driver, ".cts-results-container input");
			checkboxes.checkCheckBox(2);

			action.pause(3000);

			searchResults.clickPrintButton();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(48));
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_selectall_10_1");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS, "Test Basic 'print one item' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Test Basic 'print multiple items' click event
	// @Test(groups = { "Analytics" })
	public void testPrintMultiItems() {
		System.out.println("Test Basic 'print multiple items' click event:");
		setupTestMethod(BASIC_PAGE_1ST);

		try {
			searchResults.clickOnSelectAllCheckBox();
			searchResults.clickPrintButton();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(48));
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_selectall_10_1");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS, "Test Basic 'print multiple items' click event passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// @Test(groups = { "Analytics" }, priority = 4)
	public void testPrintAllItemsOnPage() {
		try {
			System.out.println("Basic print all items click event:");
			setupTestMethod(BASIC_PAGE_1ST);
			searchResults.clearCheckBoxes();
			searchResults.clickOnSelectAllCheckBox();
			searchResults.clickPrintButton();
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(48));
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_selectall_10_1");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS, "Basic print all items click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic print all items click event.");
			e.printStackTrace();
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
