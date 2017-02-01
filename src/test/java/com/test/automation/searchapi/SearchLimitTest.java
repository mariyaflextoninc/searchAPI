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
public class SearchLimitTest {

	private SearchAPIAccess searchAPIObj;
	private LoadProperties propObject;
	private Properties prop;
	private String url;
	private JSONObject jsonResObj;
	private static Logger log = Logger.getLogger(SearchLimitTest.class);
	
	/**
	 * Validates tests - T14,T15 and T16 in limit section of test plan
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
		if (params.getLimit() == "") {
			log.info("response = " + searchAPIObj.getResponse());
			Assert.assertEquals(Integer.parseInt((String) jsonResObj.getString("resultCount")) <= 50 ? true : false,
					true);
		} else if (params.getLimit() != null) {
			Assert.assertEquals(Integer.parseInt((String) jsonResObj.getString("resultCount")) <= Integer
					.parseInt(params.getLimit()) ? true : false, true);
		} else {
			Assert.assertEquals(Integer.parseInt((String) jsonResObj.getString("resultCount")) > 0 ? true : false,
					true);
		}
	}

	/**
	 * Validates tests - T17 and T18 in limit section of test plan
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
		return new Object[][] { { new SearchParams("member", null, "podcast", "1") },
				{ new SearchParams("member", null, "podcast", "") },
				{ new SearchParams("member", null, "podcast", null) } };
	}

	@DataProvider(name = "negativeTest")
	public Object[][] dpNegative() {
		return new Object[][] { { new SearchParams("member", null, "podcast", "abc") },
				{ new SearchParams("member", null, "podcast", "0") },
				{ new SearchParams("member", null, "podcast", "2000") } };
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