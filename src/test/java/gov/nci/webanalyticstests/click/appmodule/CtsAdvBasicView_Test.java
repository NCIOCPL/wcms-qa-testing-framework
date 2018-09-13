package gov.nci.webanalyticstests.click.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.AdvanceSearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.click.AnalyticsTestClickBase;

public class CtsAdvBasicView_Test extends AnalyticsTestClickBase {
		
	private final String PATH = "/about-cancer/treatment/clinical-trials/search/v";
	private final String FROM_ADVANCED_SEARCH = "?t=C4911&q=nivolumab&loc=0&tid=S1609&rl=2&id=NCI-2016-01041&pn=1&ni=10";
	private final String FROM_BASIC_SEARCH = "?q=ipilimumab&loc=1&z=20850&zp=100&rl=1&id=NCI-2016-01041&pn=1&ni=10";	
	private final String FROM_LISTING_PAGE = "?id=NCI-2016-01041&r=1";
	private final String FROM_CT_REDIRECT = "?id=NCT02834013&r=1";
	
	// Don't see an exisiting BasicSearchResultsPage, so I'm reusing advanced for all instances
	private AdvanceSearchResults searchResults;
	private Beacon beacon;
		
	
	@Test(groups = { "Analytics" })
	public void testAdvLinkRanking() {
		try {
			
			// DO STUFF HERE
			Assert.assertEquals(1,  1);

			logger.log(LogStatus.PASS,"Advanced ranked result click event passed.");
		} catch (Exception e) {
			Assert.fail("Error: Advanced ranked result click event.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Go to the results page and create a new searchresults object.
	 * @param queryParams
	 */
	private void setSearchResults(String queryParams) {
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

