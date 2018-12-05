package gov.nci.factsheets;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.google.common.collect.Lists;
import com.relevantcodes.extentreports.ExtentTest;



public class FactSheetsListPage {

	WebDriver driver;
	ExtentTest logger;

	public final String FACTSHEETLISTPAGE_Browser_TITLE = "Fact Sheets - National Cancer Institute";
	public static final String FACTSHEETLISTPAGE_PAGE_TITLE = "NCI Fact Sheets";
	public static final String FACTSHEETSLISTPAGE_INTRO_TEXT = "The NCI fact sheet collection addresses a variety of cancer topics. Fact sheets are updated and revised based on the latest cancer research.";
	public final String BREAD_CRUMB = "Home\nPublications";
	public static final List<String> FACTSHEETSUBJECTLISTS_ON_FACTSHEETSLISTPAGE= Lists.newArrayList("Cancer Therapy","Cancer Types",
			"Detection and Diagnosis", "Diet and Nutrition", "Prevention", "Risk Factors and Possible Causes", "Support, Coping, and Resources",
			"Tobacco and Smoking Cessation", "En Español");
	

	    /**************** FactSheets Page WebElements *****************************/
	    @FindBy(how = How.XPATH, using = ".//h1")
	    WebElement factsheetslist_PageTitle;
	    @FindBy(how = How.XPATH, using = "//div[@class='contentid-909853 slot-item first-SI']/p")
	    WebElement text_FSListIntroText;
	    @FindBy(how = How.XPATH, using = "//*[@id='header']/title")
		WebElement factsheetslist_BrowserTitle;
		
	   

	    public FactSheetsListPage(WebDriver driver, ExtentTest logger) {
			this.driver = driver;
			this.logger = logger;
			PageFactory.initElements(driver, this);
		}

	
	   
	    
	    // Testing if the H1 header element exists on the page
	    // ---------------------------------------------------
	    
	    public WebElement getPageH1Title() {
			return factsheetslist_PageTitle;
		}
	    

	 // Verifying the Description Text
	    // ---------------------------------------------------
	   
		public WebElement getPageIntroText() {
			return text_FSListIntroText;
		}


}

	    
		
			
			
		
		
