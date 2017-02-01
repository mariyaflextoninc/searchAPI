package com.test.automation.searchapi;

import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

/**
 * Automation for term parameter in SearchAPI
 * 
 * @author Mariya
 *
 */
public class SearchTermTest {
	SearchAPIAccess searchAPIObj;
	private LoadProperties propObject;
	private StringBuffer response;
	private Properties prop;
	private String url;
	private JSONObject jsonResObj;
	private static Logger log = Logger.getLogger(SearchTermTest.class);
	
	/**
	 * Validate T1 in term section
	 * 
	 * @param key
	 * @param value
	 * @throws NumberFormatException
	 * @throws JSONException
	 */
	@Test(dataProvider = "postiveTest")
	public void positiveTest(SearchParams params) throws NumberFormatException, JSONException {
		url = url + params.toString();
		log.info("url = " + url);
		searchAPIObj.getUrlConnection(url);
		try {
			jsonResObj = new JSONObject(searchAPIObj.getResponse().toString());
		} catch (JSONException e) {
			// Logger.getLogger(pClass);
		}
		log.info("response = " + searchAPIObj.getResponse());
		Assert.assertEquals(Integer.parseInt((String) jsonResObj.getString("resultCount")) > 0 ? true : false, true);
	}

	/**
	 * Validates T2 and T3 in term section
	 * 
	 * @param key
	 * @param value
	 * @throws JSONException
	 */
	@Test(dataProvider = "negativeTest")
	public void negativeTest(SearchParams params) throws JSONException {
		url = url + params.toString();
		log.info("url = " + url);
		searchAPIObj.getUrlConnection(url);
		jsonResObj = new JSONObject(searchAPIObj.getResponse().toString());
		log.info("response = " + searchAPIObj.getResponse());
		Assert.assertEquals(Integer.parseInt((String) jsonResObj.getString("resultCount")) == 0 ? true : false, true);

	}

	@BeforeMethod
	public void beforeMethod() {
		searchAPIObj = new SearchAPIAccess();
		prop = searchAPIObj.getProp();
		url = prop.getProperty("url");
	}

	@AfterMethod
	public void afterMethod() {
	}

	@DataProvider(name = "postiveTest")
	public Object[][] dpPositive() {
		return new Object[][] { { new SearchParams("zootopia", null, null, null) } };
	}

	@DataProvider(name = "negativeTest")
	public Object[][] dpNegative() {
		return new Object[][] { { new SearchParams("", null, null, null) },
				{ new SearchParams("@@48hjjjww", null, null, null) } };
	}

	@BeforeClass
	public void beforeClass() {

	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeTest
	public void beforeTest() {
	}

	@AfterTest
	public void afterTest() {
	}

	@BeforeSuite
	public void beforeSuite() {
		propObject = new LoadProperties();
		prop = propObject.getPropertiesFromFile();
	}

	@AfterSuite
	public void afterSuite() {
	}
}
