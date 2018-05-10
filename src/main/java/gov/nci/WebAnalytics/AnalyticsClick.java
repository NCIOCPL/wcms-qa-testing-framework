package gov.nci.WebAnalytics;

import java.net.MalformedURLException;
import java.net.URI;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AnalyticsClick extends AnalyticsBase {	

	/*************** Basic Search Page WebElements **********************/
	@FindBy(how = How.CSS, using = "#mega-nav a")
	WebElement mega_menu_link;
	@FindBy(how = How.CSS, using = ".feature-card")
	WebElement feature_card;

	public AnalyticsClick() {}
	
	// Constructor to extend AnalyticsBase
	public AnalyticsClick(String beaconUrl) throws MalformedURLException {
		super(beaconUrl);
	}
	
	// Constructor to initialize the Page objects
	public AnalyticsClick(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("PageFactory initiated");
	}	
	
	public void clickFeatureCard() {
		feature_card.click();
	}	
	
	// Click mega menu
	public void clickMegaMenu() {
		mega_menu_link.click();
	}
	
}
