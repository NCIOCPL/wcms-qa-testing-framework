package gov.nci.WebAnalytics;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class BeaconParams {

	public BeaconParams() {}

	static final String CHANNEL = "ch";
	static final String EVENTS = "events";	
	static final String LINKTYPE = "pe";
	static final String LINKNAME = "pev2";
	static final String LINKURL = "pev1";
	
	// Beacon properties
	public URI uri;
	public String[] suites;	
	public List<NameValuePair> params;
	public String channel;
	public String[] events;
	public List<NameValuePair> props; 
	public List<NameValuePair> eVars; 
	public List<NameValuePair> hiers;
	public String linkType;
	public String linkName;
	public String linkUrl;	
	
	/**
	 * Constructor
	 * @param beaconUrl
	 */
	public BeaconParams(String beaconUrl) {
		params = buildParamsList(uri);
	}
	
	
	/**
	 * Split URI into list of encoded elements
	 * @param uri
	 * @return retParams
	 * TODO: replace deprecated parse() method
	 */
	public List<NameValuePair> buildParamsList(URI uri) {
		List<NameValuePair> rtnParams = new ArrayList<>();
		rtnParams = URLEncodedUtils.parse(uri, "UTF-8");
		return rtnParams;
	}


	
	/**
	 * Check query params to see if this is a link tracking event
	 * @param parms
	 * @return
	 */
	public boolean hasParam(List<NameValuePair> paramList, String myParam) {
		for (NameValuePair param : paramList) {
			if (param.getName().toLowerCase().equals(myParam)) {
				return true;
			}
		}
		return false;
	}
	
	
}
