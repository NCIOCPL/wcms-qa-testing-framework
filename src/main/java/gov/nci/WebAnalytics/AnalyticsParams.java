package gov.nci.WebAnalytics;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

public class AnalyticsParams {

	
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


	
}