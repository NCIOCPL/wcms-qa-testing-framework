package gov.nci.clinicalTrial.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	 * Get the first checkbox.
	 * 
	 * @return
	 */
	public WebElement getCheckbox() {
		return getCheckbox(0);
	}

	/**
	 * Get a checkbox by index.
	 * 
	 * @param index
	 * @return
	 */
	public WebElement getCheckbox(int index) {
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

}