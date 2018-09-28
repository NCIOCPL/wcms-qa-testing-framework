package gov.nci.webanalyticstests.blog.common;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.blog.common.BlogLinks;
import gov.nci.blog.common.BlogRightRail;
import gov.nci.blog.pages.BlogPost;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestClickBase;

public class BlogRightRailClick_Test extends AnalyticsTestClickBase {

	private BlogPost blogPost;
	private BlogRightRail rightRail;
	private BlogLinks blogLinks;

	private Beacon beacon;
	private Actions action;

	private final String CANCER_CURRENTS_POST = "/news-events/cancer-currents-blog/2018/fda-olaparib-breast-brca-mutations";
	private final String CRCHD_POST = "	/about-nci/organization/crchd/blog/2017/celebrating-cure";
	private final String CANCER_CURRENTS_EN = "/news-events/cancer-currents-blog";
	private final String CANCER_CURRENTS_ES = "/espanol/noticias/temas-y-relatos-blog";

	// ==================== Setup methods ==================== //

	public void setupTestMethod(String path) {
		try {
			action = new Actions(driver);
			driver.get(config.goHome() + path);
			driver.navigate().refresh();

			this.blogPost = new BlogPost(driver);
			this.rightRail = blogPost.getRightRail();
			this.blogLinks = blogPost.getBlogLinks();
			action.pause(1000).perform();
		} catch (Exception e) {
			Assert.fail("Error building Blog Series page object.");
			e.printStackTrace();
		}
	}

	// ==================== Test methods ==================== //

	/**************** Blog right rail tests *****************************/

	@Test(groups = { "Analytics" }, priority = 5)
	public void testBlogPostRailArchiveExpand() {
		System.out.println("Test expand archive click: ");
		setupTestMethod(CANCER_CURRENTS_POST);

		try {
			rightRail.clickArchiveHeader();
			beacon = getBeacon();

			doCommonClassAssertions(driver.getCurrentUrl(), "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Expand:Archive");
		} catch (Exception e) {
			Assert.fail("Error expanding archive on rail.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 6)
	public void testBlogPostRailArchiveCollapse() {
		System.out.println("Test collapse archive click: ");
		setupTestMethod(CANCER_CURRENTS_POST);

		try {
			action.pause(1000).perform();
			rightRail.clickArchiveHeader();
			rightRail.clickArchiveHeader();
			beacon = getBeacon();

			doCommonClassAssertions(driver.getCurrentUrl(), "BlogAccordionAction");
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Collapse:Archive");
		} catch (Exception e) {
			Assert.fail("Error collapsing archive on rail.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 7)
	public void testBlogPostRailMonthClick() {
		System.out.println("Test month click: ");
		setupTestMethod(CANCER_CURRENTS_POST);

		try {
			String currUrl = driver.getCurrentUrl();
			action.pause(1000).perform();
			rightRail.clickArchiveHeader();
			rightRail.clickArchiveYear("2017");
			rightRail.clickArchiveMonth("May");
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "BlogArchiveDateClick");
			Assert.assertTrue(beacon.hasEvent(55));
			Assert.assertEquals(beacon.props.get(50), "2017:5");
		} catch (Exception e) {
			Assert.fail("Error clicking the month.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 8)
	public void testBlogPostRailFeaturedClick() {
		System.out.println("Test featured click: ");
		setupTestMethod(CANCER_CURRENTS_POST);

		try {
			String currUrl = driver.getCurrentUrl();
			String featuredText = rightRail.getFeaturedItemText(0);
			rightRail.clickFeaturedItem(0);
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "FeaturedPostsClick");
			Assert.assertTrue(beacon.hasEvent(54));
			Assert.assertEquals(beacon.props.get(50), featuredText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_FeaturedPosts:1");
		} catch (Exception e) {
			Assert.fail("Error clicking a featured blog link.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 9)
	public void testBlogPostRailCategoryClick() {
		System.out.println("Test category click: ");
		setupTestMethod(CANCER_CURRENTS_POST);

		try {

			String currUrl = driver.getCurrentUrl();
			String catText = rightRail.getCategoryItemText(1);
			rightRail.clickCategoryItem(1);
			beacon = getBeacon();

			doCommonClassAssertions(currUrl, "CategoryClick");
			Assert.assertTrue(beacon.hasEvent(55));
			Assert.assertEquals(beacon.props.get(50), catText);
			Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Category:2");
		} catch (Exception e) {
			Assert.fail("Error clicking a blog category link.");
			e.printStackTrace();
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
	 * Shared Assert() calls for BlogPost_Test class.
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
