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
 * Automation for limit parameter of SearchAPI
 * 
 * @author Mariya
 *
 */
public class SearchMediaTest {
	private SearchAPIAccess searchAPIObj;
	private LoadProperties propObject;
	private Properties prop;
	private String url;
	private JSONObject jsonResObj;
	private JSONObject expectedResObj;
	private JSONObject tempObj;
	private static Logger log = Logger.getLogger(SearchMediaTest.class);
	
	/**
	 * Validates tests T9, T10, T12 and T13 in limit section
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

		if (params.getMedia() == "") {
			expectedResObj = jsonResObj;
		}
		if (params.getMedia() == "all") {
			JSONAssert.assertEquals(jsonResObj, expectedResObj, true);
		} else if (params.getMedia() == "podcast") { // comparing previous
														// response with current
														// response
			JSONAssert.assertNotEquals(jsonResObj, tempObj, false);
		}
		tempObj = jsonResObj; // saving the previous response
	}

	/**
	 * Validate test T11 in limit section
	 * 
	 * @param params
	 * @throws JSONException
	 */
	@Test(dataProvider = "negativeTest")
	public void negativeTest(SearchParams params) throws JSONException {
		url = url + params.toString();
		log.info("\nurl = " + url);
		searchAPIObj.getUrlConnection(url);
		log.info("response = " + searchAPIObj.getResponse());
		Assert.assertEquals(Integer.parseInt(searchAPIObj.getResponse().toString()), 400);

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
		return new Object[][] { { new SearchParams("zootopia", null, "movie", null) },
				{ new SearchParams("member", null, "podcast", null) }, { new SearchParams("zootopia", null, "", null) },
				{ new SearchParams("zootopia", null, "all", null) } };
	}

	@DataProvider(name = "negativeTest")
	public Object[][] dpNegative() {
		return new Object[][] { { new SearchParams("zootopia", null, "mycam", null) } };
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
