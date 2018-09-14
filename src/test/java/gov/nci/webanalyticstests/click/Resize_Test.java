package gov.nci.webanalyticstests.click;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import gov.nci.commonobjects.Resize;
import gov.nci.webanalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class Resize_Test extends AnalyticsTestClickBase {
	
	private Resize resize;
	private Beacon beacon;	
	
	@BeforeMethod(groups = { "Analytics" }) 
	public void setupResize() throws MalformedURLException, UnsupportedEncodingException {
		resize = new Resize(driver);
	}

	// TODO: Fix random failure errors (this may be doing the resizes too quickly - set timeout if needed)
	/// Correct click events have been captured on resize
	@Test(groups = { "Analytics" }, priority = 1)
	public void testResizeGeneral() {
		System.out.println("Test cycle throuh all sizes: ");
		resize.toXlarge();
		resize.toLarge();
		resize.toMed();
		resize.toSmall();
		resize.maximize();
		beacon = getBeacon();

		doCommonClickAssertions(beacon);
		Assert.assertFalse(beacon.hasEvent(1));
		Assert.assertEquals(beacon.props.get(8), "english");
		logger.log(LogStatus.PASS, "Resize gen value test passed.");		
	}
	
	/// Correct linkName captured on small resize
	@Test(groups = { "Analytics" }, priority = 2)
	public void testResizeToMobile() {
		System.out.println("Test resize to small: ");
		resize.toSmall();
		beacon = getBeacon();
		
		doCommonClickAssertions(beacon);		
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertEquals(beacon.linkName, "ResizedToMobile");	
		logger.log(LogStatus.PASS, "'Resize to mobile' values are correct.");
	}

	/// Correct linkName captured on medium resize
	@Test(groups = { "Analytics" }, priority = 3)
	public void testResizeToTablet() {
		System.out.println("Test resize to medium: ");
		resize.toMed();
		beacon = getBeacon();
		
		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertEquals(beacon.linkName, "ResizedToTablet");
		logger.log(LogStatus.PASS, "'Resize to tablet' values are correct.");
	}

	/// Correct linkName captured on large resize
	@Test(groups = { "Analytics" }, priority = 4)
	public void testResizeToDesktop() {
		System.out.println("Test resize to large: ");		
		resize.toLarge();
		beacon = getBeacon();
		
		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertEquals(beacon.linkName, "ResizedToDesktop");
		logger.log(LogStatus.PASS, "'Resize to desktop' values are correct.");
	}

	/// Correct linkName captured on XL resize
	@Test(groups = { "Analytics" }, priority = 5)
	public void testResizeToExtraWide() {
		System.out.println("Test resize to x-large: ");
		resize.toXlarge();
		beacon = getBeacon();
		
		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(7));
		Assert.assertEquals(beacon.linkName, "ResizedToExtra wide");
		logger.log(LogStatus.PASS, "'Resize to extra wide' values are correct.");
	}

	/// Correct event on maximize
	@Test(groups = { "Analytics" }, priority = 6)
	public void testMaximize() {
		System.out.println("Test maximize: ");
		resize.maximize();
		beacon = getBeacon();
		
		doCommonClickAssertions(beacon);
		Assert.assertTrue(beacon.hasEvent(7));
		logger.log(LogStatus.PASS, "Maximize values are correct.");
	}	
	
}
