package com.test.automation.searchapi;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

import org.json.JSONObject;


public class SearchAPIAccess {
	private  LoadProperties propObject;
	private StringBuffer response;
	private Properties prop;
	private int resCode;
	
	public LoadProperties getPropObject() {
		return propObject;
	}

	public void setPropObject(LoadProperties propObject) {
		this.propObject = propObject;
	}

	public StringBuffer getResponse() {
		return response;
	}

	public void setResponse(StringBuffer response) {
		this.response = response;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	
	
	public SearchAPIAccess(){
		propObject = new LoadProperties();
		prop = propObject.getPropertiesFromFile();
	}
	
	
	/**
	 * Getting the response from flickr url
	 * @param resposnseURL
	 */
	public void getUrlConnection(String resposnseURL){
		try{
			URL url = new URL(resposnseURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			resCode = conn.getResponseCode();
			response = new StringBuffer();
			if(resCode != 200){
				response.append(resCode);
				System.out.println("response code = "+resCode);
				conn.disconnect();
				//throw new RuntimeException("HTTP Error Code"+conn.getResponseCode());
			}
			else{
				Scanner scan = new Scanner(url.openStream());
				while(scan.hasNext()){
					response.append(scan.nextLine());
				}
				
			}
		}
		catch(Exception fe){
			fe.printStackTrace();
		}
		finally{
			
		}
	}
	/**
	 * Checks if given string is true or false
	 * @return boolean
	 */
	public boolean isInteger(String num){
		try{
			Integer.parseInt(num);
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
}
