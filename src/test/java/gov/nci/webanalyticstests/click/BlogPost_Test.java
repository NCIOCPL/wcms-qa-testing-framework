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
	
	@Test(groups = { "Analytics" })
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
	
	@Test(groups = { "Analytics" })
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
	
	@Test(groups = { "Analytics" })
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
	
	@Test(groups = { "Analytics" })
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
	
	@Test(groups = { "Analytics" })
	public void testBlogRailArchiveCollapse() {
		try {
			System.out.println("Test collapse archive click: ");
			rightRail = blogPost.getRightRail();
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

	@Test(groups = { "Analytics" })
	public void testBlogRailMonthClick() {
		try {
			System.out.println("Test month click: ");
			String currUrl = driver.getCurrentUrl();
			rightRail = blogPost.getRightRail();
			rightRail.clickArchiveHeader();
			rightRail.clickArchiveYear("2017");
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
	
	/**
	 * Shared Assert() calls for BlogPost_Test class.
	 * @param currentUrl
	 */
	private void doCommonClassAssertions(String currentUrl) {
		// Note: remvoe this once pageName value is fixed on CDE side
		Assert.assertTrue(currentUrl.contains(beacon.props.get(67)));		
	}
	
	
}
