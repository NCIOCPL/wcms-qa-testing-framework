package gov.nci.blog.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import gov.nci.framework.PageObjectBase;
import gov.nci.Utilities.ScrollUtil;

public class BlogSeries extends PageObjectBase {

	WebDriver driver;	
	
	/**
	 * @param driver
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */	
	public BlogSeries(WebDriver driver) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**************** Blog Series Page Elements *****************************/

	
	/**************** Blog Series Page Actions *****************************/


	
}
