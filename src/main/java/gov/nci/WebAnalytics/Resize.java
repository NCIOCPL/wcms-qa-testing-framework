package gov.nci.WebAnalytics;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Resize extends AnalyticsBase {
		
	public Resize() {		
	}
	
	public Resize(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("Resize PageFactory initialized");
	}	
	
	/**
	 * All the proxy browser 'actions' go in here. These are not tests, but things that we do 
	 * to fire off analytics events. These actions will populate our list of har objects, which will
	 * then be tested.
	 * @throws RuntimeException
	 */	
	/*** Resize browser ***/
	public void resizeBrowser() throws RuntimeException {
		Dimension small = new Dimension(300, 800);
		Dimension med = new Dimension(700, 800);
		Dimension large = new Dimension(1100, 800);
		Dimension xlarge = new Dimension(1600, 800);

		System.out.println("Begin Resize actions");		
		driver.navigate().to(homePage);
		driver.manage().window().setSize(xlarge);
		driver.manage().window().setSize(large);		
		driver.manage().window().setSize(med);
		driver.manage().window().setSize(small);
		driver.manage().window().maximize();
		System.out.println("Done Resize actions");
	}
	
}
