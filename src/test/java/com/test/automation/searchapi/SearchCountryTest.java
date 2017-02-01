package com.test.automation.searchapi;

import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

/**
 * Automation for Country parameter of SearchAPI
 * 
 * @author Mariya
 *
 */
public class SearchCountryTest {
	SearchAPIAccess searchAPIObj;
	private LoadProperties propObject;
	private Properties prop;
	private String url;
	private JSONObject jsonResObj;
	private JSONObject expectedResObj;
	private JSONObject tempObj;
	private static Logger log = Logger.getLogger(SearchCountryTest.class);
	
	
	/**
	 * Validates T4, T5 and T8 in Country section
	 * 
	 * @param params
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
		if (params.getCountry() == "") {
			expectedResObj = jsonResObj;
		} else if (params.getCountry() == "US") {
			JSONAssert.assertEquals(jsonResObj, expectedResObj, false);
		} else if (params.getCountry() == "IN") { // test filtering : comparing
													// previous response with
													// current response
			JSONAssert.assertNotEquals(jsonResObj, tempObj, false);
		}
		tempObj = jsonResObj; // saving the previous response
	}

	/**
	 * Validate tests T7 in Country section
	 * 
	 * @param params
	 * @throws JSONException
	 */
	@Test(dataProvider = "negativeTest")
	public void negativeTest(SearchParams params) throws JSONException {
		url = url + params.toString();
		System.out.println("\nurl = " + url);
		searchAPIObj.getUrlConnection(url);
		log.info("\nresponse = " + searchAPIObj.getResponse());
		String res = searchAPIObj.getResponse().toString();
		if (searchAPIObj.isInteger(res)) {// response is a response code means
			// we got the response as error
			// message
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
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
		return new Object[][] { { new SearchParams("zootopia", "", null, null) },
				{ new SearchParams("zootopia", "US", null, null) },
				{ new SearchParams("zootopia", "IN", null, null) } };
	}

	@DataProvider(name = "negativeTest")
	public Object[][] dpNegative() {
		return new Object[][] { { new SearchParams("zootopia", "USS", null, null) } };
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
