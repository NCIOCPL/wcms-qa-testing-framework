package gov.nci.WebAnalytics.Tests;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.AnalyticsBase;
import gov.nci.WebAnalytics.MegaMenu;

public class MegaMenu_Test extends AnalyticsTestBase {
	
	private MegaMenu megaMenu;

	@BeforeMethod(groups = { "Analytics" }) 
	private void beforeMethod() {
		megaMenu = new MegaMenu(driver);
	}
	
	/// Menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEn() {
		megaMenu.clickMMBarEn();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasLinkName(clickBeacons, "MegaMenuClick"));
		Assert.assertTrue(hasEvent(clickBeacons, "event26"));
		logger.log(LogStatus.PASS, "MegaMenu top level click passed.");
	}
	
	/// Spanish menu bar click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMBarEs() {
		megaMenu.clickMMBarEs();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));		
		Assert.assertTrue(hasLinkName(clickBeacons, "MegaMenuClick"));
		Assert.assertTrue(hasEvent(clickBeacons, "event26"));
		logger.log(LogStatus.PASS, "MegaMenu Spanish top level click passed.");
	}
	
	/// MegaMenu expansion returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMExpand() {
		System.out.println("=== Begin debug megamenu expand ===");
		megaMenu.clickMMSubnavHeader();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));
		boolean hasLinkName = false;
		
		for(AnalyticsBase beacon : clickBeacons) {
			System.out.print(beacon.linkName);
			if(beacon.linkName.toLowerCase().equals("megamenudesktopreveal")) {
				// Assert.assertTrue(beacon.events[0].contains("event28"));
				hasLinkName = true;
			}
		}
		// Assert.assertTrue(hasLinkName);
		System.out.println("=== End debug megamenu expand ===");
		logger.log(LogStatus.PASS, "MegaMenu expansion passed.");
	}
	
	/// MegaMenu subnav header click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMMSubnavHeaderClick() {		
		megaMenu.clickMMSubnavHeader();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasLinkName(clickBeacons, "MegaMenuClick"));
		Assert.assertTrue(hasEvent(clickBeacons, "event26"));
		logger.log(LogStatus.PASS, "Subnav header click passed.");
	}
	
	/// MegaMenu subnav title click returns the expected values
	@Test(groups = { "Analytics" })
	public void testSubnavClick() {		
		megaMenu.clickMMSubnavLi();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasLinkName(clickBeacons, "MegaMenuClick"));
		Assert.assertTrue(hasEvent(clickBeacons, "event26"));
		Assert.assertTrue(haseVar(clickBeacons, 53, "About Cancer"));
		Assert.assertTrue(hasProp(clickBeacons, 53, "About Cancer"));
		Assert.assertTrue(hasProp(clickBeacons, 54, "Understanding cancer"));
		Assert.assertTrue(hasProp(clickBeacons, 55, "cancer disparities"));
		logger.log(LogStatus.PASS, "Expaned subnav title click passed.");
	}
	
	/// MegaMenu subnav title click returns the expected values
	@Test(groups = { "Analytics" })
	public void testMobileMegaMenuReveal() {
		megaMenu.revealMegaMenuMobile();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasLinkName(clickBeacons, "megamenumobilereveal"));
		Assert.assertTrue(hasEvent(clickBeacons, "event28"));
		logger.log(LogStatus.PASS, "Expaned mobile mega menu passed");
	}
	
	@Test(groups = { "Analytics" })
	public void mmSmoke() {
		megaMenu.clickMMBarEn();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasProp(clickBeacons, 4, "d=pev1"));
		Assert.assertTrue(hasProp(clickBeacons, 67, "D=pageName"));
		Assert.assertTrue(haseVar(clickBeacons, 2, "English"));
		Assert.assertFalse(haseVar(clickBeacons, 237, ""));
		logger.log(LogStatus.PASS, "MegaMenu smoke passed.");
	}	
}
