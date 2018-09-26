package gov.nci.clinicalTrial.common;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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
	 * Get a collection of checkbox WebElements.
	 * 
	 * @return list of checkbox WebElements
	 */
	private List<WebElement> getCollection() {
		List<WebElement> elements = browser.findElements(By.cssSelector(cssSelector));
		return elements;
	}

	/**
	 * Get a list of attribute values from the WebElements collection.
	 * 
	 * @param HTML attribute
	 * @return
	 */
	public List<String> getAttributeCollection(String attribute) {
		List<String> attrList = new ArrayList<String>();
		List<WebElement> elements = getCollection();
		for (WebElement element : elements) {
			attrList.add(element.getAttribute(attribute));
		}
		return attrList;
	}

	/**
	 * Get a checkbox WebElement from the item's ID.
	 * 
	 * @param id
	 * @return
	 */
	private WebElement getFromId(String id) {

		
		WebElement element = browser.findElement(By.cssSelector("#" + id));
		return element;
	}

	
	public void clickById(String id) {
		WebElement element = getFromId(id);

		JavascriptExecutor javaScript = (JavascriptExecutor) browser;
		javaScript.executeScript("var x=document.getElementById('NCI-2015-01918');x.style.display='inline';x.click();x.checked=true;");


		Actions action = new Actions(browser);
		action.pause(5000);

		//element.click();
	}

	/**
	 * Uncheck all checkboxes on a page.
	 */
	public void uncheckAll() {
		JavascriptExecutor js = (JavascriptExecutor) browser;
		js.executeScript("for(var checkboxes=document.getElementsByTagName('input'),x=0;x<checkboxes.length;x++)"
				+ "'checkbox'==checkboxes[x].type&&(checkboxes[x].checked=!1);");
	}
}