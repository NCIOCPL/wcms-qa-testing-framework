package gov.nci.WebAnalytics;

import java.net.MalformedURLException;
import java.util.List;
import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.nci.testcases.AnalyticsTest;

public class MegaMenu extends AnalyticsClick {

	public MegaMenu(){		
	}
	
	// Constructor to initialize the Page objects
	public MegaMenu(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}	
	
	/*** MegaMenu web elements ***/
	@FindBy(how = How.CSS, using = "#mega-nav a")
	WebElement mega_menu_link;	
	
	
	/*** Browser actions ***/
	private void clickMegaMenu2() {
		System.out.println("local clickMegaMenu hit!");
		mega_menu_link.click();
	}

	
	//region browseractions
	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */
	public void doBrowserActions() throws RuntimeException {
		navigateSite();
	}
	
	/// Click around pages
	public void navigateSite() {
				

		
		// Click on the MegaMenu
		clickMegaMenu2();
		driver.navigate().back();
		
	}

	//endregion browseractions
	
	//region tests

	//endregion tests
	
}
