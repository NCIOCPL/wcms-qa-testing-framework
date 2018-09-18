package gov.nci.blog.common;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import gov.nci.Utilities.ScrollUtil;
import gov.nci.framework.PageObjectBase;

public class BlogRightRail extends PageObjectBase {

	WebDriver driver;

    public BlogRightRail (WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }	
	

	/**************** Blog Right Rail Elements *****************************/
	@FindBy(id = "nvcgSlListBlogRTRail")
	WebElement div_rightRail;
	
	@FindBy(css = "#Featured+Posts + ul")
	WebElement list_featured;

	@FindBy(css = "#Categories + ul")
	WebElement list_categories;

	@FindBy(css = "#blog-archive-accordion")
	WebElement div_archiveAccordion;

	@FindBy(css = "h3#archive")
	WebElement hdr_archive;

	
	/**************** Blog Post Page Actions *****************************/
//
//	/**
//	 * Clicks on the first "Recommended From NCI" card.
//	 */
//	public void clickBodyLink() {
//		ScrollUtil.scrollIntoview(driver, lnk_body);
//		lnk_body.click();
// 	}
//
	
	/**
	 * Click on first definition LINK.
	 */
	public void clickArchiveHeader() {
		ScrollUtil.scrollIntoview(driver, hdr_archive);
		hdr_archive.click();
	}

    private List<WebElement> getPageOptionsControl() {
            List<WebElement> pocControls = getBrowser().findElements(By.cssSelector("#PageOptionsControl1"));
            // int controlCount = pocControls.size();
            // System.out.println("   Size = " + controlCount);

            return pocControls;
    }
    
    
    
}