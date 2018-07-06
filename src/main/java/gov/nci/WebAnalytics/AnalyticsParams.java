package gov.nci.WebAnalytics;

import java.net.URI;

public class AnalyticsParams {
	
	// Parameter values from URL
	static final String CHANNEL = "ch";
	static final String EVENTS = "events";	
	static final String LINKTYPE = "pe";
	static final String LINKNAME = "pev2";
	static final String LINKURL = "pev1";
	
	// Partial parameter values. Each prop, eVar, and hier is its own query parameter. 
	// The getNumberedParams() method handles the logic of appending the number values
	// to each of these query parameter
	static final String PROP_PARTIAL = "c";
	static final String EVAR_PARTIAL = "v";
	static final String HIER_PARTIAL = "h";
	
	/**
	 * Constructor
	 * @param beaconUrl
	 */
	public AnalyticsParams(URI uri) {
	}
	
	
}
