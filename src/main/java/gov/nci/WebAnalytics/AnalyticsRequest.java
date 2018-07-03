package gov.nci.WebAnalytics;

import java.net.URI;
import java.util.List;
import org.apache.http.NameValuePair;

public class AnalyticsRequest {

	// Constants
	public static final String STATIC_SERVER = "static.cancer.gov";
	public static final String TRACKING_SERVER = "nci.122.2o7.net";	
	
	/**
	 * Setters and getters for request object
	 */	
	// Analytics params object
	private AnalyticsParams params; 
	public AnalyticsParams getParams() {
		return params;
	}
	public void setParams(AnalyticsParams params) {
		this.params = params;
	}
	
	// URI for tracking beacon
	private URI uri;	
	public URI getUri() {
		return uri;				
	}	
	public void setUri(URI uri) {
		this.uri = uri;
	}	

	// Suites
	private String[] suites;
	public String[] getSuites() {
		return suites;
	}
	public void setSuites(String[] suites) {
		this.suites = suites;
	}	

	// Events
	private String[] events;
	public String[] getEvents() {
		return events;
	}
	public void setEvents(String[] events) {
		this.events = events;
	}	
	
	// Channel
	private String channel;
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	// Link type
	private String linkType;
	public String getLlnkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	
	// Link name
	private String linkName;
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	// Link URL
	private String linkUrl;
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setlinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	// Props
	private List<NameValuePair> props; 
	public List<NameValuePair> getProps() {
		return props;
	}
	public void setProps(List<NameValuePair> props) {
		this.props = props;
	}

	// eVars
	private List<NameValuePair> eVars; 
	public List<NameValuePair> geteVars() {
		return eVars;
	}
	public void seteVars(List<NameValuePair> eVars) {
		this.eVars = eVars;
	}
	
	// Hiers
	private List<NameValuePair> hiers;
	public List<NameValuePair> getHiers() {
		return hiers;
	}
	public void setHiers(List<NameValuePair> hiers) {
		this.hiers = hiers;
	}

	/**
	 * No arg constructor
	 */
	public AnalyticsRequest() {}
	
	/**
	 * Constructor
	 * @param beaconUrl
	 */
	public AnalyticsRequest(String beaconUrl) {
		setUri(createURI(beaconUrl));
		setParams(new AnalyticsParams(getUri()));
		setSuites(getSuites(getUri()));
		setChannel(getChannel(params.getAll()));
		setEvents(getEvents(params.getAll()));
		setProps(getProps(params.getAll()));
		seteVars(getEvars(params.getAll()));
		setHiers(getHiers(params.getAll()));
		setLinkType(getLinkType(params.getAll()));
		setLinkName(getLinkName(params.getAll()));
		setlinkUrl(getLinkUrl(params.getAll()));
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
		return AnalyticsParams.getNumberedParams(parms, AnalyticsParams.PROP_PARTIAL, "prop");
	}
	
	/**
	 * Get list of eVars ('v' values in beacon)
	 * @param parms
	 * @return
	 */
	public List<NameValuePair> getEvars(List<NameValuePair> parms) {
		return AnalyticsParams.getNumberedParams(parms, AnalyticsParams.EVAR_PARTIAL, "eVar");
	}
	
	/**
	 * Get list of hierarchy values ("h" values in beacon)
	 * @param parms
	 * @return
	 */
	public List<NameValuePair> getHiers(List<NameValuePair> parms) {
		return AnalyticsParams.getNumberedParams(parms, AnalyticsParams.HIER_PARTIAL, "hier");
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

}
