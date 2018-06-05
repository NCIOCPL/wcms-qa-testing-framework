package gov.nci.WebAnalytics;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MegaMenu extends AnalyticsBase {
	
	// Local driver object and actions
	private WebDriver driver;	
	private Actions action;
	private WebDriverWait wait;
	
	// Constructor to initialize the page object
	public MegaMenu(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 5);		
		PageFactory.initElements(driver, this);
		System.out.print("MegaMenu PageFactory initialized: ");
	}

	/** Web elements
	* These are the elements that make up our page object
	*/
	// TODO: Figure out why FindBy(linkText) is so finnicky in Firefox
	@FindBy(css = "#mega-nav .nav-item-title a")
	WebElement mm_bar_link;
	@FindBy(css = "#mega-nav .sub-nav-group a")
	WebElement mm_subnav_header;	
	@FindBy(css = "#mega-nav .sub-nav-group ul li a")
	WebElement mm_subnav_li;
	@FindBy(linkText = "What Is Cancer")
	WebElement mm_subnav_li_text;
	@FindBy(css = ".mobile-menu-bar button.menu-btn")
	WebElement mm_reveal_mobile;
	@FindBy(css = "#mega-nav .mega-menu-scroll.open")
	WebElement mm_reveal_desktop;	
	
	/** Browser actions
	* All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	* to fire off analytics events. These actions will populate our list of har objects, which will
	* then be tested.
	*/
	public void clickMMBarEn() {
		System.out.println("Click megamenu bar (English)");
		driver.navigate().to(WANav.homePage);
		mm_bar_link.click();
	}

	public void clickMMBarEs() {
		System.out.println("Click megamenu bar (Spanish)");
		driver.navigate().to(WANav.spanishPage);
		mm_bar_link.click();
	}
	
	public void clickMMSubnavHeader() {
		System.out.println("Click megamenu subnav header");
		driver.navigate().to(WANav.homePage);
		action.moveToElement(mm_bar_link);
		action.perform();
		wait.until(ExpectedConditions.visibilityOf(mm_subnav_header));
		mm_subnav_header.click();
	}
	
	public void clickMMSubnavLi() {
		System.out.println("Click megamenu subnav list item");		
		driver.navigate().to(WANav.homePage);
		action.moveToElement(mm_bar_link);
		action.perform();
		wait.until(ExpectedConditions.visibilityOf(mm_subnav_li_text));
		mm_subnav_li_text.click();
	}

	public void revealMegaMenuDesktop() {
		System.out.println("Expand megamenu on desktop");		
		driver.navigate().to(WANav.homePage);
		action.moveToElement(mm_bar_link);
		action.perform();
		AnalyticsBase.nap(5);
		driver.navigate().refresh();
	}
	
	public void revealMegaMenuMobile() {
		System.out.println("Expand megamenu on mobile");		
		driver.manage().window().setSize(Resize.small);
		mm_reveal_mobile.click();
	}
	
}
