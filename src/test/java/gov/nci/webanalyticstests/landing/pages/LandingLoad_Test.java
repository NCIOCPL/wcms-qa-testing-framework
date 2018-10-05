package gov.nci.webanalyticstests.landing.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class LandingLoad_Test extends AnalyticsTestLoadBase {

	private final String TESTDATA_SHEET_NAME = "LandingPage";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	public void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}

	// ==================== Test methods ==================== //

	/// Test Landing Page load event
	@Test(dataProvider = "LandingPageLoad", groups = { "Analytics" })
	public void testLandingPageLoad(String path, String contentType) {
		System.out.println("Test Landing Page load event:");
		driver.get(config.goHome() + path);

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);
			Beacon beacon = getBeacon();

			doCommonLoadAssertions(beacon, analyticsMetaData, path);
			logger.log(LogStatus.PASS, "Test Landing Page load event passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "LandingPageLoad")
	public Iterator<Object[]> getLandingPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}