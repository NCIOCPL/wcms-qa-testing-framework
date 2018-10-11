package gov.nci.webanalyticstests.dictionary.pages;

import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
		} catch (Exception e) {
			handleTestErrors(new Object() {
			}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "DictionaryGeneticsLoad")
	private Iterator<Object[]> getDictionaryPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}

}
