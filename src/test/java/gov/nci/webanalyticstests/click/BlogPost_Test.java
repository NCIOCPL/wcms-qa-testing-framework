package gov.nci.webanalyticstests.click;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.Beacon;
import gov.nci.blog.pages.BlogPost;

public class BlogPost_Test extends AnalyticsTestClickBase {
	
	private BlogPost blogPost;
	private Beacon beacon;
	
	private final String BLOG_POST_PATH = "/news-events/cancer-currents-blog/2018/fda-olaparib-breast-brca-mutations";		


	@Test(groups = { "Analytics" })
	public void testBodyClick() {
		try {
			System.out.println("Test body link click: ");			
			driver.get(config.goHome() + BLOG_POST_PATH);
			blogPost = new BlogPost(driver);
			blogPost.clickBodyLink();
		    beacon = getBeacon();

			Assert.assertEquals(beacon.linkName, "BlogBodyLinkClick");
//			Assert.assertTrue(beacon.hasEvent(54));
//		    Assert.assertEquals(beacon.props.get(50), blogPost.getRecommendedLinkText());
//		    Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BlogCard:1");
//		    Assert.assertTrue(currUrl.contains(beacon.props.get(67)));		   		    
		} catch (Exception e) {
			Assert.fail("Error clicking link in body.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	// TODO: fix slowness and other issues
	public void testRecommendedClick() {
		try {
			System.out.println("Test 'Recommended' card click: ");			
			driver.get(config.goHome() + BLOG_POST_PATH);
			blogPost = new BlogPost(driver);
			String recommended = blogPost.getRecommendedLinkText();
			String currUrl = driver.getCurrentUrl();
			blogPost.clickRecommended();
		    beacon = getBeacon();

			Assert.assertEquals(beacon.linkName, "BlogFeatureCardClick");
			Assert.assertTrue(beacon.hasEvent(54));
		    Assert.assertEquals(beacon.props.get(50), recommended);
		    Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BlogCard:1");
		    Assert.assertTrue(currUrl.contains(beacon.props.get(67)));		   		    
		} catch (Exception e) {
			Assert.fail("Error navigating blost post page.");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Shared Assert() calls for BlogPost_Test 
	 * @param beacon
	 */
	private void doCommonClassAssertions(Beacon beacon) {
		doCommonClickAssertions(beacon);

		
	}
	
}
