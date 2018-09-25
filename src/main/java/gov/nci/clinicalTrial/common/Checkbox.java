package gov.nci.clinicalTrial.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import gov.nci.Utilities.ScrollUtil;

/**
 * Exposes functionality related to a single checkbox
 */
public class Checkbox {

	private WebElement theCheckbox;
	private WebDriver browser;
	private String cssSelector;

	public Checkbox(WebDriver browser, By selector) {
		this.theCheckbox = browser.findElement(selector);
	}

	public Checkbox(WebDriver browser, String cssSelector) {
		this.browser = browser;
		this.theCheckbox = browser.findElement(By.cssSelector(cssSelector));
		this.cssSelector = cssSelector;
	}

	public boolean IsVisible() {
		return theCheckbox.isDisplayed();
	}

	/**
	 * Get a checkbox by index.
	 * 
	 * @param index
	 * @return
	 */
	private WebElement getCheckbox(int index) {
		try {
			WebElement checkbox = getCheckboxes().get(index);
			return checkbox;
		} catch (IndexOutOfBoundsException ex) {
			System.out.println("Invalid arraylist index: " + index);
			return null;
		}
	}

	/**
	 * Get a list of checkbox elements by selector.
	 * 
	 * @return
	 */
	private List<WebElement> getCheckboxes() {
		List<WebElement> checkboxes = browser.findElements(By.cssSelector(cssSelector));
		return checkboxes;
	}

	/**
	 * Click a checkbox.
	 * 
	 * @param index
	 */
	public void clickCheckbox(int index) {
		WebElement checkbox = getCheckbox(index);
		ScrollUtil.scrollIntoview(browser, checkbox);
		makeInputsVisible(index);
		checkbox.click();
	}

	/**
	 * Click the first checkbox in the collection.
	 * 
	 * @param index
	 */
	public void clickCheckbox() {
		clickCheckbox(0);
	}

	/**
	 * Uncheck all checkboxes on a page.
	 */
	public void uncheckAll() {
		JavascriptExecutor js = (JavascriptExecutor) browser;
		js.executeScript("for(var checkboxes=document.getElementsByTagName('input'),x=0;x<checkboxes.length;x++)"
				+ "'checkbox'==checkboxes[x].type&&(checkboxes[x].checked=!1);");
	}

	/**
	 * Make any hidden input elements visible for testing.
	 */
	private void makeInputsVisible(int index) {
		JavascriptExecutor js = (JavascriptExecutor) browser;
		js.executeScript("document.querySelectorAll('input')[" + index + "].setAttribute('style', 'display:block;');");
	}
	
}