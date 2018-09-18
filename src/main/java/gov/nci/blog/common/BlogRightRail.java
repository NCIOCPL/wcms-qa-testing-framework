package gov.nci.blog.common;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
	
	@FindBy(css = "li.month a")
	List<WebElement> list_months;
	
	/**
	 * Get a 'Year' header webelement.
	 * @param year
	 * @return
	 */
	public WebElement getArchiveYear(String year) {
		WebElement element = driver.findElement(By.xpath("//h4[contains(text(), '" + year + "')]"));
		return element;
	}
	
	/**
	 * Get a 'Month' list WebElement.
	 * @param month
	 * @return
	 */
	public WebElement getArchiveMonth(String month) {
		for(WebElement element : list_months) {
			if (element.getText().equalsIgnoreCase(month)) {
				return element;
			}
		}
		return null;
	}
	
	
	/**************** Blog Post Page Actions *****************************/

	/**
	 * Click on archive header.
	 */
	public void clickArchiveHeader() {
		ScrollUtil.scrollIntoview(driver, hdr_archive);
		hdr_archive.click();
	}
	
	/**
	 * Click on an archive year header.
	 */
	public void clickArchiveYear(String year) {
		WebElement element = getArchiveYear(year);		
		ScrollUtil.scrollIntoview(driver, element);
		element.click();
	}
    
	/**
	 * Click on an archive month link.
	 */
	public void clickArchiveMonth(String month) {
		WebElement element = getArchiveMonth(month);
		element.click();
	}
	
}