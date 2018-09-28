package gov.nci.webanalyticstests.blog.common;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.blog.common.BlogLinks;
import gov.nci.blog.common.BlogRightRail;
import gov.nci.blog.pages.BlogSeries;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BlogLinksClick_Test extends AnalyticsTestClickBase {

	private BlogSeries blogSeries;
	private BlogRightRail rightRail;
	private BlogLinks blogLinks;

	private Beacon beacon;
	private Actions action;

	private final String CANCER_CURRENTS_POST = "/news-events/cancer-currents-blog/2018/fda-olaparib-breast-brca-mutations";
	private final String CRCHD_POST = "	/about-nci/organization/crchd/blog/2017/celebrating-cure";
	private final String CANCER_CURRENTS_EN = "/news-events/cancer-currents-blog";
	private final String CANCER_CURRENTS_ES = "/espanol/noticias/temas-y-relatos-blog";

	// ==================== Setup methods ==================== //

	private void setupTestMethod(String path) {
		try {
			action = new Actions(driver);
			driver.get(config.goHome() + path);
			driver.navigate().refresh();

			this.blogSeries = new BlogSeries(driver);
			this.rightRail = blogSeries.getRightRail();
			this.blogLinks = blogSeries.getBlogLinks();

		} catch (Exception e) {
			Assert.fail("Error building Blog Series page object.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/**************** Blog common link tests *****************************/

	@Test(groups = { "Analytics" })
	public void testBlogSeriesSubscribeClick() {
		System.out.println("Test subscribe click: ");
		setupTestMethod(CANCER_CURRENTS_EN);

		try {
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickSubscribeNoNav();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "BlogSubscribeClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Subscribe");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// @Test(groups = { "Analytics" })
	public void testBlogSeriesNewerClick() {
		System.out.println("Test 'newer' click: ");
		setupTestMethod(CANCER_CURRENTS_EN);

		try {
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickOlderSeries();
			blogLinks.clickNewerSeries();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Newer");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	// @Test(groups = { "Analytics" })
	public void testBlogSeriesOlderClick() {
		System.out.println("Test 'older' click: ");
		setupTestMethod(CANCER_CURRENTS_EN);

		try {
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickOlderSeries();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Older");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/**************** Blog common link tests *****************************/

	@Test(groups = { "Analytics" }, priority = 10)
	public void testBlogPostSubscribeClick() {
		System.out.println("Test subscribe click: ");
		setupTestMethod(CANCER_CURRENTS_POST);

		try {
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickSubscribeNoNav();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "BlogSubscribeClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Subscribe");
		} catch (Exception e) {
			Assert.fail("Error clicking 'subscribe' link.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 11)
	public void testBlogPostNewerClick() {
		System.out.println("Test 'newer' click: ");
		setupTestMethod(CANCER_CURRENTS_POST);

		try {
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickNewerPost();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Newer");
		} catch (Exception e) {
			Assert.fail("Error clicking 'newer' link.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 11)
	public void testBlogPostOlderClick() {
		System.out.println("Test 'older' click: ");
		setupTestMethod(CANCER_CURRENTS_POST);

		try {
			String currUrl = driver.getCurrentUrl();
			blogLinks.clickOlderPost();
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "OlderNewerClick");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Older");
		} catch (Exception e) {
			Assert.fail("Error clicking 'older' link.");
			e.printStackTrace();
		}
	}

	// ==================== Common assertions ==================== //
	/**
	 * Shared Assert() calls for BlogSeries_Test class.
	 * 
	 * @param currentUrl
	 * @param linkName
	 */
	private void doCommonClassAssertions(String currentUrl, String linkName) {
		// Note: remove this once pageName value is fixed on CDE side
		Assert.assertEquals(beacon.linkName, linkName);
		Assert.assertTrue(currentUrl.contains(beacon.props.get(67)));
	}

}
