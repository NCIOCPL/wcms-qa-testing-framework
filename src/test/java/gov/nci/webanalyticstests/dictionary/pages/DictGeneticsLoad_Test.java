package gov.nci.webanalyticstests.dictionary.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsMetaData;
import gov.nci.webanalytics.Beacon;

public class DictGeneticsLoad_Test extends DictionaryLoadBase {

	private final String TESTDATA_SHEET_NAME = "TermsPageGenetics";

	private AnalyticsMetaData analyticsMetaData;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsDictData");
	}

	// ==================== Test methods ==================== //

	/// Test Genetics Dictionary Page load event
	@Test(dataProvider = "DictionaryGeneticsLoad", groups = { "Analytics" })
	public void testGeneticsDictionaryPageLoad(String path, String contentType) {
		System.out.println("Test Genetics Dictionary Page load event at " + path + ":");
		driver.get(config.goHome() + path);

		try {
			analyticsMetaData = new AnalyticsMetaData(driver);
			Beacon beacon = getBeacon();

			String dictPath = getDictionaryPath(path);
			doCommonLoadAssertions(beacon, analyticsMetaData, dictPath);
			logger.log(LogStatus.PASS, "Test Genetics Dictionary Page load event at " + path + " passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "DictionaryGeneticsLoad")
	public Iterator<Object[]> getDictionaryPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}
