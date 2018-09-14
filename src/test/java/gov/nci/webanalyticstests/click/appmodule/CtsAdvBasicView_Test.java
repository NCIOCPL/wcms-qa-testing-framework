package gov.nci.webanalyticstests.click.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.AdvanceSearchResults;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.clinicalTrial.pages.TrialDetailView;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.click.AnalyticsTestClickBase;

public class CtsAdvBasicView_Test extends AnalyticsTestClickBase {
		
	private final String PATH = "/about-cancer/treatment/clinical-trials/search/v";
	private final String FROM_ADVANCED_SEARCH = "?t=C4911&q=nivolumab&loc=0&tid=S1609&rl=2&id=NCI-2016-01041&pn=1&ni=10";
	private final String FROM_BASIC_SEARCH = "?q=ipilimumab&loc=1&z=20850&zp=100&rl=1&id=NCI-2016-01041&pn=1&ni=10";	
	private final String FROM_LISTING_PAGE = "?id=NCI-2016-01041&r=1";
	private final String FROM_CT_REDIRECT = "?id=NCT02834013&r=1";
	
	private TrialDetailView trialView;
	private Beacon beacon;
		
	
	@Test(groups = { "Analytics" })
	public void testTrialViewStartOverClick() {
		System.out.println("CTS View - start over click: ");
		getTrialDetailView(FROM_ADVANCED_SEARCH);
		trialView.clickStartOverNoNav();
		beacon = getBeacon();
		
		doCommonClassAssertions(beacon);
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_advanced|start over");
	}
	
	@Test(groups = { "Analytics" })
	public void testTrialViewOpenAllClick() {
		System.out.println("CTS View - open all sections click: ");
		getTrialDetailView(FROM_ADVANCED_SEARCH);
		trialView.clickOpenAll();
		beacon = getBeacon();
		
		doCommonClassAssertions(beacon);
		Assert.assertEquals(beacon.props.get(41), "clinical_trial|none|none|open-all");
	}

	@Test(groups = { "Analytics" })
	public void testTrialViewCloseAllClick() {
		System.out.println("CTS View - close all sections click: ");
		getTrialDetailView(FROM_ADVANCED_SEARCH);
		trialView.clickCloseAll();
		beacon = getBeacon();
		
		doCommonClassAssertions(beacon);
		Assert.assertEquals(beacon.props.get(41), "clinical_trial|none|none|close-all");
	}


	@Test(groups = { "Analytics" })
	public void testSectionExpandClick() {
		System.out.println("CTS View - expand one section click: ");
		getTrialDetailView(FROM_BASIC_SEARCH);
		trialView.clickCloseAll();
		trialView.clickSection("description");
		beacon = getBeacon();
		
		doCommonClassAssertions(beacon);
		Assert.assertEquals(beacon.props.get(41), "clinical_trial|trial-description|Description|expand");
	}
	
	/**
	 * Go to the trial view page and create a new TrialDetailView object..
	 * 
	 * @param queryParams
	 */
	private void getTrialDetailView(String queryParams) {
		try {
			driver.get(config.goHome() + PATH + queryParams);
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			trialView = new TrialDetailView(driver, chatPrompt);
		} catch (Exception ex) {
			Assert.fail("Error loading CTS Trial Detail View: " + PATH + queryParams);
			ex.printStackTrace();
		}
	}
	
	/**
	 * Shared Assert() calls for CtsAdvBasicView_Test
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

