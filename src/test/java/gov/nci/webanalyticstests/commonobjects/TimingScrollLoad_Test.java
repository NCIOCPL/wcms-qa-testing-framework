package gov.nci.webanalyticstests.commonobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class TimingScrollLoad_Test extends AnalyticsTestLoadBase {

	private final String REGEX_PAGE_SCROLL = "^\\d{1,3}pct\\|\\d{1,3}pct\\|\\d{1,5}px\\|\\/.*$"; // 30pct|30pct|3215px|/research
	private final String REGEX_PERCENT_VIEWED = "^\\d{1,3}\\|\\d{1,3}$"; // 26|0
	private final String PATH = "/news-events";

	private AnalyticsMetaData analyticsMetaData;

	// ==================== Test methods ==================== //

	/// Test Page Load event after no scrolling
	@Test(groups = { "Analytics" })
	public void testNoScrollPageLoad() {
		System.out.println("Test Page Load event after no scrolling :");
		driver.get(config.goHome());
		driver.navigate().refresh();

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, analyticsMetaData);
			logger.log(LogStatus.PASS, "Test Page Load event after no scrolling passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	/// Test Page Load event after scrolling to bottom
	@Test(groups = { "Analytics" })
	public void testFullScrollPageLoad() {
		System.out.println("Test Page Load event after scrolling to bottom:");
		driver.get(config.goHome());
		// scroll to bottom
		driver.navigate().refresh();

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, analyticsMetaData);
			logger.log(LogStatus.PASS, "Test Page Load event after scrolling to bottom passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	/// Test engagement tracking on page load
	// @Test(groups = { "Analytics" })
	public void testEngagement() {
		System.out.println("Test engagement tracking on page load:");
		driver.get(config.goHome());

		try {
			// TODO
			// Click and scroll around page
			// Refresh
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon, analyticsMetaData);
			logger.log(LogStatus.PASS, "Test engagement tracking on page load passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared assertions for all tests in this class.
	 * 
	 * @param beacon
	 * @param analyticsMetaData
	 */
	private void doCommonClassAssertions(Beacon beacon, AnalyticsMetaData analyticsMetaData) {
		doCommonLoadAssertions(beacon, analyticsMetaData, PATH);

		Assert.assertTrue(beacon.props.get(48).matches(REGEX_PAGE_SCROLL));
		Assert.assertTrue(beacon.props.get(64).matches(REGEX_PERCENT_VIEWED));
	}

}
