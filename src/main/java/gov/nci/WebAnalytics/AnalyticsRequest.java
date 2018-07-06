package gov.nci.WebAnalytics;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class AnalyticsRequest {

	// Constants
	public static final String STATIC_SERVER = "static.cancer.gov";
	public static final String TRACKING_SERVER = "nci.122.2o7.net";
	
	
	// URI for tracking beacon
	private URI uri;	
	public URI getUri() {
		return uri;				
	}	
	public void setUri(URI uri) {
		this.uri = uri;
	}	
	
	// Channel
	private String channel;
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String[] suites;	
	public AnalyticsParams params; 
	public String[] events;
	public List<NameValuePair> props; 
	public List<NameValuePair> eVars; 
	public List<NameValuePair> hiers;
	public String linkType;
	public String linkName;
	public String linkUrl;	
	
	public AnalyticsRequest() {}
	
	/**
	 * Constructor
	 * @param beaconUrl
	 */
	public AnalyticsRequest(String beaconUrl) {
		setUri(createURI(beaconUrl));
		
		/// TODO: get rid of everything but URL - access methods from outside of the constructor
		
		params = new AnalyticsParams(uri);
		suites = getSuites(uri);
		// setChannel(getChannel(params.getAll()));
		channel = getChannel(buildParamsList(uri));
		events = getEvents(buildParamsList(uri));
		props = getProps(buildParamsList(uri));
		eVars = getEvars(buildParamsList(uri));
		hiers = getHiers(buildParamsList(uri));
		linkType = getLinkType(buildParamsList(uri));
		linkName = getLinkName(buildParamsList(uri));
		linkUrl = getLinkUrl(buildParamsList(uri));
	}
	
	/**
	 * Create URI object from a given URL string
	 * @param url
	 * @return
	 */
	public URI createURI(String url) {
		try {
			URI rtnUri = URI.create(url);
			return rtnUri;
		} catch (IllegalArgumentException ex) {
			System.out.println("Invalid beacon URL \"" + url + "\\\" at AnalyticsBase:createURI()");
			return null;
		}
	}
	
	/**
	 * Get the reporting suites (s_account) value
	 * as an array of strings
	 * @param uri
	 * @return
	 */
	protected String[] getSuites(URI uri) {
		try {
			String[] path = uri.getPath().split("/");
			String[] rtnSuites = path[3].split(",");
			return rtnSuites;
		} 
		catch(ArrayIndexOutOfBoundsException ex) {
			System.out.println("Invalid URI path: \"" + uri.getPath() + "\\\" at AnalyticsBase:getSuitesI()");			
			return null;
		}
	}
	
	/**
	 * Get channel value 
	 * @param parms
	 * @return
	 */
	public String getChannel(List<NameValuePair> parms) {
		for (NameValuePair param : parms) {
			if (param.getName().equalsIgnoreCase(AnalyticsParams.CHANNEL)) {
				return param.getValue().trim();
			}
		}
		return "";
	}
	
	/**
	 * Get array of event values
	 * @param parms
	 * @return
	 */
	public String[] getEvents(List<NameValuePair> parms) {
		String rtnEvents = "";
		for (NameValuePair param : parms) {
			if (param.getName().equalsIgnoreCase(AnalyticsParams.EVENTS)) {
				rtnEvents = param.getValue();
				break;
			}
		}
		return rtnEvents.split(",");
	}
	
	/**
	 * Get list of props ('c' values in beacon)
	 * @param parms
	 * @return
	 */
	public List<NameValuePair> getProps(List<NameValuePair> parms) {
		return getNumberedParams(parms, AnalyticsParams.PROP_PARTIAL, "prop");
	}
	
	/**
	 * Get list of eVars ('v' values in beacon)
	 * @param parms
	 * @return
	 */
	public List<NameValuePair> getEvars(List<NameValuePair> parms) {
		return getNumberedParams(parms, AnalyticsParams.EVAR_PARTIAL, "eVar");
	}
	
	/**
	 * Get list of hierarchy values ("h" values in beacon)
	 * @param parms
	 * @return
	 */
	public List<NameValuePair> getHiers(List<NameValuePair> parms) {
		return getNumberedParams(parms, AnalyticsParams.HIER_PARTIAL, "hier");
	}

	/**
	 * Get "Link Type" value (pe)(
	 * @param parms
	 * @return
	 */
	public String getLinkType(List<NameValuePair> parms) {
		for (NameValuePair param : parms) {
			if (param.getName().equalsIgnoreCase(AnalyticsParams.LINKTYPE)) {
				return param.getValue().trim();
			}
		}
		return "";
	}

	/**
	 * Get "Link Name" value (pev2)(
	 * @param parms
	 * @return
	 */	
	public String getLinkName(List<NameValuePair> parms) {
		for (NameValuePair param : parms) {
			if (param.getName().equalsIgnoreCase(AnalyticsParams.LINKNAME)) {
				return param.getValue().trim();
			}
		}
		return "";
	}

	/**
	 * Get "Link URL" value (pev1)(
	 * @param parms
	 * @return
	 */		
	public String getLinkUrl(List<NameValuePair> parms) {
		for (NameValuePair param : parms) {
			if (param.getName().equalsIgnoreCase(AnalyticsParams.LINKURL)) {
				return param.getValue().trim();
			}
		}
		return "";
	}
	
	/**
	 * Check for parameters to verify that this is a link event
	 * @param paramList
	 * @return
	 */
	public boolean hasLinkType(List<NameValuePair> paramList) {
		for (NameValuePair param : paramList) {
			if (param.getName().equalsIgnoreCase(AnalyticsParams.LINKTYPE)) {
				return true;
			}
		}
		return false;
	}
		
	/**
	 * Temporary util method for troubleshooting
	 * TODO: remove this once explicit wait is working 
	 * @param sec
	 */
	protected static void nap(int sec) {
		long ms = new Long(sec*1000);		
		try { 
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
			System.out.println("AnalyticsBase:nap() failed");
		}
	}
	protected static void nap() {
		nap(10);
	}
	

	
	
	
	/***** URL Param methods *****/

	/**
	 * Split URI into list of encoded elements
	 * @param uri
	 * @return retParams
	 */
	public List<NameValuePair> buildParamsList(URI uri) {		
		List<NameValuePair> rtnParams = new ArrayList<NameValuePair>();
		
		try {
			String queries = getRawQueryString(uri);
			for(String parm : queries.split("&")) {
				String[] pair = parm.split("=");
				String key = URLDecoder.decode(pair[0], "UTF-8");
				String value = "";
				if(pair.length > 1) {
					value = URLDecoder.decode(pair[1], "UTF-8"); 
				}
				rtnParams.add(new BasicNameValuePair(key, value));				
			}
		} 
		catch (UnsupportedEncodingException ex) {
			System.out.println("Error decoding URI in WaParams:buildParamsList()");
		}		
		return rtnParams;
	}
	
	/**
	 * Get an encoded query string from a given URI.
	 * @param uri
	 * @return
	 */	
	public static String getRawQueryString(URI uri) {
		return uri.getRawQuery();
	}
	
	/**
	 * Get the clean query string from a given URI.
	 * @param uri
	 * @return
	 */
	public static String getQueryString(URI uri) {
		return uri.getQuery();
	}
	
	/**
	 * Check query params to see if this is a link tracking event
	 * @param parms
	 * @return
	 */
	public static boolean hasParam(List<NameValuePair> paramList, String myParam) {
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
