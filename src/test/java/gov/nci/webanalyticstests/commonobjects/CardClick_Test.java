package gov.nci.webanalyticstests.commonobjects;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;

import gov.nci.Utilities.ExcelManager;
import gov.nci.commonobjects.Card;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class CardClick_Test extends AnalyticsTestClickBase {

	private Card card;

	private String currentUrl;
	private String testDataFilePath;

	private final String TESTDATA_SHEET_NAME = "HomeLandingCards";

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	private void setupClass() {
		testDataFilePath = config.getProperty("AnalyticsCommonData");
	}

	private void setupTestMethod(String path) {
		try {
			driver.get(config.goHome() + path);

			this.currentUrl = driver.getCurrentUrl();
			this.card = new Card(driver);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building Related Resources page object.");
		}
	}

	// ==================== Test methods ==================== //

	@Test(dataProvider = "CardClickData", groups = { "Analytics" })
	public void testAllCardClicks(String path, String cardType, String titleSel, String linkSel, int index) {
		System.out.println("Test home Primary Feature Card click: ");
		setupTestMethod(path);
		int pos = index + 1;

		String titleText = card.getCardText(titleSel, 0);
		String linkText = card.getCardText(linkSel, 0);
		String cardPos = cardType + ":" + pos;
		card.clickCardLink(linkSel, 0);

		// getCardClickBeacon(titleSel, linkSel, 0);
		Beacon beacon = getBeacon();

		doCommonClassAssertions(beacon, cardPos, titleText, linkText);
	}

	// ==================== Data providers ==================== //

	/**
	 * Get an iterator data object with path and content type Strings, filtered by a
	 * given value and column.
	 * 
	 * @return expected strings
	 */
	@DataProvider(name = "CardClickData")
	private Iterator<Object[]> getCardClickData() {
		ExcelManager excelReader = new ExcelManager(testDataFilePath);
		ArrayList<Object[]> myObjects = new ArrayList<Object[]>();

		for (int rowNum = 2; rowNum <= excelReader.getRowCount(TESTDATA_SHEET_NAME); rowNum++) {
			String path = excelReader.getCellData(TESTDATA_SHEET_NAME, "Path", rowNum);
			String cardType = excelReader.getCellData(TESTDATA_SHEET_NAME, "CardType", rowNum);
			String titleSel = excelReader.getCellData(TESTDATA_SHEET_NAME, "TitleSelector", rowNum);
			String linkSel = excelReader.getCellData(TESTDATA_SHEET_NAME, "LinkSelector", rowNum);
			int index = excelReader.getCellIntegerData(TESTDATA_SHEET_NAME, "CardPos", rowNum);
			Object ob[] = { path, cardType, titleSel, linkSel, index };
			myObjects.add(ob);
		}
		return myObjects.iterator();
	}
	// ==================== Common assertions ==================== //

	/**
	 * Shared Assert() calls for CardClick_Test
	 * 
	 * @param typePosition
	 *            [Card Type]:[position number]
	 */
	private void doCommonClassAssertions(Beacon beacon, String typePosition, String titleText, String linkText) {
		String testPath = beacon.props.get(60);

		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(27));
		Assert.assertEquals(beacon.linkName, "FeatureCardClick");
		Assert.assertEquals(beacon.props.get(57).trim(), titleText.trim());
		Assert.assertEquals(beacon.props.get(58).trim(), linkText.trim());
		Assert.assertEquals(beacon.props.get(59), typePosition);
		Assert.assertTrue(currentUrl.contains(testPath.substring(testPath.indexOf("cancer.gov"))));
	}

}
