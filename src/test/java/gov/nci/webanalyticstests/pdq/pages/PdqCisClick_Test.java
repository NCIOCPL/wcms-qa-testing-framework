package gov.nci.webanalyticstests.pdq.pages;

import java.util.ArrayList;
import java.util.Iterator;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.Utilities.ExcelManager;
import gov.nci.pdq.common.PdqRightNav;
import gov.nci.pdq.pages.PdqPage;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class PdqCisClick_Test extends AnalyticsTestClickBase {

	private PdqPage pdqPage;
	private PdqRightNav pdqRightNav;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsPDQData");
	}

	// Go to the page, build the page and nav objects, then pause to load
	// everything.
	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			pdqPage = new PdqPage(driver);
			pdqRightNav = pdqPage.getRightNav();
		} catch (Exception e) {
			Assert.fail("Error building PDQ page object.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	// Test Right Nav section click
	@Test(groups = { "Analytics" }, dataProvider = "RightNavData")
	public void testRightNavClick(String path, String linkName) {
		System.out.println("Test Right Nav current section click (" + linkName + "):");
		setupTestMethod(path);

		try {
			// We need to click the section link twice to ensure that we don't capture the
			// old GlobalLinkTrack beacon.
			pdqRightNav.clickSection(linkName);
			pdqRightNav.clickSection(linkName);
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "RightNavLink-");
			Assert.assertEquals(beacon.props.get(27), linkName);
			Assert.assertEquals(beacon.props.get(66), "pdq_" + linkName.toLowerCase());
			Assert.assertEquals(beacon.eVars.get(49), beacon.props.get(27));
			logger.log(LogStatus.PASS, "Test Right Nav section click (" + linkName + ") passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {}, e);
		}

	}

	// Test Toggle Link details click
	@Test(groups = { "Analytics" }, dataProvider = "ToggleData")
	public void testToggleLinkDetail(String path, String linkName) {
		System.out.println("Test Toggle Link details click (" + linkName + "):");
		setupTestMethod(path);

		try {
			pdqPage.clickPatientHPToggle();
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "GlobalLinkTrack");
			Assert.assertEquals(beacon.props.get(28), "www.cancer.gov" + path);
			logger.log(LogStatus.PASS, "Test Toggle Link details click (" + linkName + ") passed.");
		} catch (Exception e) {
			handleTestErrors(new Object() {}, e);
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "RightNavData")
	private Iterator<Object[]> getRightNavData() {
		return getPdqPageData("RightNav");
	}

	@DataProvider(name = "ToggleData")
	private Iterator<Object[]> getTogglevData() {
		return getPdqPageData("PatientHPToggle");
	}

	/**
	 * Get an iterator data object with path and link text values.*
	 * 
	 * @param dataSheet
	 *            to use
	 * @return path and link text strings
	 */
	private Iterator<Object[]> getPdqPageData(String dataSheet) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(dataSheet); rowNum++) {
			String path = excelReader.getCellData(dataSheet, "Path", rowNum);
			String sectionName = excelReader.getCellData(dataSheet, "LinkText", rowNum);
			Object ob[] = { path, sectionName };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

}