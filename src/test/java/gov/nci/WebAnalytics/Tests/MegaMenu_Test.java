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
	
	/// Megamenu click returns the expected general/shared values
	@Test(groups = { "Analytics" })
	public void testMMGeneral() {
		megaMenu.clickMMBarEn();
		clickBeacons = AnalyticsBase.getClickBeacons(getHarUrlList(proxy));
		Assert.assertTrue(hasProp(clickBeacons, 4, "d=pev1"));
		Assert.assertTrue(hasProp(clickBeacons, 67, "D=pageName"));
		Assert.assertTrue(haseVar(clickBeacons, 2, "English"));
		logger.log(LogStatus.PASS, "MegaMenu gen value test passed.");
	}	
	

	
}
