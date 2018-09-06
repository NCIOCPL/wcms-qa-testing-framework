package gov.nci.webanalyticstests.load;

import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import gov.nci.Utilities.ConfigReader;
import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;

public class BlogPostPage_Test extends AnalyticsTestLoadBase {

	/**
	 * The following page / content types are covered by this test class:
	 * - Blog Post Page (English and Spanish)
	 */
	
	private AnalyticsPageLoad analyticsPageLoad;
	private Beacon beacon;	
	private String testDataFilePath;
	private final String TESTDATA_SHEET_NAME = "BlogPostPage";
	
	@BeforeClass(groups = { "Analytics" }) 
	@Parameters({ "environment" })
	public void setup(String environment) {
		config = new ConfigReader(environment);
		testDataFilePath = config.getProperty("AnalyticsPageLoadData");
	}
	
	/// BlogPost page loads return expected values
	@Test(dataProvider = "BlogPostPageLoad", groups = { "Analytics" })
	public void testBlogPostPageLoad(String path, String contentType) {
		try {
			driver.get(config.goHome() + path);
			analyticsPageLoad = new AnalyticsPageLoad(driver);
			System.out.println(contentType + " load event (" + analyticsPageLoad.getLanguageName() + "):");
			beacon = getBeacon();
			DoCommonLoadAssertions(beacon, analyticsPageLoad, path);
			Assert.assertEquals(beacon.eVars.get(48), analyticsPageLoad.getMetaIsPartOf() + " Viewer");
			logger.log(LogStatus.PASS, contentType + " load values are correct.");
		}
		catch (Exception e) {
			Assert.fail("Error loading " + contentType);
			e.printStackTrace();
		}
	}

	@DataProvider(name = "BlogPostPageLoad")
	public Iterator<Object[]> getBlogPostPageLoadData() {
		return getPathContentTypeData(testDataFilePath, TESTDATA_SHEET_NAME);
	}
	
}