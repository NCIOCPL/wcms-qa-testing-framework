package gov.nci.blog.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import gov.nci.framework.PageObjectBase;
import gov.nci.Utilities.ScrollUtil;

public class BlogPost extends PageObjectBase {

	WebDriver driver;	
	
	/**
	 * @param driver
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */	
	public BlogPost(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**************** Blog Post Page Elements *****************************/
	@FindBy(css = "#cgvBody p a")
	WebElement lnk_body;
	
	@FindBy(css = "#blog-cards .feature-card a")	
	WebElement lnk_blogFeatureCard;

	public String getRecommendedLinkText() {
		return lnk_blogFeatureCard.getText();
	}
	
	
	/**************** Blog Post Page Actions *****************************/

	/**
	 * Clicks on the first "Recommended From NCI" card.
	 */
	public void clickBodyLink() {
		ScrollUtil.scrollIntoview(driver, lnk_body);
		lnk_body.click();
 	}

	
	/**
	 * Clicks on the first "Recommended From NCI" card.
	 */
	public void clickRecommended() {
		ScrollUtil.scrollIntoview(driver, lnk_blogFeatureCard);
		lnk_blogFeatureCard.click();
 	}


	
}
