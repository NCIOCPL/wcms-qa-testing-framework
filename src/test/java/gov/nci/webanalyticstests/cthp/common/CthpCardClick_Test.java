package gov.nci.webanalyticstests.cthp.common;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;

import gov.nci.Utilities.ExcelManager;
import gov.nci.commonobjects.Card;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class CthpCardClick_Test extends AnalyticsTestClickBase {

	private final String TESTDATA_SHEET_HP = "CTHPHPCard";
	private final String TESTDATA_SHEET_PATIENT = "CTHPPatientCard";

	private String testDataFilePath;
	private Card card;
	private String currentUrl;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	public void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCTHPData");
	}

	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);
			currentUrl = driver.getCurrentUrl();
			card = new Card(driver);

			Actions action = new Actions(driver);
			action.pause(1000).perform();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building Related Resources page object.");
		}
	}

	// ==================== Test methods ==================== //

	// @Test(dataProvider = "CthpHpCard", groups = { "Analytics" })
	public void testCthpHpCardClick(String path, String audience, String cardTitle, String linkText, String cardPos) {
		System.out.println("Test CTHP Patient hp click: ");
		setupTestMethod(path);
		card.clickCardText(linkText);
		Beacon beacon = getBeacon();

		doCommonClassAssertions(beacon, cardTitle, linkText, cardPos);
	}

	// Test CTHP Patient Card click event
	@Test(dataProvider = "CthpPatientCard", groups = { "Analytics" })
	public void testCthpPatientCardClick(String path, String cardTitle, String linkText, String cardPos) {
		System.out.println("Test CTHP Patient Card click event (" + cardTitle + "):");
		setupTestMethod(path);

		try {
			card.clickCardText(linkText);
			Beacon beacon = getBeacon();

			doCommonClassAssertions(beacon, cardTitle, linkText, cardPos);
			logger.log(LogStatus.PASS, "Test CTHP Patient Card click event (" + cardTitle + ") passed.");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// ==================== Data providers ==================== //

	@DataProvider(name = "CthpHpCard")
	public Iterator<Object[]> getCthpHpCardData() {
		return getCthpCardData(TESTDATA_SHEET_HP);
	}

	@DataProvider(name = "CthpPatientCard")
	public Iterator<Object[]> getCthpPatientCardData() {
		return getCthpCardData(TESTDATA_SHEET_PATIENT);
	}

	/**
	 * Get a testable data object, filtered by audience
	 * 
	 * @param sheetName
	 * @return
	 */
	public Iterator<Object[]> getCthpCardData(String sheetName) {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();
		for (int rowNum = 2; rowNum <= excelReader.getRowCount(sheetName); rowNum++) {
			String path = excelReader.getCellData(sheetName, "Path", rowNum);
			String title = excelReader.getCellData(sheetName, "CardTitle", rowNum);
			String text = excelReader.getCellData(sheetName, "LinkText", rowNum);
			String index = excelReader.getCellData(sheetName, "CardPos", rowNum);

			Object ob[] = { path, title, text, index };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}

	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for this class.
	 * 
	 * @param beacon
	 * @param cardTitle
	 * @param linkText
	 * @param typePosition
	 *            formatted as cardType:positionNumber
	 */
	private void doCommonClassAssertions(Beacon beacon, String cardTitle, String linkText, String typePosition) {
		String testPath = beacon.props.get(60);

		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(27));
		Assert.assertEquals(beacon.linkName, "FeatureCardClick");
		Assert.assertEquals(beacon.props.get(57).trim(), cardTitle.trim());
		Assert.assertEquals(beacon.props.get(58).trim(), linkText.trim());
		Assert.assertEquals(beacon.props.get(59), typePosition);
		Assert.assertTrue(currentUrl.contains(testPath.substring(testPath.indexOf("cancer.gov"))));
	}

}
