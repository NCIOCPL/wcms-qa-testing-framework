package gov.nci.webanalyticstests.clinicaltrial.pages;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;

import gov.nci.webanalytics.AnalyticsPageLoad;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class AdvancedSearchLoad_Test extends AnalyticsTestLoadBase {

	/**
	 * This test class covers Clinical Trial Advanced Search pages
	 */

	@Test(dataProvider = "AdvancedSearchPage", groups = { "Analytics" })
	public void testCTSAdvancedSearchPageLoad(String path, String type) {
		System.out.println("Test " + type + " page load:");
		driver.get(config.goHome() + path);

		try {
			AnalyticsPageLoad analyticsPageLoad = new AnalyticsPageLoad(driver);
			Beacon beacon = getBeacon();

			doCommonLoadAssertions(beacon, analyticsPageLoad, path);
			Assert.assertEquals(beacon.props.get(62), type);
			Assert.assertEquals(beacon.eVars.get(62), beacon.props.get(62));
			logger.log(LogStatus.PASS, type + " page load values passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error loading page in " + currMethod + "()");
		}
	}

	/**
	 * Get an iterator data object with path and search type Strings.
	 */
	@DataProvider(name = "AdvancedSearchPage")
	public Iterator<Object[]> getPathTypeData() {
		String path = "/about-cancer/treatment/clinical-trials/advanced-search";
		String type = "Clinical Trials: Advanced";

		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		Object ob[] = { path, type };
		myObjects.add(ob);

		return myObjects.iterator();
	}

}