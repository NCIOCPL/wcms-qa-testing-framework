package gov.nci.webanalyticstests.commonobjects;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;

import gov.nci.Utilities.ExcelManager;
import gov.nci.commonobjects.MegaMenu;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class MegaMenuClick_Test extends AnalyticsTestClickBase {

	private final String TESTDATA_SHEET_NAME = "MegaMenuInfo";

	private MegaMenu megaMenu;
	private String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCommonData");
	}

	/**
	 * Navigate to a page and create a new MegaMenu object.
	 * 
	 * @param path
	 */
	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			megaMenu = new MegaMenu(driver, "");
		} catch (Exception e) {
			Assert.fail("Error creating MegaMenu object at path: " + path);
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/// Test MegaMenu Nav Group click event
	@Test(dataProvider = "NavGroupData", groups = { "Analytics" })
	public void testMegaMenuNavGroup(String path, String navGroup, String language) {

		System.out.println("Test MegaMenu Nav Group click event at " + path + ":");
		setupTestMethod(path);

		try {
			megaMenu.clickMMBarEn();
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			logger.log(LogStatus.PASS, "Test MegaMenu Nav Group click event at" + path + " passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "NavGroupData")
	private Iterator<Object[]> getMMNavData() {
		return getMegaMenuData("NavGroup");
	}

	/**
	 * Get an iterator data object with path, types, selectors, and position for
	 * Card objects, filtered by content type.
	 * 
	 * @param returnValues
	 *            specifies which collection of values we need for testing.
	 * @return
	 */
	private Iterator<Object[]> getMegaMenuData(String returnValues) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {

			String path = excelReader.getCellData(TESTDATA_SHEET_NAME, "Path", rowNum);
			String navGroup = excelReader.getCellData(TESTDATA_SHEET_NAME, "NavGroup", rowNum);
			String subNavGroup = excelReader.getCellData(TESTDATA_SHEET_NAME, "SubNavGroup", rowNum);
			String listItem = excelReader.getCellData(TESTDATA_SHEET_NAME, "ListItem", rowNum);
			String language = excelReader.getCellData(TESTDATA_SHEET_NAME, "Language", rowNum);

			if (returnValues == "Path") {
				Object obj[] = { path, language };
				myObjects.add(obj);
			} else if (returnValues == "NavGroup") {
				Object obj[] = { path, navGroup, language };
				myObjects.add(obj);
			} else if (returnValues == "SubNav") {
				Object obj[] = { path, navGroup, subNavGroup, language };
				myObjects.add(obj);
			} else {
				Object obj[] = { path, navGroup, subNavGroup, listItem, language };
				myObjects.add(obj);
			}

		}
		return myObjects.iterator();

	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);

		Assert.assertEquals(beacon.linkName, "MegaMenuClick");
		Assert.assertTrue(beacon.channels.length() > 0);
	}

}
