package gov.nci.webanalyticstests.click;

import gov.nci.webanalytics.adobe.Beacon;
import gov.nci.webanalyticstests.AnalyticsTestBase;

public class AnalyticsTestClickBase extends AnalyticsTestBase {
	
	/**
	 * Get the 'click' beacon to test
	 * @return AnalyticsRequest
	 */
	protected Beacon getBeacon() {
		setHarUrlList(proxy);
		setBeaconLists(harUrlList);
		Beacon rtn = getLastReq(clickBeacons);		
		System.out.println("Click beacon to test: ");
		System.out.println(rtn.url  + "\n");
		return getLastReq(clickBeacons);
	}
	
}