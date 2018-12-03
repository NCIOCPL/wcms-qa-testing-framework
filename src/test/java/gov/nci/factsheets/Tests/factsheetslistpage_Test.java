package gov.nci.factsheets.Tests;

import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;
import gov.nci.Utilities.BrowserManager;
import gov.nci.clinicaltrials.BaseClass;
import gov.nci.commonobjects.Banner;
import gov.nci.commonobjects.BreadCrumb;
import gov.nci.factsheets.FactSheetsListPage;



public class factsheetslistpage_Test extends BaseClass {


	FactSheetsListPage fslp;
	//Resources4ResearchersSearchResult r4rSearchResult;
	//AdvanceSearch advanceSearch;
	//BreadCrumb crumb;
	//Banner banner;
	//String testDataFilePath;

	@BeforeClass(groups = { "Smoke", "current" })
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("FactSheetsListPageURL");
		System.out.println("PageURL: " + pageURL);
		driver = BrowserManager.startBrowser(browser, config, pageURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		fslp = new FactSheetsListPage(driver, logger);
		
		System.out.println("FactSheetsList Page setup done");
	}
	
	
	
	@Test(groups = { "Smoke" })
	public void verifyFSlistPageBrowserTitle() {
		//System.out.println(driver.getTitle());
		Assert.assertEquals(driver.getTitle(), fslp.FACTSHEETLISTPAGE_Browser_TITLE);
	logger.log(LogStatus.PASS, "Verify that Browser Title of the page is *Fact Sheets - National Cancer Institute* | Actual Result: "
				+ driver.getTitle());
	
	}
	
	
		
	//@Test(groups = { "Smoke" })
	public void verifyFSlistPageH1Title() {
		Assert.assertEquals(fslp.getPageH1Title().getText(), fslp.FACTSHEETLISTPAGE_PAGE_TITLE);
		logger.log(LogStatus.PASS, "Verify that H1 Title of the page is *NCI Fact Sheets* | Actual Result: "
				+ fslp.getPageH1Title());
	}

	
	
	
	
}