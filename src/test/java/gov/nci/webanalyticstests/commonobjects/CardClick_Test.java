package gov.nci.webanalyticstests.commonobjects;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.Card;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class CardClick_Test extends AnalyticsTestClickBase {

	private Card card;
	private Beacon beacon;
	private String titleText;
	private String linkText;

	private final String SEL_HOME_PRIMARY_FEATURE = ".feature-primary .feature-card";
	private final String SEL_HOME_SECONDARY_FEATURE = ".feature-secondary .feature-card";
	private final String SEL_HOME_GUIDE = ".guide-card .card";
	private final String SEL_HOME_MULTIMEDIA = ".multimedia div[class*=feature-card]";
	private final String SEL_HOME_THUMB = ".card-thumbnail";

	@BeforeMethod(groups = { "Analytics" })
	public void setupBlogPost() {
		try {
			this.card = new Card(driver);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building Related Resources page object.");
		}
	}

	@Test(groups = { "Analytics" })
	public void testHomePrimaryFeatureClick() {
		try {
			System.out.println("Test home primary feature card click: ");
			driver.get(config.goHome());
			String linkAndTitle = SEL_HOME_PRIMARY_FEATURE + " h3";
			getCardClickBeacon(linkAndTitle, linkAndTitle);

			doCommonClassAssertions();
			Assert.assertEquals(beacon.props.get(59), "Feature:1");
		} catch (Exception e) {
			Assert.fail("Error clicking home primary feature card.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" })
	public void testHomeGuideCardClick() {
		try {
			System.out.println("Test home guide card click: ");
			driver.get(config.goHome());
			String title = SEL_HOME_GUIDE + " h2";
			String link = title + " + ul li a";
			getCardClickBeacon(title, link);

			doCommonClassAssertions();
			Assert.assertEquals(beacon.props.get(59), "Guide:1");
		} catch (Exception e) {
			Assert.fail("Error clicking home guide card.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" })
	public void testHomeMultimediaCardClick() {
		try {
			System.out.println("Test home multimedia card click: ");
			driver.get(config.goHome());
			String linkAndTitle = SEL_HOME_MULTIMEDIA + " h3";
			getCardClickBeacon(linkAndTitle, linkAndTitle, 0);

			doCommonClassAssertions();
			Assert.assertEquals(beacon.props.get(59), "Multimedia:1");
		} catch (Exception e) {
			Assert.fail("Error clicking multimedia card.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testHomeThumbnailCardClick() {
		try {
			System.out.println("Test home Thumbnail card click: ");
			driver.get(config.goHome());
			String linkAndTitle = SEL_HOME_THUMB + " h3 a";
			getCardClickBeacon(linkAndTitle, linkAndTitle);

			doCommonClassAssertions();
			Assert.assertEquals(beacon.props.get(59), "Thumbnail:1");
		} catch (Exception e) {
			Assert.fail("Error clicking home Thumbnail card.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testLandingPrimaryFeatureClick() {
		try {
			System.out.println("Test Landing page primary feature card click: ");
			driver.get(config.getPageURL("LandingPage"));
			String linkAndTitle = SEL_HOME_PRIMARY_FEATURE + " h3";
			getCardClickBeacon(linkAndTitle, linkAndTitle);

			doCommonClassAssertions();
			Assert.assertEquals(beacon.props.get(59), "Feature:1");
		} catch (Exception e) {
			Assert.fail("Error clicking Landing page primary feature card.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testLandingSecFeatureClick() {
		try {
			System.out.println("Test Landing page secondary feature card click: ");
			driver.get(config.getPageURL("LandingPage"));
			String linkAndTitle = SEL_HOME_SECONDARY_FEATURE + " h3";
			getCardClickBeacon(linkAndTitle, linkAndTitle, 1);

			doCommonClassAssertions();
			Assert.assertEquals(beacon.props.get(59), "SecondaryFeature:2");
		} catch (Exception e) {
			Assert.fail("Error clicking Landing page primary feature card.");
			e.printStackTrace();
		}
	}

	
	/**
	 * Go to the results page and retrieve the beacon request object.
	 * 
	 * @param titleSelector
	 * @param linkSelector
	 * @param index
	 */
	private void getCardClickBeacon(String titleSelector, String linkSelector, int index) {
		this.titleText = card.getCardText(titleSelector, index);
		this.linkText = card.getCardText(linkSelector, index);
		card.clickCardLink(linkSelector, index);
		this.beacon = getBeacon();
	}

	/**
	 * Go to the results page and retrieve the beacon request object.
	 * 
	 * @param titleSelector
	 * @param linkSelector
	 */
	private void getCardClickBeacon(String titleSelector, String linkSelector) {
		getCardClickBeacon(titleSelector, linkSelector, 0);
	}

	/**
	 * Shared Assert() calls for CardClick_Test
	 */
	private void doCommonClassAssertions() {
		String currentUrl = driver.getCurrentUrl();
		String testPath = beacon.props.get(60);

		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(27));
		Assert.assertEquals(beacon.linkName, "FeatureCardClick");
		Assert.assertEquals(beacon.props.get(57).trim(), titleText.trim());
		Assert.assertEquals(beacon.props.get(58).trim(), linkText.trim());
		Assert.assertTrue(currentUrl.contains(testPath.substring(testPath.indexOf("cancer.gov"))));
	}

}
