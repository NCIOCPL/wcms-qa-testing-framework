package gov.nci.webanalyticstests.click.appmodule;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.clinicalTrial.pages.BasicSearch;
import gov.nci.clinicalTrial.pages.SuppressChatPromptPageObject;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.click.AnalyticsTestClickBase;

public class CtsBasicSearch_Test extends AnalyticsTestClickBase {

	private BasicSearch basicSearch;
	private Beacon beacon;
	private Actions action;	
	
	@BeforeMethod(groups = { "Analytics" }) 
	// Run before each test method in this class
	public void setup() {
		driver.get(config.getPageURL("BasicClinicalTrialSearchURL"));
		try {
			// Create search page with chat prompt suppressed.					
			SuppressChatPromptPageObject chatPrompt = new SuppressChatPromptPageObject(driver, null);
			basicSearch = new BasicSearch(driver, chatPrompt);
			action = new Actions(driver);
		} catch (Exception e) {
			basicSearch = null;
			logger.log(LogStatus.ERROR, "Error creating Basic Search page.");
		}
	}

	@Test(groups = { "Analytics" })
	public void testBasicDisplay() {
		/* Do browser actions  **/ 
		System.out.println("Test \"Display\" click event for keyword.");
		driver.get(config.goHome());
		
		/* Get our beacon object **/ 		
		beacon = getBeacon();

		/* Do assertions and log result */ 
		Assert.assertTrue(beacon.hasEvent(37));
		Assert.assertEquals(beacon.props.get(4), "D=pev1");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.props.get(67), "D=pageName");
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|display");
		Assert.assertEquals(beacon.eVars.get(2), "english");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_basic");
		logger.log(LogStatus.PASS, "CTS Basic 'abandon' value test passed.");
	}
	
	
	@Test(groups = { "Analytics" })
	public void testBasicBegin() {
		/* Start entering values into a field  **/ 
		System.out.println("Test \"Begin\" click event.");
		basicSearch.setSearchKeyword("stomatitis");
		action.pause(1000).perform();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(38));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|start");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_basic");
		logger.log(LogStatus.PASS, "CTS Basic 'start' value test passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void testAbandonKeyword() {
		/* Enter a keyword field, then abandon the form by navigating away  **/ 
		System.out.println("Test \"Abandon\" click event for keyword.");
		basicSearch.setSearchKeyword("liver");
		action.pause(1000).perform();
		driver.get(config.goHome());
		beacon = getBeacon();

		Assert.assertTrue(beacon.hasEvent(40));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|q");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_basic");
		logger.log(LogStatus.PASS, "CTS Basic 'abandon' value test passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void testAbandonAge() {
		/* Enter an age field, then abandon the form by navigating away  **/ 
		System.out.println("Test \"Abandon\" click event for age.");		
		basicSearch.setSearchAge("55");
		action.pause(1000).perform();
		driver.get(config.goHome());
		beacon = getBeacon();

		Assert.assertTrue(beacon.hasEvent(40));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|a");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_basic");
		logger.log(LogStatus.PASS, "CTS Basic 'abandon' value test passed.");
	}
}

