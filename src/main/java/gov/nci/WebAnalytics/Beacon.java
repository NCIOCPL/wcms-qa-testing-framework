package gov.nci.WebAnalytics;

import java.net.MalformedURLException;
import java.net.URL;

public class Beacon {

	public URL url;
	public String[] queries;
	public String channel;
	public String[] events;
	public String[] props; 
	public String[] evars; 
	
	// Constructor
	public Beacon() {
		url = null;
		queries = new String[0];
		channel = "";
		events = new String[0];
		props = new String[0];
		evars = new String[0];		
	}
	
	// Constructor
	public Beacon(String beacon) throws MalformedURLException {
		url = new URL(beacon);
		channel = "";
		queries = new String[0];
		events = new String[0];
		props = new String[0];
		evars = new String[0];
		
		String query = url.getQuery();
		query = "debug: " + query;
	}
	
	//protected String[] getQueries(String url) {
		//return url;
	//}
	
	
}
