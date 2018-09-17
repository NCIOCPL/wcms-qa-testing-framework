package gov.nci.webanalyticstests.click;

import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.webanalytics.Beacon;
import gov.nci.blog.pages.BlogPost;

public class BlogPost_Test extends AnalyticsTestClickBase {
	
	private BlogPost blogPost;
	private Beacon beacon;
	
	private final String BLOG_POST_PATH = "/news-events/cancer-currents-blog/2018/cabozantinib-fda-first-line-kidney";		

	@Test(groups = { "Analytics" })
	public void testRecommendedClick() {
		try {
			System.out.println("Test 'Recommended' card click: ");			
			driver.get(config.goHome() + BLOG_POST_PATH);
			blogPost = new BlogPost(driver);
			blogPost.clickRecommended();
		    beacon = getBeacon();

			String currUrl = driver.getCurrentUrl();
			Assert.assertEquals(beacon.linkName, "BlogFeatureCardclick");
			Assert.assertTrue(beacon.hasEvent(54));
		    Assert.assertEquals(beacon.props.get(50), blogPost.getRecommendedLinkText());
		    Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BlogCard:1");
		    Assert.assertTrue(currUrl.contains(beacon.props.get(67)));
		    

//		    prop4
//		    D=pev1
//		    prop8
//		    english
//		    prop50
//		    Two New Therapies Approved for Advanced Kidney Cancer
//		    prop66
//		    Blog_CancerCurrents_Post_BlogCard:1
//		    prop67
//		    www-dt-qa.cancer.gov/news-events/cancer-currents-blog/2018/cabozantinib-fda-first-line-kidney
//		    event54,event92=20
//		    Fallback Visitor ID
//		    4B1DC06D148AB3B0-30C78FB2E8AE7FB8
//		    Image sent from JS?
//		    1
//		    JavaScript version
//		    1.6
//		    Javascript-enabled browser?
//		    N
//		    Link name
//		    BlogFeatureCardClick
		    
		    
		} catch (Exception e) {
			Assert.fail("Error submitting sitewide search.");
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
