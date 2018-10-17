package gov.nci.webanalyticstests.r4r.pages;

import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class R4RPageLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "R4RPageLoad";

	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsR4RData");
	}

	// ==================== Test methods ==================== //

	/// Test R4R Pages Load event
	@Test(dataProvider = "R4RPageLoad", groups = { "Analytics" })
	public void testR4RPageLoad(String path, String type) {
		System.out.println("Page type: " + type);
		driver.get(config.goHome() + path);

		try {
			Beacon beacon = getBeacon();
			doCommonClassAssertions(beacon);
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "R4RPageLoad")
	private Iterator<Object[]> getR4RPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared assertions for all tests in this class.
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		Assert.assertTrue(beacon.hasSuite("nciglobal", driver.getCurrentUrl()), "Common missing Global Suite");
		Assert.assertEquals(beacon.channels, "Research");
		Assert.assertTrue(beacon.hasEvent(1), "Missing event1");
		Assert.assertTrue(beacon.hasEvent(47), "Missing event47");
		Assert.assertEquals(beacon.props.get(6), "Resources for Researchers");
		Assert.assertEquals(beacon.props.get(10), "Resources for Researchers - National Cancer Institute");
		Assert.assertEquals(beacon.props.get(44), "Research Landing Page");
		Assert.assertEquals(beacon.eVars.get(44), beacon.props.get(44));
	}
}
