package gov.nci.Utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickUtil {

	private static JavascriptExecutor javaScript;

	/**
	 * Click method for cases where Selenium can't find or otherwise doesn't want to
	 * click on a given WebElement.
	 * 
	 * @param driver
	 * @param element
	 */
	public static void forceClick(WebDriver driver, WebElement element) {
		javaScript = (JavascriptExecutor) driver;
		javaScript.executeScript("arguments[0].click();", element);
	}

	/**
	 * Click method for cases where Selenium can't find or otherwise doesn't want to
	 * click on a given WebElement.
	 * 
	 * @param driver
	 * @param selector
	 */
	public static void forceClick(WebDriver driver, String selector) {
		javaScript = (JavascriptExecutor) driver;
		javaScript.executeScript("document.querySelector(arguments[0]).click();", selector);
	}
	
}
