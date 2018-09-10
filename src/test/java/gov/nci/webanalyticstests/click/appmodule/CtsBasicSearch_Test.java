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
	// TODO: add wait() to capture begin / abandon events
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
	public void testBasicBegin() {
		/* Do browser actions  **/ 
		basicSearch.setSearchKeyword("stomatitis");
		action.pause(500).perform();
		
		/* Get our beacon object **/ 
		beacon = getBeacon();
		
		/* Do assertions and log result */ 
		Assert.assertTrue(beacon.hasEvent(38));
		Assert.assertFalse(beacon.hasEvent(40));
		Assert.assertTrue(beacon.hasProp(74, "clinicaltrials_basic|start"));
		Assert.assertTrue(beacon.haseVar(47, "clinicaltrials_basic"));
		logger.log(LogStatus.PASS, "CTS Basic 'start' value test passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void abandonKeyword() {
		/* Enter a keyword field, then abandon the form by navigating away  **/ 
		basicSearch.setSearchKeyword("liver");
		action.pause(500).perform();
		driver.navigate().to(config.getPageURL("HomePage"));
		beacon = getBeacon();

		/* Verify that the expected values are tracked */
		Assert.assertTrue(beacon.hasEvent(40));
		Assert.assertTrue(beacon.hasProp(74, "clinicaltrials_basic|abandon|q"));
		Assert.assertTrue(beacon.haseVar(47, "clinicaltrials_basic"));
		logger.log(LogStatus.PASS, "CTS Basic 'abandon' value test passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void abandonAge() {
		/* Enter an age field, then abandon the form by navigating away  **/ 
		basicSearch.setSearchAge("55");
		action.pause(500).perform();
		driver.navigate().to(config.getPageURL("HomePage"));
		beacon = getBeacon();

		/* Verify that the expected values are tracked */
		Assert.assertTrue(beacon.hasEvent(40));
		Assert.assertTrue(beacon.hasProp(74, "clinicaltrials_basic|abandon|a"));
		Assert.assertTrue(beacon.haseVar(47, "clinicaltrials_basic"));
		logger.log(LogStatus.PASS, "CTS Basic 'abandon' value test passed.");
	}
}

