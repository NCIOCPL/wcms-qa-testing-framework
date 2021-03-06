package gov.nci.webanalyticstests.error.pages;

import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class ErrorLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "ErrorPage";

	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsErrorPgData");
	}

	// ==================== Test methods ==================== //

	/// Test Error Page load event
	@Test(dataProvider = "ErrorPageLoad", groups = { "Analytics" })
	public void testErrorPageLoad(String path, String contentType) {
		System.out.println("Path: " + path + ", Type: " + contentType);
		driver.get(config.goHome() + path);

		try {
			Beacon beacon = getBeacon();

			// Error pages do not share the rest of the common pageload valules
			Assert.assertTrue(beacon.hasSuite("nciglobal", driver.getCurrentUrl()), "suite");
			Assert.assertTrue(beacon.hasEvent(47), "event47");
			Assert.assertEquals(beacon.channels, "Error Pages", "channel");
			Assert.assertEquals(beacon.getPageType(), "errorpage", "pageType");
			Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8), "eVar2");
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "ErrorPageLoad")
	private Iterator<Object[]> getTopicPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}