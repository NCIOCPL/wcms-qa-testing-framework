package gov.nci.WebAnalytics;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MegaMenu extends AnalyticsBase {
	/*** MegaMenu web elements ***/
	@FindBy(how = How.CSS, using = "#mega-nav .nav-item-title a")
	WebElement mm_bar_link;
	@FindBy(how = How.CSS, using = "#mega-nav .sub-nav-group a")
	WebElement mm_subnav_title_link;	
	@FindBy(how = How.CSS, using = "#mega-nav .sub-nav-group ul li a")
	WebElement mm_subnav_list_item;	
	
	public MegaMenu(){		
	}
	
	// Constructor to initialize the Page objects
	public MegaMenu(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("MegaMenu PageFactory initialized");
	}
	
	/** Browser actions
	* All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	* to fire off analytics events. These actions will populate our list of har objects, which will
	* then be tested.
	*/
	public void clickMegaMenuEn() {
		driver.navigate().to(homePage);
		mm_bar_link.click();
		//mm_subnav_title_link.click();
		//mm_subnav_list_item.click();
	}

	public void clickMegaMenuEs() {
		driver.navigate().to(spanishPage);
		mm_bar_link.click();
		//mm_subnav_title_link.click();
		//mm_subnav_list_item.click();
	}
	
	public void hoverMegaMenu() {
		driver.manage().timeouts().implicitlyWait(10,  TimeUnit.SECONDS);		
		Actions action = new Actions(driver);
		action.moveToElement(mm_bar_link).build().perform();
		
	}
	
}
