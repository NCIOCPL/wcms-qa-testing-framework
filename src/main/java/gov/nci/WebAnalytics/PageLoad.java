package gov.nci.WebAnalytics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class PageLoad extends AnalyticsBase {

	// Local driver object
	public WebDriver driver;
	
	// Constructor to initialize the page object	
	public PageLoad(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.print("PageFactory initialized for load events: ");
	}
	
	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */
	public void loadPageTypes() {
		System.out.print("Navigate to several page types");		
		driver.navigate().to(WANav.homePage);
		driver.navigate().to(WANav.landingPage);
		driver.navigate().to(WANav.cthpPatient);
		driver.navigate().to(WANav.cthpHP);
		driver.navigate().to(WANav.appModulePage);
		driver.navigate().to(WANav.blogSeriesPage);
	}

	public void loadHomePage() {
		driver.navigate().to(WANav.homePage	);
	}
	
	public void loadBlogPostPage() {
		driver.navigate().to(WANav.blogPostPage);
	}
	
	public void loadBlogSeriesPage() {
		driver.navigate().to(WANav.blogSeriesPage);
	}
	
	public void loadCTHPPatient() {
		driver.navigate().to(WANav.cthpPatient);
	}
	
	public void loadCTHPHP() {
		driver.navigate().to(WANav.cthpHP);	
	}
	
	public void loadInnerPage() {
		driver.navigate().to(WANav.innerPage);
	}
	
	public void loadLandingPage() {
		driver.navigate().to(WANav.landingPage);
	}
	
	public void loadPDQPage() {
		driver.navigate().to(WANav.pdqPage);
	}
	
	public void loadTopicPage() {
		driver.navigate().to(WANav.topicPage);
	}
	
	public void loadSpanishPage() {
		driver.navigate().to(WANav.spanishPage);
	}
	
	public void loadAppModulePage() {
		driver.navigate().to(WANav.appModulePage);
	}
	
	public void loadBasicSearchPage() {
		driver.navigate().to(WANav.basicSearchPage);
	}
	
	public void loadAdvSearchPage() {
		driver.navigate().to(WANav.advSearchPage);
	}
	
	public void loadResultsPage() {	
		driver.navigate().to(WANav.resultsPage);
	}
	
	public void goHomeAndBack() {
		// Home page
		driver.navigate().to(WANav.homePage);
		driver.navigate().to(WANav.spanishPage);
		driver.navigate().back();
		driver.navigate().refresh();
	}	
	
	/// Click around pages
	public void doPageLoadActions() throws RuntimeException {
		System.out.println("Begin PageLoad actions");
		loadPageTypes();
		goHomeAndBack();		
		System.out.println("End PageLoad actions");
	}
	
}
