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

	private void setupTestMethod() {
		try {
			action = new Actions(driver);
			driver.get(config.goHome() + CANCER_CURRENTS_EN);
			driver.navigate().refresh();

			this.blogSeries = new BlogSeries(driver);
			this.rightRail = blogSeries.getRightRail();
			this.blogLinks = blogSeries.getBlogLinks();
			action.pause(2000).perform();
		} catch (Exception e) {
			Assert.fail("Error building Blog Series page object.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //
	/**************** Blog Series right rail tests *****************************/

	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailArchiveExpand() {
		System.out.println("Test expand archive click: ");
		setupTestMethod();

		try {
			rightRail.clickArchiveHeader();
			beacon = getBeacon();

			doCommonClassAssertions(driver.getCurrentUrl(), "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Expand:Archive");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailMonthClick() {
		System.out.println("Test month click: ");
		setupTestMethod();

		try {
			String currUrl = driver.getCurrentUrl();
			action.pause(1000);
			rightRail.clickArchiveHeader();
			rightRail.clickArchiveYear("2017");
			rightRail.clickArchiveMonth("May");
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "BlogArchiveDateClick");
			Assert.assertTrue(beacon.hasEvent(55));
			Assert.assertEquals(beacon.props.get(50), "2017:5");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailFeaturedClick() {
		System.out.println("Test featured click: ");
		setupTestMethod();

		try {
			String currUrl = driver.getCurrentUrl();
			String featuredText = rightRail.getFeaturedItemText(0);
			rightRail.clickFeaturedItem(0);
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "FeaturedPostsClick");
			Assert.assertTrue(beacon.hasEvent(54));
			Assert.assertEquals(beacon.props.get(50), featuredText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_FeaturedPosts:1");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	@Test(groups = { "Analytics" })
	public void testBlogSeriesRailCategoryClick() {
		System.out.println("Test category click: ");
		setupTestMethod();

		try {
			String currUrl = driver.getCurrentUrl();
			String catText = rightRail.getCategoryItemText(1);
			rightRail.clickCategoryItem(1);
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "CategoryClick");
			Assert.assertTrue(beacon.hasEvent(55));
			Assert.assertEquals(beacon.props.get(50), catText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Series_Category:2");
		} catch (Exception e) {
			String currMethod = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Assert.fail("Error clicking component in " + currMethod + "()");
		}
	}

	/**************** Blog common link tests *****************************/

	@Test(groups = { "Analytics" })
	public void testBlogSeriesSubscribeClick() {
		System.out.println("Test subscribe click: ");
		setupTestMethod();

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
		setupTestMethod();

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
		setupTestMethod();

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
