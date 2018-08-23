package gov.nci.WebAnalytics;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import gov.nci.framework.ParsedURL;

public class AnalyticsRequest {
	
	// TODO: Remove buildParamsList/getList when possible	
	public URI uri;
	public String url;
	public List<NameValuePair> paramsList;
	public ParsedURL purl;
    private Map<String, String> myMap = new LinkedHashMap<String, String>();
	
	public AnalyticsRequest(String url) {
		try {
			this.url = url;
			this.uri = null;
			this.paramsList = new ArrayList<NameValuePair>();	

			System.out.println("Debug constructor     : " + myMap);
		} catch (Exception e) {
			System.out.println("Error initializing AnalyticsRequest. Check the value of the URL being passed in.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Build a collection of parameters
	 * @throws NullPointerException
	 */
	public void buildParamsList() throws NullPointerException {
		this.uri = createUri(url);
		
//		this.paramsList = myMap.toSomething();
		this.purl = new ParsedURL(url);		
		this.paramsList = this.getList(uri);
		System.out.println("Debug buildParamsList : " + myMap);	
	}
	
	/**
	 * Create URI object from a given URL string
	 * @param url (String)
	 * @return URI
	 */
	private static URI createUri(String url) {
		try {
			URI rtnUri = URI.create(url);
			return rtnUri;
		} catch (IllegalArgumentException ex) {
			System.out.println("Invalid request URL \"" + url + 
					"\\\" at AnalyticsRequest:createURI()");
			return null;
		}
	}
	

	
	/**
	 * Split URI into list of encoded elements
	 * @param uri
	 * @return retParams
	 */
	@Deprecated
	public static List<NameValuePair> getList(URI uri) {
		List<NameValuePair> rtnParams = new ArrayList<NameValuePair>();
		
		try {
			String queries = uri.getRawQuery(); // get encoded query string
			for(String parm : queries.split("&")) {
				String[] pair = parm.split("=");
				String Name = URLDecoder.decode(pair[0], "UTF-8");
				String value = "";
				if(pair.length > 1) {
					value = URLDecoder.decode(pair[1], "UTF-8"); 
				}
				rtnParams.add(new BasicNameValuePair(Name, value));				
			}
		} 
		catch (UnsupportedEncodingException ex) {
			System.out.println("Error decoding URI in WaParams:buildParamsList()");
		}		
		return rtnParams;
	}	
	
	/******************** Utility functions ****************************************/
	/**
	 * Utility function to check for a user-specified variable and value
	 * @param name
	 * @param value
	 * @return bool
	 */
	protected boolean hasVariable(String name, String value) {
		// TODO: fill this out
		return false;
	}
	
	/**
	 * Get a list of numbered parameters and their values (e.g. [prop1="www.cancer.gov", prop2="/home", prop3="NCI"])
	 * @param paramList
	 * @param parm
	 * @param replacement
	 * @return
	 */
	protected static List<NameValuePair> getNumberedParams(List<NameValuePair> paramList, String parm, String replacement) {
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
	 * Can be used for cases where the parameter name and analytics variable name match 
	 * @param paramList
	 * @param parm
	 * @return
	 */
	protected static List<NameValuePair> getNumberedParams(List<NameValuePair> paramList, String parm) {
		return getNumberedParams(paramList, parm, parm);
	}	
	
}
