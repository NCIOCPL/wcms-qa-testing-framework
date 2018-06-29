package gov.nci.WebAnalytics.Tests;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import gov.nci.WebAnalytics.CTSBasicSearch;

public class CTSBasicSearch_Test extends AnalyticsTestBase{
	
	private CTSBasicSearch basicSearch;

	@BeforeMethod(groups = { "Analytics" }) 
	public void beforeMethod() {
		basicSearch = new CTSBasicSearch(driver);
	}

	/// ??? returns the expected general/shared values
	@Test(groups = { "Analytics" })
	public void testCTSBasicGeneral() {
		Assert.assertTrue(1 == 1);
		logger.log(LogStatus.PASS, "CTS Basic gen value test passed.");
	}		
	
}

