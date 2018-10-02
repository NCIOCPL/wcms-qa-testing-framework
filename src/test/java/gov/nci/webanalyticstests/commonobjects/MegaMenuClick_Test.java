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
	private String currentUrl;

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
			currentUrl = driver.getCurrentUrl();
			megaMenu = new MegaMenu(driver, "");
		} catch (Exception e) {
			Assert.fail("Error creating MegaMenu object at path: " + path);
			e.printStackTrace();
		}
	}

	// Path NavGroup SubNavGroup ListItem Language
	// / About Cancer | Understanding Cancer | Cancer Statistics | english
	// /espanol/investigacion | Tipos de cáncer | Tipos comunes de cáncer Linfoma | spanish

	// ==================== Test methods ==================== //

	/// Test MegaMenu Nav Group click event
	@Test(dataProvider = "NavGroupData", groups = { "Analytics" })
	public void testMegaMenuNavGroup(String path, String navGroup, String lang) {
		System.out.println("Test MegaMenu Nav Group click event at " + path + " (" + lang + "):");
		setupTestMethod(path);

		try {
			//megaMenu.clickMMBar();
			megaMenu.clickMMItemByText(navGroup);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26));
			Assert.assertEquals(beacon.linkName, "MegaMenuClick");
			Assert.assertEquals(beacon.props.get(8), lang);
			Assert.assertEquals(beacon.props.get(53), navGroup);
			Assert.assertEquals(beacon.props.get(54), navGroup);
			Assert.assertEquals(beacon.props.get(55), navGroup);

			logger.log(LogStatus.PASS, "Test MegaMenu Nav Group click event at " + path + " (" + lang + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}


	/// Test MegaMenu Nav Group click event
	// @Test(dataProvider = "SubNavGroupData", groups = { "Analytics" })
	public void testMegaMenuSubNavGroup(String path, String navGroup, String subNavGroup, String lang) {
		System.out.println("Test MegaMenu Nav Group click event at " + path + " (" + lang + "):");
		setupTestMethod(path);

		try {
			//megaMenu.clickMMBar();
			megaMenu.revealMegaMenuDesktop();
			megaMenu.clickMMItemByText(subNavGroup);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26));
			Assert.assertEquals(beacon.linkName, "MegaMenuClick");
			Assert.assertEquals(beacon.props.get(8), lang);
			Assert.assertEquals(beacon.props.get(53), navGroup);
			Assert.assertEquals(beacon.props.get(54), subNavGroup);
			Assert.assertEquals(beacon.props.get(55), subNavGroup);

			logger.log(LogStatus.PASS, "Test MegaMenu Nav Group click event at " + path + " (" + lang + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}
	
	/**
	/// MegaMenu subnav header click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavHeaderClick() {		
		megaMenu.clickMMSubnavHeader();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(26));
		Assert.assertEquals(beacon.linkName, "MegaMenuClick");
		logger.log(LogStatus.PASS, "Subnav header click passed.");
	}
	
	/// MegaMenu subnav link click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavLiClick() {
		megaMenu.clickMMSubnavLi();
		beacon = getBeacon();
		
		doCommonClassAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(26));
		Assert.assertEquals(beacon.props.get(53), "About Cancer");
		Assert.assertEquals(beacon.props.get(54), "Understanding Cancer");
		Assert.assertEquals(beacon.props.get(55), "What Is Cancer");
		Assert.assertEquals(beacon.eVars.get(53), "About Cancer");
		logger.log(LogStatus.PASS, "Expaned subnav title click passed.");
	}
	
	/// Expanding the mobile megamenu returns the expected values
	@Test(groups = { "Analytics" })
	public void testMegaMenuMobileReveal() {
		megaMenu.revealMegaMenuMobile();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(28));
		logger.log(LogStatus.PASS, "Expaned mobile mega menu passed");
	}
	
	/// Expanding the desktop megamenu returns the expected values
	@Test(groups = { "Analytics" })
	public void testMegaMenuDesktopReveal() {
		megaMenu.revealMegaMenuDesktop();
		beacon = getBeacon();
		
		Assert.assertTrue(beacon.hasEvent(28));
		Assert.assertFalse(beacon.hasEvent(26));
		logger.log(LogStatus.PASS, "MegaMenu expansion passed.");
	}
	**/
	
	// ==================== Data providers ==================== //

	@DataProvider(name = "NavGroupData")
	private Iterator<Object[]> getMMNavData() {
		return getMegaMenuData("NavGroup");
	}

	@DataProvider(name = "SubNavGroupData")
	private Iterator<Object[]> getMMSubNavData() {
		return getMegaMenuData("SubNavGroup");
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
			} else if (returnValues == "SubNavGroup") {
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
		Assert.assertEquals(beacon.eVars.get(2), beacon.props.get(8));
		Assert.assertTrue(currentUrl.contains(beacon.props.get(56)));
		Assert.assertEquals(beacon.eVars.get(53), beacon.props.get(53));

	}

}
