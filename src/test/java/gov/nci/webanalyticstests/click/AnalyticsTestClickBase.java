package gov.nci.webanalyticstests.click;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.testng.annotations.BeforeMethod;

import gov.nci.WebAnalytics.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class AnalyticsTestClickBase extends AnalyticsTestBase {
	
	@BeforeMethod(groups = { "Analytics" }) 
	public void setupPageLoad() throws MalformedURLException, UnsupportedEncodingException {
		// maybe not needed
	}		

	/**
	 * Get the 'click' beacon to test
	 * @return AnalyticsRequest
	 */
	protected Beacon getClickBeacon() {
		setHarUrlList(proxy);
		setBeaconLists(harUrlList);
		Beacon rtn = getLastReq(clickBeacons);		
		System.out.println("Click beacon to test: ");
		System.out.println(rtn.url  + "\n");
		return getLastReq(clickBeacons);
	}
	
}