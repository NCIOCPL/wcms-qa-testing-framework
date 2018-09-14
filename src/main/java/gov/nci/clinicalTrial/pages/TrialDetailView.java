package gov.nci.clinicalTrial.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import gov.nci.Utilities.ScrollUtil;
import gov.nci.clinicalTrial.common.Delighter;
import gov.nci.framework.AutoSuggestHelper;
import gov.nci.framework.ElementChange;

public class TrialDetailView extends ClinicalTrialPageObjectBase {

	WebDriver driver;
	Actions action;

	/*************** Basic Search Page UI Elements **********************/	
	@FindBy(how = How.XPATH, using = "//*[@id='cgvBody']/div[1]")
	private WebElement text_HeaderText;
	@FindBy(how = How.CSS, using = ".cts-results-info a")
	private WebElement lnk_backToResults;
	@FindBy(how = How.CSS, using = ".cts-start-over a")
	private WebElement lnk_startOver;
	@FindBy(how = How.CSS, using = ".accordion-controls .open-all")
	private WebElement btn_openAll;
	@FindBy(how = How.CSS, using = ".accordion-controls .close-all")
	private WebElement btn_closeAll;

	// Constructor - Initializing the Page objects
	public TrialDetailView(WebDriver driver, ClinicalTrialPageObjectBase decorator) throws MalformedURLException, UnsupportedEncodingException {
		super(driver, decorator);
		this.driver = driver;
		this.action = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	public void clickOpenAll() {
		btn_openAll.click();
	}

	public void clickCloseAll() {
		btn_closeAll.click();
	}

	public void clickBackToResults() {
		lnk_backToResults.click();
	}

	public void clickStartOver() {
		lnk_startOver.click();
	}

	public void clickStartOverNoNav() {
		ElementChange.removeHref(driver, ".cts-start-over a");
		clickStartOver();
	}
	
	public WebElement getHeaderText() {
		return text_HeaderText;
	}

	public WebElement getSection(String sectionId) {
		WebElement section = driver.findElement(By.cssSelector(".accordion #trial-" + sectionId + " h2"));
		return section;
	}
	
	public void clickSection(String sectionId) {
		WebElement section = getSection(sectionId);
		ScrollUtil.scrollIntoview(this.driver, section);
		section.click();
		action.pause(1000);
	}
//
//	// Make the search button visible so it can be clicked.
//	ScrollUtil.scrollIntoview(this.browser, btn_Search);
//
//	expectUrlChange(() ->{
}
