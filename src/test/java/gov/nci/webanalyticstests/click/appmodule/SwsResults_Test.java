package gov.nci.webanalyticstests.click.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.SitewideSearchForm;
import gov.nci.sitewidesearch.pages.SitewideSearchResults;
import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.load.AnalyticsTestLoadBase;

public class SwsResults_Test extends AnalyticsTestLoadBase {
	
	private AnalyticsPageLoad analyticsPageLoad;
	private SitewideSearchResults swSearchResults;	
	private SitewideSearchForm swSearchForm;
	private Beacon beacon;

	private final String RESULTS_PATH_EN = "/search/results";
	private final String RESULTS_PATH_ES = "/espanol/buscar/resultados";
	private final String SEARCH_TERM = "Tumor";	
	private final String CSS_RESULTS_NUM = ".sitewide-results span.results-num";

	// Verify analytics load values for sitewide cancer term search results
	@Test(groups = { "Analytics" })
	public void testSwsResultsPageEn() {
		try {
			System.out.println("Test sitewide search term: " + SEARCH_TERM);
			driver.get(config.goHome());
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			swSearchForm = new SitewideSearchForm(driver);
		    swSearchForm.setSitewideSearchKeyword(SEARCH_TERM);
		    swSearchForm.clickSearchButton();
		    beacon = getBeacon();
		    
		    analyticsPageLoad.setPageTitle("NCI Search Results - National Cancer Institute");
		    doCommonClassAssertions(beacon, analyticsPageLoad, RESULTS_PATH_EN);
			logger.log(LogStatus.PASS, "English results load values are correct.");
		} catch (Exception e) {
			Assert.fail("Error submitting English sitewide search.");
			e.printStackTrace();
		}
	}
	
	// Verify analytics load values for Spanish sitewide cancer term search results
	@Test(groups = { "Analytics" })
	public void testSwsResultsPageEs() {
		try {
			System.out.println("Test Spanish sitewide search term: " + SEARCH_TERM);
			driver.get(config.goHome() + "/espanol");
			analyticsPageLoad = new AnalyticsPageLoad(driver);			
			swSearchForm = new SitewideSearchForm(driver);
		    swSearchForm.setSitewideSearchKeyword(SEARCH_TERM);
		    swSearchForm.clickSearchButton();
		    beacon = getBeacon();
		    
		    analyticsPageLoad.setPageTitle("Resultados - National Cancer Institute");
		    doCommonClassAssertions(beacon, analyticsPageLoad, RESULTS_PATH_ES);
			logger.log(LogStatus.PASS, "Spanish results load values are correct.");
		} catch (Exception e) {
			Assert.fail("Error submitting Spanish sitewide search.");
			e.printStackTrace();
		}
	}

	// Verify analytics load values for sitewide cancer term search results
	@Test(groups = { "Analytics" })
	public void testSwsResultsPageNoResults() {
		try {
			System.out.println("Direct navigation to results page");			
			driver.get(config.goHome() + RESULTS_PATH_EN);
		    beacon = getBeacon();
		    
		    analyticsPageLoad.setPageTitle("NCI Search Results - National Cancer Institute");		    
		    doCommonClassAssertions(beacon, analyticsPageLoad, RESULTS_PATH_EN);
			logger.log(LogStatus.PASS, "Sitewide search direct navigation values are correct.");
		} catch (Exception e) {
			Assert.fail("Error on sitewide search direct navigation.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Shared assertions for all tests in this class.
	 * @param beacon
	 * @param analyticsPageLoad
	 * @param path
	 */
	private void doCommonClassAssertions(Beacon beacon, AnalyticsPageLoad analyticsPageLoad, String path) {
		doCommonLoadAssertions(beacon, analyticsPageLoad, path);
		Assert.assertEquals(beacon.eVars.get(10), analyticsPageLoad.getElementTextFromCss(CSS_RESULTS_NUM));
	}
	
}
