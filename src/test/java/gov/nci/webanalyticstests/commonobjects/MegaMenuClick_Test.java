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

	/**
	 * Overload setupTestMethod() without a passed path.
	 */
	private void setupTestMethod() {
		setupTestMethod("");
	}

	// ==================== Test methods ==================== //

	/// Megamenu click returns the expected general/shared values
	@Test(groups = { "Analytics" })
	public void testMMGeneral() {

		setupTestMethod();
		try {
			megaMenu.clickMMBarEn();
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			logger.log(LogStatus.PASS, "MegaMenu gen value test passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEn() {

		setupTestMethod();
		try {
			megaMenu.clickMMBarEn();
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26));
			Assert.assertEquals(beacon.props.get(8), "english");
			logger.log(LogStatus.PASS, "MegaMenu top level click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Spanish menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEs() {

		setupTestMethod("/espanol");
		try {
			megaMenu.clickMMBarEs();
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26));
			Assert.assertEquals(beacon.props.get(8), "spanish");
			logger.log(LogStatus.PASS, "MegaMenu Spanish top level click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// MegaMenu subnav header click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavHeaderClick() {
		setupTestMethod();

		try {
			megaMenu.clickMMSubnavHeader();
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26));
			Assert.assertEquals(beacon.linkName, "MegaMenuClick");
			logger.log(LogStatus.PASS, "Subnav header click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// MegaMenu subnav link click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavLiClick() {

		setupTestMethod();
		try {
			megaMenu.clickMMSubnavLi();
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(26));
			Assert.assertEquals(beacon.props.get(53), "About Cancer");
			Assert.assertEquals(beacon.props.get(54), "Understanding Cancer");
			Assert.assertEquals(beacon.props.get(55), "What Is Cancer");
			Assert.assertEquals(beacon.eVars.get(53), "About Cancer");
			logger.log(LogStatus.PASS, "Expaned subnav title click passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Expanding the mobile megamenu returns the expected values
	@Test(groups = { "Analytics" })
	public void testMegaMenuMobileReveal() {
		setupTestMethod();

		try {
			megaMenu.revealMegaMenuMobile();
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(28));
			logger.log(LogStatus.PASS, "Expaned mobile mega menu passed");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/// Expanding the desktop megamenu returns the expected values
	/// @Test(groups = { "Analytics" })
	public void testMegaMenuDesktopReveal() {
		setupTestMethod();

		try {
			megaMenu.revealMegaMenuDesktop();
			Beacon beacon = getBeacon();

			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.linkName, "MegaMenuMobileReveal");
			Assert.assertTrue(beacon.hasEvent(28));
			Assert.assertFalse(beacon.hasEvent(26));
			logger.log(LogStatus.PASS, "MegaMenu expansion passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	/**
	 * Get an iterator data object with path, types, selectors, and position for
	 * Card objects, filtered by content type.
	 * 
	 * @param filter
	 * @return collection of card info
	 */
	@DataProvider(name = "MegaMenuData")
	private Iterator<Object[]> getMegaMenuData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String contentType = excelReader.getCellData(TESTDATA_SHEET_NAME, "ContentType", rowNum);

			// if (contentType.equalsIgnoreCase(filter)) {
			String path = excelReader.getCellData(TESTDATA_SHEET_NAME, "Path", rowNum);
			String cardType = excelReader.getCellData(TESTDATA_SHEET_NAME, "CardType", rowNum);
			String titleSel = excelReader.getCellData(TESTDATA_SHEET_NAME, "TitleSelector", rowNum);
			String linkSel = excelReader.getCellData(TESTDATA_SHEET_NAME, "LinkSelector", rowNum);
			int index = excelReader.getCellIntegerData(TESTDATA_SHEET_NAME, "CardPos", rowNum);
			Object ob[] = { path, cardType, titleSel, linkSel, index };
			myObjects.add(ob);
			// }
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
