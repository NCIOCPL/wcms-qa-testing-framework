package gov.nci.WebAnalytics;

import java.net.URI;
import java.util.List;
import org.apache.http.NameValuePair;

public class AnalyticsRequest {
	
	// Constants
	public static final String STATIC_SERVER = "static.cancer.gov";
	public static final String TRACKING_SERVER = "nci.122.2o7.net";
	
	// Analytics base fields
	public URI uri;
	public String[] suites;	
	public WAParams params; 
	public String channel;
	public String[] events;
	public List<NameValuePair> props; 
	public List<NameValuePair> eVars; 
	public List<NameValuePair> hiers;
	public String linkType;
	public String linkName;
	public String linkUrl;	
	
	/**
	 * No arg constructor
	 */
	public AnalyticsRequest() {
		uri = null;
		suites = new String[0];		
		params = null;
	}
	
	/**
	 * Constructor
	 * @param beaconUrl
	 */
	public AnalyticsRequest(String beaconUrl) {
		uri = createURI(beaconUrl);
		params = new WAParams(uri);
		suites = getSuites(uri);
		channel = getChannel(params.all);
		events = getEvents(params.all);
		props = getProps(params.all);
		eVars = getEvars(params.all);
		hiers = getHiers(params.all);
		linkType = getLinkType(params.all);
		linkName = getLinkName(params.all);
		linkUrl = getLinkUrl(params.all);
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
			if (param.getName().equalsIgnoreCase(WAParams.CHANNEL)) {
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
			if (param.getName().equalsIgnoreCase(WAParams.EVENTS)) {
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
		return WAParams.getNumberedParams(parms, WAParams.PROP_PARTIAL, "prop");
	}
	
	/**
	 * Get list of eVars ('v' values in beacon)
	 * @param parms
	 * @return
	 */
	public List<NameValuePair> getEvars(List<NameValuePair> parms) {
		return WAParams.getNumberedParams(parms, WAParams.EVAR_PARTIAL, "eVar");
	}
	
	/**
	 * Get list of hierarchy values ("h" values in beacon)
	 * @param parms
	 * @return
	 */
	public List<NameValuePair> getHiers(List<NameValuePair> parms) {
		return WAParams.getNumberedParams(parms, WAParams.HIER_PARTIAL, "hier");
	}

	/**
	 * Get "Link Type" value (pe)(
	 * @param parms
	 * @return
	 */
	public String getLinkType(List<NameValuePair> parms) {
		for (NameValuePair param : parms) {
			if (param.getName().equalsIgnoreCase(WAParams.LINKTYPE)) {
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
			if (param.getName().equalsIgnoreCase(WAParams.LINKNAME)) {
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
			if (param.getName().equalsIgnoreCase(WAParams.LINKURL)) {
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
			if (param.getName().equalsIgnoreCase(WAParams.LINKTYPE)) {
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

}
