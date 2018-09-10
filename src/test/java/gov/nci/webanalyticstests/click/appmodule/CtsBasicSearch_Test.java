package gov.nci.webanalyticstests.click.appmodule;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

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
	// TODO: fix action.pause() hanging issue
	
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
		/* Do browser actions and get our beacon object **/
		System.out.println("CTS \"Display\" click event:");
		driver.get(config.goHome());
		beacon = getBeacon();

		/* Do assertions and log result */ 
		DoCommonAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(37));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|display");
		logger.log(LogStatus.PASS, "CTS Basic 'abandon' value test passed.");
	}
	
	
	@Test(groups = { "Analytics" })
	public void testBasicStart() {
		System.out.println("CTS \"Start\" click event:");
		basicSearch.setSearchKeyword("canc");
		action.pause(1000).perform();
		beacon = getBeacon();
		
		DoCommonAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(38));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|start");
		logger.log(LogStatus.PASS, "CTS Basic 'start' value test passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void testBasicComplete() throws MalformedURLException, UnsupportedEncodingException {
		System.out.println("CTS \"Complete\" click event:");
		basicSearch.clickSearchButton();
		beacon = getBeacon();
		
		DoCommonAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(39));
		Assert.assertFalse(beacon.hasEvent(46));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|complete");
		logger.log(LogStatus.PASS, "CTS Basic 'complete' value test passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void testKeywordMatch() throws MalformedURLException, UnsupportedEncodingException {
		System.out.println("CTS \"Complete\" click event with keyword match:");
		basicSearch.setSearchKeyword("Ampulla of Vater Cancer");		
		basicSearch.clickSearchButton();
		beacon = getBeacon();
		
		DoCommonAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(39));
		Assert.assertTrue(beacon.hasEvent(46));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|complete");
		logger.log(LogStatus.PASS, "CTS \"Complete\" click event with keyword match test passed.");
	}	
	
	@Test(groups = { "Analytics" })
	public void testAbandonKeyword() {
		/* Enter the keyword field, then abandon the form by navigating away. **/ 
		System.out.println("CTS \"Abandon\" click event for keyword:");
		basicSearch.setSearchKeyword("Liver");
		action.pause(1000).perform();
		driver.get(config.goHome());
		beacon = getBeacon();

		DoCommonAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(40));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|q");
		logger.log(LogStatus.PASS, "CTS \"Abandon\" click event for keyword passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void testAbandonAge() {
		/* Enter the age field, then abandon the form by navigating away. **/ 
		System.out.println("CTS \"Abandon\" click event for age:");
		basicSearch.setSearchAge("55");
		action.pause(1000).perform();
		driver.get(config.goHome());
		beacon = getBeacon();

		DoCommonAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(40));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|a");
		logger.log(LogStatus.PASS, "CTS \"Abandon\" click event for age passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void testAbandonZip() {
		/* Enter the zip field, then abandon the form by navigating away. **/ 
		System.out.println("CTS \"Abandon\" click event for zip:");
		basicSearch.setSearchZip("20001");
		action.pause(1000).perform();
		driver.get(config.goHome());
		beacon = getBeacon();

		DoCommonAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(40));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|abandon|z");
		logger.log(LogStatus.PASS, "CTS \"Abandon\" click event for zipcode passed.");
	}

	@Test(groups = { "Analytics" })
	public void testErrorAge() {
		System.out.println("CTS \"Error\" click event for age:");
		basicSearch.setSearchAge("abc");
		basicSearch.setSearchKeyword("");
		beacon = getBeacon();

		DoCommonAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(41));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
		Assert.assertEquals(beacon.props.get(75), "a|Please enter a number between 1 and 120.");
		logger.log(LogStatus.PASS, "CTS \"Error\" click event for age passed.");
	}
	
	@Test(groups = { "Analytics" })
	public void testErrorZip() {
		System.out.println("CTS \"Error\" click event for zip:");
		basicSearch.setSearchZip("abcde");
		basicSearch.setSearchKeyword("");
		beacon = getBeacon();

		DoCommonAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(41));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
		Assert.assertEquals(beacon.props.get(75), "z|Please enter a valid 5 digit ZIP code.");
		logger.log(LogStatus.PASS, "CTS \"Error\" click event for zipcode passed.");
	}

	// TODO: fix Selenium TimeoutException on this test
	// @Test(groups = { "Analytics" })
	public void testErrorSubmit() throws MalformedURLException, UnsupportedEncodingException {
		System.out.println("CTS \"Error\" submit button click:");
		basicSearch.setSearchZip("abcde");
		basicSearch.clickSearchButton();
		beacon = getBeacon();

		DoCommonAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(41));
		Assert.assertEquals(beacon.props.get(74), "clinicaltrials_basic|error");
		Assert.assertEquals(beacon.props.get(75), "submit|attempted form submit with errors");
		logger.log(LogStatus.PASS, "CTS \"Error\" submit button click passed.");
	}
	
	/**
	 * Shared Assert() calls for CtsBasicSearch_Test
	 * @param beacon
	 */
	private void DoCommonAssertions(Beacon beacon) {
		Assert.assertTrue(beacon.suites.length > 0);
		Assert.assertTrue(beacon.channels.length() > 0);
		Assert.assertEquals(beacon.props.get(4), "D=pev1");
		Assert.assertEquals(beacon.props.get(8), "english");
		Assert.assertEquals(beacon.props.get(67), "D=pageName");
		Assert.assertEquals(beacon.eVars.get(2), "english");
		Assert.assertEquals(beacon.eVars.get(47), "clinicaltrials_basic");
	}
	
}

