package gov.nci.WebAnalytics;

public class RequestBeacon {

	public String url;
	public String channel;
	public String[] events;
	public String[] props; 
	public String[] evars; 
	
	// Constructor
	public RequestBeacon() {
		url = "";
		channel = "";
		events = new String[0];
		props = new String[0];
		evars = new String[0];		
	}
	
	// Constructor
	public RequestBeacon(String beacon) {
		url = beacon;
		channel = "";
		events = new String[0];
		props = new String[0];
		evars = new String[0];		
	}
	
	
}
