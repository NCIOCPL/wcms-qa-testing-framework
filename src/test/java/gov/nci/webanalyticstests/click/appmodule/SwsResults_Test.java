package gov.nci.webanalyticstests.click.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.SitewideSearchForm;
import gov.nci.sitewidesearch.pages.SitewideSearchResults;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.click.AnalyticsTestClickBase;

public class SwsResults_Test extends AnalyticsTestClickBase {
	
	private SitewideSearchForm swSearchForm;
	private SitewideSearchResults swSearchResults;
	private Beacon beacon;

	private final String SEARCH_TERM = "Tumor";	

	
//	Regular 	
//	prop4
//	D=pev1
//	prop8
//	english
//	prop12
//	generic
//	prop13
//	1
//	prop67
//	D=pageName
//	eVar2
//	english
//	eVar12
//	generic
	
//	Best bet 	
//	prop4
//	D=pev1
//	prop8
//	english
//	prop12
//	generic
//	prop13
//	1
//	prop67
//	D=pageName
//	eVar2
//	english
//	eVar12
//	generic
	

	@Test(groups = { "Analytics" })
	public void testBestBetsClick() {
		try {
			System.out.println("Test Best Bets click event for " + SEARCH_TERM);
			driver.get(config.goHome());
			swSearchForm = new SitewideSearchForm(driver);
			swSearchForm.doSitewideSearch(SEARCH_TERM);
			swSearchResults = new SitewideSearchResults(driver);
			swSearchResults.clickBestBets();
		    beacon = getBeacon();
		    
		    doCommonClassAssertions(beacon);
		    Assert.assertEquals(beacon.props.get(12), "best_bets");
		    Assert.assertEquals(beacon.props.get(13), "1");
		    Assert.assertEquals(beacon.eVars.get(12), beacon.props.get(12));
			logger.log(LogStatus.PASS, "Best Bet click values are correct.");
		} catch (Exception e) {
			Assert.fail("Error getting Best Bet click values.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values when searching from sitewide search results page
	@Test(groups = { "Analytics" })
	public void testSearchWithinResults() {
		try {
			driver.get(config.getPageURL("SitewideResultsPage"));
			swSearchResults = new SitewideSearchResults(driver);
			swSearchResults.doWithinSearch();
			swSearchResults.setSitewideSearchKeyword(SEARCH_TERM);
		    swSearchResults.clickSearchButton();
			System.out.println("Sitewide search term: " + SEARCH_TERM);
		    beacon = getBeacon();
		    
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "SiteWideSearchResultsSearch");
			Assert.assertEquals(beacon.props.get(11), "sitewide_bottom_withinresults");
			Assert.assertTrue(beacon.hasEvent(2));
			Assert.assertEquals(beacon.props.get(14), SEARCH_TERM.toLowerCase());
			Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));		
			Assert.assertTrue(beacon.eVars.get(13).matches("^\\+\\d{1,2}$"));
			Assert.assertEquals(beacon.eVars.get(14), beacon.props.get(14));
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics click values when searching from sitewide search results page
	@Test(groups = { "Analytics" })
	public void testSearchNewFromResults() {
		try {
			driver.get(config.getPageURL("SitewideResultsPage"));
			swSearchResults = new SitewideSearchResults(driver);			
			swSearchResults.setSitewideSearchKeyword(SEARCH_TERM);
		    swSearchResults.clickSearchButton();
			System.out.println("Sitewide search term: " + SEARCH_TERM);
		    beacon = getBeacon();
		    
			doCommonClassAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "SiteWideSearchResultsSearch");
			Assert.assertEquals(beacon.props.get(11), "sitewide_bottom_new");
			Assert.assertTrue(beacon.hasEvent(2));
			Assert.assertEquals(beacon.props.get(14), SEARCH_TERM.toLowerCase());
			Assert.assertEquals(beacon.eVars.get(11), beacon.props.get(11));		
			Assert.assertTrue(beacon.eVars.get(13).matches("^\\+\\d{1,2}$"));
			Assert.assertEquals(beacon.eVars.get(14), beacon.props.get(14));
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
			e.printStackTrace();
		}
	}	
	
	/**
	 * Shared assertions for all tests in this class.
	 * @param beacon
	 * @param analyticsPageLoad
	 * @param path
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);
	}
	
}