package gov.nci.webanalyticstests.dictionary.common;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import gov.nci.commonobjects.Card;
import gov.nci.dictionary.DictObjectBase;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class DictSearchClick_Test extends AnalyticsTestClickBase {

	String pathTermsEn = "/publications/dictionaries/cancer-terms";
	String pathTermsEs = "/espanol/publicaciones/diccionario";
	String pathDrug = "/publications/dictionaries/cancer-drug";
	String pathGenetics = "/publications/dictionaries/genetics-dictionary";

	private Beacon beacon;
	private Actions action;
	private String currentUrl;

//	TermsDictionarySearch
//	event2
//
//		D=pev1
//	prop8
//	english
//	prop11
//	dictionary_terms
//	prop22
//	breast
//	prop24
//	starts with
//
//	eVar11
//	dictionary_terms
//	eVar13
//	+1
//	eVar26
//	starts with
	
	
	@Test(groups = { "Analytics" })
	public void testCancerTermsEnStartsSearch() {
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();		
		System.out.println("Cancer Terms English Search click: ");
		
		try {
			driver.get(config.goHome() + pathTermsEn);
			DictObjectBase dict = new DictObjectBase(driver);
			dict.SubmitSearchTerm(".dictionary-search-input", "breast");
			Beacon beacon = getBeacon();
			
			doCommonClickAssertions(beacon);
			Assert.assertTrue(beacon.hasEvent(2));
			Assert.assertEquals(beacon.linkName, "TermsDictionarySearch");
			Assert.assertEquals(beacon.props.get(11), "dictionary_terms");
			Assert.assertEquals(beacon.props.get(8), "english");
		} catch (Exception ex) {
			Assert.fail("Error clicking element in " + curMethod + "()");
		}

	}

	@Test(groups = { "Analytics" })
	public void testCancerTermsEnContainsSearch() {
		String curMethod = new Object(){}.getClass().getEnclosingMethod().getName();		
		System.out.println("Cancer Terms English Search click: ");
		
		try {
			driver.get(config.goHome() + pathTermsEn);
			DictObjectBase dict = new DictObjectBase(driver);
			dict.selectContains();
			dict.SubmitSearchTerm(".dictionary-search-input", "breast");
			Beacon beacon = getBeacon();
			
			doCommonClickAssertions(beacon);
			Assert.assertEquals(beacon.props.get(11),  "dictionary_terms");
			Assert.assertEquals(beacon.props.get(8), beacon.eVars.get(2));
		} catch (Exception ex) {
			Assert.fail("Error clicking element in " + curMethod + "()");
		}

	}
	/**
	 * Shared Assert() calls for CardClick_Test
	 * 
	 * @param cardTitle
	 * @param linkText
	 * @param typePosition
	 *            formatted as cardType:positionNumber
	 */
	private void doCommonClassAssertions(String cardTitle, String linkText, String typePosition) {
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
