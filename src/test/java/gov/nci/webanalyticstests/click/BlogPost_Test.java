package gov.nci.webanalyticstests.click;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import gov.nci.webanalytics.Beacon;
import gov.nci.blog.pages.BlogPost;
import gov.nci.commonobjects.MegaMenu;

public class BlogPost_Test extends AnalyticsTestClickBase {
	
	private BlogPost blogPost;
	private Beacon beacon;
	private Actions action;
	
	private final String CANCER_CURRENTS_PATH = "/news-events/cancer-currents-blog/2018/fda-olaparib-breast-brca-mutations";		


	@BeforeMethod(groups = { "Analytics" }) 
	public void setupBlogPost() {
		action = new Actions(driver);
	}

	@Test(groups = { "Analytics" })
	public void testBodyLinkClick() {
		try {
			System.out.println("Test body link click: ");			
			driver.get(config.goHome() + CANCER_CURRENTS_PATH);
			blogPost = new BlogPost(driver);
			String firstLinkText = blogPost.getBodyLinkText();
			String currUrl = driver.getCurrentUrl();
			action.pause(2000);
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
	public void testDefinitionLinkClick() {
		try {
			System.out.println("Test definition link click: ");			
			driver.get(config.goHome() + CANCER_CURRENTS_PATH);
			blogPost = new BlogPost(driver);
			String firstLinkText = blogPost.getDefinitionLinkText();
			String currUrl = driver.getCurrentUrl();
			action.pause(2000);
			blogPost.clickDefinitionNoPopup();
		    beacon = getBeacon();

			Assert.assertEquals(beacon.linkName, "BlogBodyLinkClick");
			Assert.assertTrue(beacon.hasEvent(56));
		    Assert.assertEquals(beacon.props.get(50), firstLinkText);
		    Assert.assertEquals(beacon.props.get(66), "Blog_CancerCurrents_Post_BodyGlossifiedTerm");
		    Assert.assertTrue(currUrl.contains(beacon.props.get(67)));
		} catch (Exception e) {
			Assert.fail("Error clicking definition link.");
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Analytics" })
	public void testRecommendedClick() {
		try {
			System.out.println("Test 'Recommended' card click: ");			
			driver.get(config.goHome() + CANCER_CURRENTS_PATH);
			blogPost = new BlogPost(driver);
			String recommended = blogPost.getRecommendedLinkText();
			String currUrl = driver.getCurrentUrl();
			action.pause(2000);
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
