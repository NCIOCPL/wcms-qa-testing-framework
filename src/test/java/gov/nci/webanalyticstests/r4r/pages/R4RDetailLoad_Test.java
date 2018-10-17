package gov.nci.webanalyticstests.r4r.pages;

import org.testng.annotations.Test;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;

public class R4RDetailLoad_Test extends R4RLoadBase {

	private final String LOAD_PATH = "/research/resources/resource/3";

	private AnalyticsMetaData analyticsMetaData;

	// ==================== Test methods ==================== //

	/// Test R4R Detail Page Load event
	@Test(groups = { "Analytics" })
	public void testR4RDetailPageLoad() {
		driver.get(config.goHome() + LOAD_PATH);
		driver.navigate().refresh();

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);

			Beacon beacon = getBeacon();
			doCommonLoadAssertions(beacon, analyticsMetaData, LOAD_PATH);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

}
