package gov.nci.commonobjects;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import gov.nci.Utilities.ScrollUtil;
import gov.nci.framework.PageObjectBase;

public class Checkboxes extends PageObjectBase {

	private WebDriver driver;
	private String selector;

	/**
	 * This is a common-use class for checkbox WebElement collections. For the
	 * CTS-specific checkbox, see the gov.nci.clinicaltrial.common.Checkbox package
	 * 
	 * @param driver
	 * @param selector
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public Checkboxes(WebDriver driver, String selector) throws MalformedURLException, UnsupportedEncodingException {
		super(driver);
		this.driver = driver;
		this.selector = selector;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Verify the checkbox collection exists on page and is visible.
	 * 
	 * @return
	 */
	public boolean IsVisible() {
		List<WebElement> elementExists = getCheckboxes();

		if (elementExists.size() > 0) {
			WebElement element = elementExists.get(0);
			return element.isDisplayed();
		}
		return false;
	}

	/**
	 * Get a list of checkbox elements by selector.
	 * 
	 * @return
	 */
	private List<WebElement> getCheckboxes() {
		List<WebElement> checkboxes = driver.findElements(By.cssSelector(selector));
		return checkboxes;
	}

	// document.querySelector('.cts-results-container input').checked = false
	
	/**
	 * Uncheck all checkboxes on a page.
	 * 
	 * @param driver
	 *            A WebDriver instance representing the browser, currently
	 *            displaying the page where the change will take place.
	 */
	public void checkCheckBox(int index) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelectorAll('" + this.selector + "')[" + index + "].checked = true;");
	}

}
