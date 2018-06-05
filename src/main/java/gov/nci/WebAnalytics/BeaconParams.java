package gov.nci.WebAnalytics;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class BeaconParams {

	public BeaconParams() {}

	static final String CHANNEL = "ch";
	static final String EVENTS = "events";	
	static final String LINKTYPE = "pe";
	static final String LINKNAME = "pev2";
	static final String LINKURL = "pev1";
	static final String PROP_PARTIAL = "c";
	static final String EVAR_PARTIAL = "v";
	static final String HIER_PARTIAL = "h";
	
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
	
	/**
	 * Get a list of numbered parameters and their values (e.g. [prop1="www.cancer.gov", prop2="/home", prop3="NCI"])
	 * @param paramList
	 * @param parm
	 * @param replacement
	 * @return
	 */
	public static List<NameValuePair> getNumberedParams(List<NameValuePair> paramList, String parm, String replacement) {
		List<NameValuePair> rtnList = new ArrayList<>();
		for (NameValuePair param : paramList) {
			// Regex: parameter name followed by 1 or more digits, starting with 1-9 only
			if(param.getName().matches("^" + parm + "[1-9]\\d*$")) {
				String rtnName = param.getName().replace(parm, replacement);
				String rtnValue = param.getValue();
				rtnList.add(new BasicNameValuePair(rtnName, rtnValue));
			}
		}
		return rtnList;
	}
	
	/**
	 * Overload for getNumberedParams
	 * @param paramList
	 * @param parm
	 * @return
	 */
	public static List<NameValuePair> getNumberedParams(List<NameValuePair> paramList, String parm) {
		return getNumberedParams(paramList, parm, parm);
	}	
	
}
