package gov.nci.webanalyticstests.r4r.pages;

import org.testng.Assert;
import org.testng.annotations.Test;

import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.r4r.R4RLoadBase;

public class R4RHomePageLoad_Test extends R4RLoadBase {

	private final String PATH = "/research/resources";

	// ==================== Test methods ==================== //

	/// Test R4R Pages Load event
	@Test(groups = { "Analytics" })
	public void testR4RPageLoad() {
		System.out.println("Path: " + PATH);
		System.out.println("Page type: R4R Home");
		driver.get(config.goHome() + PATH);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared assertions for all tests in this class.
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		Assert.assertTrue(beacon.hasSuite("nciglobal", driver.getCurrentUrl()), "Common missing Global Suite");
		Assert.assertEquals(beacon.channels, "Research", "channel");
		Assert.assertTrue(beacon.hasEvent(1), "Missing event1");
		Assert.assertTrue(beacon.hasEvent(47), "Missing event47");
		Assert.assertEquals(beacon.props.get(6), "Resources for Researchers", "prop6");
		Assert.assertEquals(beacon.props.get(10), "Resources for Researchers - National Cancer Institute", "prop10");
		Assert.assertEquals(beacon.props.get(44), "RandD Resources", "prop44");
		Assert.assertEquals(beacon.eVars.get(44), beacon.props.get(44), "eVar44");
	}
}
