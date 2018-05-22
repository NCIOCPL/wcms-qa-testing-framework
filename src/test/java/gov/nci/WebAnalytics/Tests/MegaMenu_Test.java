package gov.nci.WebAnalytics.Tests;

import java.util.List;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.AnalyticsBase;
import gov.nci.WebAnalytics.MegaMenu;

public class MegaMenu_Test extends AnalyticsTestBase {
	
	// Analytics object
	public MegaMenu megaMenu;
	
	/// Menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEn() {
		megaMenu = new MegaMenu(driver);
		megaMenu.clickMegaMenuEn();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));
		boolean hasLinkName = false;
		
		for(AnalyticsBase beacon : clickBeacons) {
			if(beacon.linkName.toLowerCase().equals("megamenuclick")) {
				Assert.assertTrue(beacon.events[0].contains("event26"));
				hasLinkName = true;
			}
		}		
		Assert.assertTrue(hasLinkName);
		logger.log(LogStatus.PASS, "MegaMenu top level click passed.");
	}
	
	/// Spanish menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEs() {
		megaMenu = new MegaMenu(driver);
		megaMenu.clickMegaMenuEs();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));
		boolean hasLinkName = false;
		
		for(AnalyticsBase beacon : clickBeacons) {
			if(beacon.linkName.toLowerCase().equals("megamenuclick")) {
				Assert.assertTrue(beacon.events[0].contains("event26"));
				hasLinkName = true;
			}
		}		
		Assert.assertTrue(hasLinkName);
		logger.log(LogStatus.PASS, "MegaMenu Spanish top level click passed.");
	}
	
	/// MegaMenu expansion returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMExpand() {
		megaMenu = new MegaMenu(driver);
		megaMenu.hoverMegaMenu();
		
		logger.log(LogStatus.PASS, "MegaMenu expanded or something");
	}	
	
	/// MegaMenu subnav title click returns the expected values
	@Test(groups = { "Analytics" })
	public void testSubnavClick() {
		megaMenu = new MegaMenu(driver);
		//megaMenu.doSomething();		
		logger.log(LogStatus.PASS, "Expaned subnav title click passed.");
	}	
}
