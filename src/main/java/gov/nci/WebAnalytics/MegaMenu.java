package gov.nci.WebAnalytics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MegaMenu extends AnalyticsBase {

	public MegaMenu(){		
	}
	
	// Constructor to initialize the Page objects
	public MegaMenu(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("MegaMenu PageFactory initialized");
	}
	
	/*** MegaMenu web elements ***/
	@FindBy(how = How.CSS, using = "#mega-nav .nav-item-title a")
	WebElement mm_bar_link;
	@FindBy(how = How.CSS, using = "#mega-nav .sub-nav-group a")
	WebElement mm_subnav_title_link;	
	@FindBy(how = How.CSS, using = "#mega-nav .sub-nav-group ul li a")
	WebElement mm_subnav_list_item;	
	
	/*** Browser actions ***/
	private void clickMegaMenu() {
		driver.navigate().to(homePage);
		mm_bar_link.click();
		//mm_subnav_title_link.click();
		//mm_subnav_list_item.click();		
		driver.navigate().to(spanishPage);
		mm_bar_link.click();
		//mm_subnav_title_link.click();
		//mm_subnav_list_item.click();
	}

	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */
	public void doMegaMenuActions() throws RuntimeException {
		System.out.println("Begin MegaMenu actions");
		clickMegaMenu();
		System.out.println("Done MegaMenu actions");
	}
	
}
