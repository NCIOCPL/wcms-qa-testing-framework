package gov.nci.WebAnalytics;

public enum AnalyticsParams {
	// Parameter values from URL
	CHANNEL("ch"),
	EVENTS("events"),	
	LINKTYPE("pe"),
	LINKNAME("pev2"),
	LINKURL("pev1"),
	
	// Partial parameter values. Each prop, eVar, and hier is its own query parameter. 
	// The getNumberedParams() method handles the logic of appending the number values
	// to each of these query parameter
	PROP_PARTIAL("c"),
	EVAR_PARTIAL("v"),
	HIER_PARTIAL("h");

	private String param;
	
	AnalyticsParams() {}
	
	AnalyticsParams(String param) {
		this.param = param;
	}
	
	public String getParam() {
		return param;
	}
	
}
