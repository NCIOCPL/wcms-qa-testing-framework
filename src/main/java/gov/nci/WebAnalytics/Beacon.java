package gov.nci.WebAnalytics;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class Beacon {

	public URI uri;
	public List<NameValuePair> params;
	public String channel;
	public String[] events;
	public String[] props; 
	public String[] evars; 
	
	// Constructor
	public Beacon() {
		uri = null;
		params = new ArrayList<>();
		channel = "";
		events = new String[0];
		props = new String[0];
		evars = new String[0];		
	}
	
	// Constructor
	public Beacon(String beacon) throws MalformedURLException {
		uri = URI.create(beacon);
		params = BuildParamsList(uri);
		
		channel = "";		
		events = new String[0];
		props = new String[0];
		evars = new String[0];
	}
	
	protected List<NameValuePair> BuildParamsList(URI uri) {
		List<NameValuePair> retParams = URLEncodedUtils.parse(uri, "UTF-8");
		return retParams;
	}
	

	
}
