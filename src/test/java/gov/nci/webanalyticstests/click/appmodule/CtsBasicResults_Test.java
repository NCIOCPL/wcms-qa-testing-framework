package gov.nci.webanalyticstests.click.appmodule;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.AdvanceSearch;
import gov.nci.clinicalTrial.pages.AdvanceSearchResults;
import gov.nci.clinicalTrial.pages.BasicSearch;
import gov.nci.clinicalTrial.pages.SearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.click.AnalyticsTestClickBase;

public class CtsBasicResults_Test extends AnalyticsTestClickBase {
	
	private final String PATH = "/about-cancer/treatment/clinical-trials/search/r";
	private final String BASIC_PAGE_1ST = "?rl=1";
	private final String BASIC_PAGE_2ND = "?loc=0&rl=1&pn=2&ni=10";
	
	// Don't see an exisiting BasicSearchResultsPage, so I'm reusing advanced for now
	private AdvanceSearchResults searchResults;
	private Beacon beacon;
	

	
	@Test(groups = { "Analytics" })
	public void testStartOverClick() {
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
//		
//	@Test(groups = { "Analytics" })
//	public void testBasicStart() {
//		System.out.println("CTS \"Start\" click event:");
//		basicSearch.setSearchKeyword("canc");
//		action.pause(1000).perform();
//		beacon = getBeacon();
//		
//		doCommonClassAssertions(beacon);
//		Assert.assertTrue(beacon.hasEvent(38));
//		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|start");
//		logger.log(LogStatus.PASS, "CTS Basic 'start' value test passed.");
//	}
//	
//
//	@Test(groups = { "Analytics" })
//	public void testErrorAge() {
//		System.out.println("CTS \"Error\" click event for age:");
//		basicSearch.setSearchAge("abc");
//		basicSearch.setSearchKeyword("");
//		beacon = getBeacon();
//
//		doCommonClassAssertions(beacon);
//		Assert.assertTrue(beacon.hasEvent(41));
//		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
//		Assert.assertEquals(beacon.props.get(75), "a|Please enter a number between 1 and 120.");
//		logger.log(LogStatus.PASS, "CTS \"Error\" click event for age passed.");
//	}
	
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
		Assert.assertTrue(beacon.channels.length() > 0);
		Assert.assertEquals(beacon.props.get(4), "D=pev1");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.props.get(67), "D=pageName");
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
	}
	
}

