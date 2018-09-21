package gov.nci.webanalyticstests.dictionary.pages;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class DictTermsLoad_Test extends DictionaryBase {

	/**
	 * The following page / content types are covered by this test class:
	 * - Cancer Terms Dictionary (English and Spanish)
	 * - Drug Dictionary (English)
	 * - Genetics Terms Dictionary (English)
	 */		
	
	// TODO: genetics/drug defintiions
	// TODO: pageload from dictionary search
	// TODO: handle prop/eVar16 logic:
	// TODO: prop / evar16
	/***
	 * Other values needed:
		prop16 / eVar 16:
		<dictionary type>|<language>|<term>|<id>
	 */
	private AnalyticsPageLoad analyticsPageLoad;
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "DictionaryPage";
	
	@BeforeClass(groups = { "Analytics" }) 
	public void setup() {
		testDataFilePath = config.getProperty("AnalyticsDictData");
	}
	
	/// Dictionary page loads return expected values
	@Test(dataProvider = "DictionaryPageLoad", groups = { "Analytics" })
	public void testDictionaryPageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println(contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			Beacon beacon = getBeacon();
			
			path = getDictionaryPath(path);
			doCommonLoadAssertions(beacon, analyticsPageLoad, path);
			//Assert.assertTrue(beacon.props.get(16).length() > 0);
			//Assert.assertTrue(beacon.eVars.get(16).length() > 0);			
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	@DataProvider(name = "DictionaryPageLoad")
	public Iterator<Object[]> getDictionaryPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}
	
	
}