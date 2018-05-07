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
	public List<NameValuePair> props; 
	public List<NameValuePair> evars; 
	
	/**
	 * No-arg constructor
	 */
	public Beacon() {
		uri = null;
		params = new ArrayList<>();
		channel = "";
		events = new String[0];
		props = new ArrayList<>();
		evars = new ArrayList<>();
	}
	
	/**
	 * Constructor
	 * @param beaconUrl
	 * @throws MalformedURLException
	 */
	public Beacon(String beaconUrl) throws MalformedURLException {
		uri = URI.create(beaconUrl);
		params = BuildParamsList(uri);		
		channel = getChannel(params);
		events = getEvents(params);
		props = new ArrayList<>();
		evars = new ArrayList<>();
		String foo = "";
	}
	
	/**
	 * Split URI into list of encoded elements
	 * @param uri
	 * @return retParams
	 * TODO: replace deprecated parse() method
	 */
	protected List<NameValuePair> BuildParamsList(URI uri) {
		List<NameValuePair> rtnParams = new ArrayList<>();
		rtnParams = URLEncodedUtils.parse(uri, "UTF-8");
		return rtnParams;
	}
	
	/**
	 * Get channel value 
	 * @param parms
	 * @return
	 */
	public String getChannel(List<NameValuePair> parms) {
		String rtnChannel = "";
		for (NameValuePair param : parms) {
			if (param.getName().toLowerCase().equals("ch")) {
				rtnChannel = param.getValue();
				break;
			}
		}		
		return rtnChannel;
	}
	
	/**
	 * Get array of event values
	 * @param parms
	 * @return
	 */
	public String[] getEvents(List<NameValuePair> parms) {
		String rtnEvents = "";
		for (NameValuePair param : parms) {
			if (param.getName().toLowerCase().equals("events")) {
				rtnEvents = param.getValue();
				break;
			}
		}
		return rtnEvents.split(",");
	}
	
	/**
	 * Get array of props ('c' values in beacon)
	 * @param parms
	 * @return
	 */
	public List<String> getProps(List<NameValuePair> parms) {
		String evts = "";
		for (NameValuePair param : parms) {
			if (param.getName().toLowerCase().equals("events")) {
				evts = param.getValue();
				break;
			}
		}
		String[] ret = evts.split(",");
		return null;
	}	
	
	/**
	 * Get array of eVars ('v' values in beacon)
	 * @param parms
	 * @return
	 */
	/*
	public List<NameValuePair> getEvars(List<NameValuePair> parms) {
		List<NameValuePair> rtnEvars = new ArrayList<>();
		for (NameValuePair param : parms) {
			if(param.getName().matches("^[Vv][0-9]$")) {
				NameValuePair myPair = new NameValuePair();
				
				rtnEvars.add(e)
				
				evars.add(param.getValue());
			}
		}
		return evars;
	}
	*/
	
}
