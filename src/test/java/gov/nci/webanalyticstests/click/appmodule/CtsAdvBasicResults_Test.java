package gov.nci.webanalyticstests.click.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.AdvanceSearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.click.AnalyticsTestClickBase;

public class CtsAdvBasicResults_Test extends AnalyticsTestClickBase {
	
	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String BASIC_PAGE_1ST = "?rl=1";
	private final String BASIC_PAGE_2ND = "?loc=0&rl=1&pn=2&ni=10";	
	private final String ADVANCED_PAGE_1ST = "?rl=2";
	private final String ADVANCED_PAGE_2ND = "?loc=0&rl=2&pn=2&ni=10";
	
	// Don't see an exisiting BasicSearchResultsPage, so I'm reusing advanced for all instances
	private AdvanceSearchResults searchResults;
	private Beacon beacon;
		
	@Test(groups = { "Analytics" })
	public void testBasicStartOverClick() {
		try {
			System.out.println("Basic 'Start Over' click event:");
			setBasicSearchResults(BASIC_PAGE_1ST);
			// TODO: build utility function to stop nav
			searchResults.clickStartOverNoNav();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);			
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|start over");
			logger.log(LogStatus.PASS, "Basic 'Start Over' click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic 'Start Over' click event.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testAdvStartOverClick() {
		try {
			System.out.println("Advanced 'Start Over' click event:");
			setBasicSearchResults(ADVANCED_PAGE_1ST);
			searchResults.clickStartOverNoNav();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);			
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|start over");
			logger.log(LogStatus.PASS, "Advanced 'Start Over' click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Advanced 'Start Over' click event.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" }, priority = 1)
	public void testPrintError() {
		try {
			System.out.println("Basic print error click event:");
			setBasicSearchResults(BASIC_PAGE_1ST);
			searchResults.clearCheckBoxes();
			searchResults.clickPrintButton();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(41));
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
			Assert.assertEquals(beacon.props.get(75), "printselected|noneselected");
			logger.log(LogStatus.PASS,"Basic print error click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic print error click event.");
			e.printStackTrace();
		}
	}
	
	//@Test(groups = { "Analytics" }, priority = 2)
	public void testPrintOneItem() {
		try {
			System.out.println("Basic print one item click event:");
			setBasicSearchResults(BASIC_PAGE_1ST);
			searchResults.clearCheckBoxes();
			searchResults.selectCheckboxByIndex(2);
			searchResults.clickPrintButton();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);			
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_selectall_10_1");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS,"Basic print one item click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic print one item click event.");
			e.printStackTrace();
		}
	}
	
	//@Test(groups = { "Analytics" }, priority = 3)
	public void testPrintMultiItems() {
		try {
			System.out.println("Basic print one item click event:");
			setBasicSearchResults(BASIC_PAGE_1ST);
			searchResults.clearCheckBoxes();
			searchResults.clickOnSelectAllCheckBox();
			searchResults.clickPrintButton();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);			
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_selectall_10_1");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS,"Basic print one item click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic print one item click event.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" }, priority = 4)
	public void testPrintAllItems() {
		try {
			System.out.println("Basic print all items click event:");
			setBasicSearchResults(BASIC_PAGE_1ST);
			searchResults.clearCheckBoxes();
			searchResults.clickOnSelectAllCheckBox();
			searchResults.clickPrintButton();
			beacon = getBeacon();
			
			doCommonClassAssertions(beacon);			
			Assert.assertEquals(beacon.props.get(21), "ctsprintselected_top_selectall_10_1");
			Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|print selected");
			logger.log(LogStatus.PASS,"Basic print all items click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Basic print all items click event.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Go to the results page and retrieve the beacon request object.
	 * @param queryParams
	 */
	private void setBasicSearchResults(String queryParams) {
		try {
			driver.get(config.goHome() + PATH + queryParams);
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			searchResults = new AdvanceSearchResults(driver, chatPrompt);
		} catch (Exception ex) {
			Assert.fail("Error loading Advanced CTS results url: " + PATH + queryParams);
			ex.printStackTrace();
		}
	}
	
	/**
	 * Shared Assert() calls for CtsBasicSearch_Test
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		Assert.assertTrue(beacon.suites.length > 0);
		Assert.assertEquals(beacon.channels, "About Cancer");
		Assert.assertEquals(beacon.props.get(4), "D=pev1");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.props.get(67), "D=pageName");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}
	
}

