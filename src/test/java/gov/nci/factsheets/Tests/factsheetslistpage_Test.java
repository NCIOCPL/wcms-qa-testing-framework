package gov.nci.factsheets.Tests;

import java.net.MalformedURLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.relevantcodes.extentreports.LogStatus;

import gov.nci.Utilities.BrowserManager;
import gov.nci.clinicaltrials.BaseClass;
import gov.nci.commonobjects.BreadCrumb;
import gov.nci.factsheets.FactSheetsListPage;



public class factsheetslistpage_Test extends BaseClass {

	public final String FACTSHEETLISTPAGE_Browser_TITLE = "Fact Sheets - National Cancer Institute";
    public static final String FACTSHEETLISTPAGE_PAGE_TITLE = "NCI Fact Sheets";
	public static final String FACTSHEETSLISTPAGE_INTRO_TEXT = "The NCI fact sheet collection addresses a variety of cancer topics. Fact sheets are updated and revised based on the latest cancer research.";
    public final String BREAD_CRUMB = "Home\nPublications";
    public static final List<String> EXP_FACTSHEETLISTS_ON_FACTSHEETSLISTPAGE= Lists.newArrayList("Cancer Therapy","Cancer Types",
			"Detection and Diagnosis", "Diet and Nutrition", "Prevention", "Risk Factors and Possible Causes", "Support, Coping, and Resources",
			"Tobacco and Smoking Cessation", "En Espa�ol");

	FactSheetsListPage fslp;
	BreadCrumb crumb;


	@BeforeClass
	@Parameters({ "browser" })
	public void setup(String browser) throws MalformedURLException {
		logger = report.startTest(this.getClass().getSimpleName());
		pageURL = config.getProperty("FactSheetsListPageURL");
		driver = BrowserManager.startBrowser(browser, config, pageURL);
		System.out.println("FactSheetsList Page setup done");
	}

	@Test
	public void verifyFSlistPageBrowserTitle() {
		driver.get(pageURL);
		fslp = new FactSheetsListPage(driver, logger);
		Assert.assertEquals(driver.getTitle(), FACTSHEETLISTPAGE_Browser_TITLE);
	logger.log(LogStatus.PASS, "Verify that Browser Title of the page is *Fact Sheets - National Cancer Institute* | Actual Result: "
				+ driver.getTitle());
	}

	@Test
	public void verifyFSlistPageH1Title() {
		driver.get(pageURL);
		fslp = new FactSheetsListPage(driver, logger);
		Assert.assertEquals(fslp.getPageH1Title().getText(),FACTSHEETLISTPAGE_PAGE_TITLE);
		logger.log(LogStatus.PASS, "Verify that H1 Title of the page is *NCI Fact Sheets* | Actual Result: "
				+ fslp.getPageH1Title());
	}

	@Test
	public void verifyfactsheetDesciptionText() {
		driver.get(pageURL);
		fslp = new FactSheetsListPage(driver, logger);
		Assert.assertTrue(fslp.getPageIntroText().getText().contains(FACTSHEETSLISTPAGE_INTRO_TEXT));
		logger.log(LogStatus.PASS, "Verify that intro text is displayed below the title of the page");
    }

	@Test
	public void verifyBreadCrumb() {
		driver.get(pageURL);
		fslp = new FactSheetsListPage(driver, logger);
		crumb = new BreadCrumb(driver);
		Assert.assertEquals(crumb.getBreadCrumbText(), BREAD_CRUMB);
		System.out.println("Breadcrumb is displaying correctly");
		logger.log(LogStatus.PASS, "Pass => " + "Verifying the Breadcrumb of the page");
	}
}
