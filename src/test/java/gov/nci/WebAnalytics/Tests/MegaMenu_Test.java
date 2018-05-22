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
	
	/// Load and click events have been captured
	@Test(groups = { "Analytics" })
	public void testMMBarEn() {
		megaMenu = new MegaMenu(driver);
		megaMenu.clickMegaMenuEn();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));
				
		Assert.assertTrue(clickBeacons.size() > 0);
		logger.log(LogStatus.PASS, "MegaMenu top level click passed.");
	}
	
	/// Click event numbers match with their descriptors
	@Test(groups = { "Analytics" })
	public void testMMBarEs() {
		megaMenu = new MegaMenu(driver);
		megaMenu.clickMegaMenuEs();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(clickBeacons.size() > 0);

		int count = 0;
		for(AnalyticsBase beacon : clickBeacons) {
			if(beacon.linkName.toLowerCase() == "megamenuclick") {
				Assert.assertTrue(beacon.events[0].contains("event27"));
				count++;
			}
		}
		
		Assert.assertTrue(count > 0);		
		logger.log(LogStatus.PASS, "MegaMenu Spanish top level click passed.");
	}
	
}
