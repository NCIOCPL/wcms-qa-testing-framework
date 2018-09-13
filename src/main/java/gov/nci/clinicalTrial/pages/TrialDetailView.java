package gov.nci.clinicalTrial.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import gov.nci.Utilities.ScrollUtil;
import gov.nci.clinicalTrial.common.Delighter;
import gov.nci.framework.AutoSuggestHelper;

public class TrialDetailView extends ClinicalTrialPageObjectBase {

	WebDriver browser;

	/*************** Basic Search Page UI Elements **********************/	
	@FindBy(how = How.XPATH, using = "//*[@id='cgvBody']/div[1]")
	private WebElement text_HeaderText;
	@FindBy(how = How.CSS, using = ".cts-results-info a")
	private WebElement lnk_backToResults;
	@FindBy(how = How.CSS, using = ".accordion-controls .open-all")
	private WebElement btn_openAll;
	@FindBy(how = How.CSS, using = ".accordion-controls .close-all")
	private WebElement btn_closeAll;
		
	@FindBy(how = How.XPATH, using = "//fieldset[@id='fieldset--zip']/div[@class='error-msg']")
	private WebElement err_ZipCodeInputDisplay;

	// Constructor - Initializing the Page objects
	public TrialDetailView(WebDriver browser, ClinicalTrialPageObjectBase decorator) throws MalformedURLException, UnsupportedEncodingException {
		super(browser, decorator);
		this.browser = browser;
		PageFactory.initElements(browser, this);
	}

	public void clickOpenAll() {
		btn_openAll.click();
	}

	public void clickCloseAll() {
		btn_closeAll.click();
	}
		
	public WebElement getHeaderText() {
		return text_HeaderText;
	}

}
