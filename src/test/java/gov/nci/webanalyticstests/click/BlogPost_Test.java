package gov.nci.webanalyticstests.click;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.blog.common.BlogRightRail;
import gov.nci.blog.pages.BlogPost;
import gov.nci.webanalytics.Beacon;

public class BlogPost_Test extends AnalyticsTestClickBase {
	
	private BlogPost blogPost;
	private BlogRightRail rightRail;
	private Beacon beacon;
	private Actions action;
	
	private final String CANCER_CURRENTS_PATH = "/news-events/cancer-currents-blog/2018/fda-olaparib-breast-brca-mutations";

	@BeforeMethod(groups = { "Analytics" }) 
	public void setupBlogPost() {
		try {
			action = new Actions(driver);
			driver.get(config.goHome() + CANCER_CURRENTS_PATH);
			blogPost = new BlogPost(driver);
			action.pause(2000);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error building BlogPost page object.");
		}
	}

	/**************** Blog Post body tests *****************************/
	
	@Test(groups = { "Analytics" }, priority = 1)
	public void testBlogBodyLinkClick() {
		try {
			System.out.println("Test body link click: ");
			String firstLinkText = blogPost.getBodyLinkText();
			String currUrl = driver.getCurrentUrl();
			blogPost.clickBodyLink();
		    beacon = getBeacon();

			Assert.assertEquals(beacon.linkName, "BlogBodyLinkClick");
			Assert.assertTrue(beacon.hasEvent(56));
		    Assert.assertEquals(beacon.props.get(50), firstLinkText);
		    Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BodyLink");
		    Assert.assertTrue(currUrl.contains(beacon.props.get(67)));		   		    
		} catch (Exception e) {
			Assert.fail("Error clicking link in body.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" }, priority = 2)
	public void testBlogDefinitionLinkClick() {
		try {
			System.out.println("Test definition link click: ");
			String firstLinkText = blogPost.getDefinitionLinkText();
			String currUrl = driver.getCurrentUrl();
			blogPost.clickDefinitionNoPopup();
		    beacon = getBeacon();

		    doCommonClassAssertions(currUrl);
			Assert.assertEquals(beacon.linkName, "BlogBodyLinkClick");
			Assert.assertTrue(beacon.hasEvent(56));
		    Assert.assertEquals(beacon.props.get(50), firstLinkText);
		    Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BodyGlossifiedTerm");
		} catch (Exception e) {
			Assert.fail("Error clicking definition link.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" }, priority = 3)
	public void testBlogRecommendedClick() {
		try {
			System.out.println("Test 'Recommended' card click: ");
			String recommended = blogPost.getRecommendedLinkText();
			String currUrl = driver.getCurrentUrl();
			blogPost.clickRecommended();
		    beacon = getBeacon();

		    doCommonClassAssertions(currUrl);
			Assert.assertEquals(beacon.linkName, "BlogFeatureCardClick");
			Assert.assertTrue(beacon.hasEvent(54));
		    Assert.assertEquals(beacon.props.get(50), recommended);
		    Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BlogCard:1");
		} catch (Exception e) {
			Assert.fail("Error navigating blost post page.");
			e.printStackTrace();
		}
	}
		
	/**************** Blog right rail tests *****************************/
	
	@Test(groups = { "Analytics" }, priority = 4)
	public void testBlogRailArchiveExpand() {
		try {
			System.out.println("Test expand archive click: ");			
			rightRail = blogPost.getRightRail();
			rightRail.clickArchiveHeader();
		    beacon = getBeacon();

		    doCommonClassAssertions(driver.getCurrentUrl());
			Assert.assertEquals(beacon.linkName, "BlogAccordionAction");
		    Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Expand:Archive");
		} catch (Exception e) {
			Assert.fail("Error expanding archive on rail.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" }, priority = 5)
	public void testBlogRailArchiveCollapse() {
		try {
			System.out.println("Test collapse archive click: ");
			rightRail = blogPost.getRightRail();
			action.pause(500);
			rightRail.clickArchiveHeader();
			rightRail.clickArchiveHeader();
		    beacon = getBeacon();

		    doCommonClassAssertions(driver.getCurrentUrl());		    
			Assert.assertEquals(beacon.linkName, "BlogAccordionAction");
		    Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Collapse:Archive");
		} catch (Exception e) {
			Assert.fail("Error collapsing archive on rail.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 6)
	public void testBlogRailMonthClick() {
		try {
			System.out.println("Test month click: ");
			String currUrl = driver.getCurrentUrl();
			rightRail = blogPost.getRightRail();
			rightRail.clickArchiveHeader();
			rightRail.clickArchiveYear("2017");
			action.pause(500);
			rightRail.clickArchiveMonth("May");
		    beacon = getBeacon();

		    doCommonClassAssertions(currUrl);
			Assert.assertTrue(beacon.hasEvent(55));
			Assert.assertEquals(beacon.linkName, "BlogArchiveDateClick");
		    Assert.assertEquals(beacon.props.get(50), "2017:5");
		} catch (Exception e) {
			Assert.fail("Error clicking the month.");
			e.printStackTrace();
		}
	}

	@Test(groups = { "Analytics" }, priority = 7)
	public void testBlogRailFeaturedClick() {
		try {
			System.out.println("Test featured click: ");
			String currUrl = driver.getCurrentUrl();
			rightRail = blogPost.getRightRail();
			String featuredText = rightRail.getFeaturedItemText(0);			
			rightRail.clickFeaturedItem(0);
		    beacon = getBeacon();

		    doCommonClassAssertions(currUrl);
			Assert.assertTrue(beacon.hasEvent(54));
			Assert.assertEquals(beacon.linkName, "FeaturedPostsClick");
		    Assert.assertEquals(beacon.props.get(50), featuredText);
		    Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_FeaturedPosts:1");
		} catch (Exception e) {
			Assert.fail("Error clicking a featured blog link.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" }, priority = 8)
	public void testBlogRailCategoryClick() {
		try {
			System.out.println("Test category click: ");
			String currUrl = driver.getCurrentUrl();
			rightRail = blogPost.getRightRail();
			String catText = rightRail.getCategoryItemText(1);
			rightRail.clickCategoryItem(1);
		    beacon = getBeacon();

		    doCommonClassAssertions(currUrl);
			Assert.assertTrue(beacon.hasEvent(55));
			Assert.assertEquals(beacon.linkName, "CategoryClick");
		    Assert.assertEquals(beacon.props.get(50), catText);
		    Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_Category:2");
		} catch (Exception e) {
			Assert.fail("Error clicking a blog category link.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Shared Assert() calls for BlogPost_Test class.
	 * @param currentUrl
	 */
	private void doCommonClassAssertions(String currentUrl) {
		// Note: remove this once pageName value is fixed on CDE side
		Assert.assertTrue(currentUrl.contains(beacon.props.get(67)));		
	}	
	
}
