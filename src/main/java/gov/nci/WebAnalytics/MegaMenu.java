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

public class MegaMenu extends AnalyticsTest {

	public MegaMenu(){		
	}
	
	/*** MegaMenu web elements ***/
	@FindBy(how = How.CSS, using = "#mega-nav a")
	WebElement mega_menu_link;	
	
	
	/*** Browser actions ***/
	public void clickMegaMenu() {
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
	private void doBrowserActions() throws RuntimeException {
		navigateSite();
	}
	
	/// Click around pages
	public void navigateSite() {
				
		// Click on a feature card
		clickEvents.clickFeatureCard();
		driver.navigate().back();
		
		// Click on the MegaMenu
		clickEvents.clickMegaMenu();		
		driver.navigate().back();
		
	}

	//endregion browseractions
	
	//region tests

	/// Load and click events have been captured
	@Test(groups = { "Analytics" }, priority = 1)
	public void verifyHar() {
		doBrowserActions();
		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsLoad> loadBeacons = AnalyticsLoad.getLoadBeacons(harList);
		List<AnalyticsClick> clickBeacons = AnalyticsClick.getClickBeacons(harList);		
				
		Assert.assertTrue(harList.size() > 0);
		Assert.assertTrue(loadBeacons.size() > 0);
		Assert.assertTrue(clickBeacons.size() > 0);
		
		System.out.println("=== Start debug testEvents() ===");
		for(String har : harList) {
			System.out.println(har);
		}
		System.out.println("=== End debug testEvents() ===");				
		
		logger.log(LogStatus.PASS, "Load and click events have been captured.");				
	}	
	
	/// Click event numbers match with their descriptors
	@Test(groups = { "Analytics" })
	public void testClickEvents() {
		navigateSite();
		List<String> harList = AnalyticsBase.getHarUrlList(proxy);
		List<AnalyticsClick> clickBeacons = AnalyticsClick.getClickBeacons(harList);
		
		for(AnalyticsClick beacon : clickBeacons) {
			if(beacon.linkName == "FeatureCardClick") {
				Assert.assertTrue(beacon.events[0].contains("event27"));
			}
			if(beacon.linkName == "MegaMenuClick") {
				Assert.assertTrue(beacon.events[0].contains("event27"));
			}
		}
		
		logger.log(LogStatus.PASS, "Click event values are correct.");		
	}		
	
	/// Temporary method to verify that my new changes are picked up
	@Test(groups = { "Analytics" })
	public void testString() {
		String str = "clickEvent";
		Assert.assertEquals("clickEvent", str);
	}
	//endregion tests
	
}
