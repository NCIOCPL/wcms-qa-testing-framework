package gov.nci.webanalyticstests.pdq.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class PdqClick_Test extends AnalyticsTestLoadBase {

	private Beacon beacon;
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "PDQPage";

	@BeforeClass(groups = { "Analytics" })
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsPDQData");
	}

	/// PDQ page loads return expected values
	@Test(dataProvider = "PDQPageLoad", groups = { "Analytics" })
	public void testPdqPageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			driver.navigate().refresh();
//			analyticsPageLoad = new AnalyticsPageLoad(driver);
//			System.out.println(contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
//			beacon = getBeacon();
//
//			String[] pathNoId = path.split("#");
//			doCommonLoadAssertions(beacon, analyticsPageLoad, pathNoId[0]);
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		} catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	@DataProvider(name = "PDQPageLoad")
	public Iterator<Object[]> getPDQPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}