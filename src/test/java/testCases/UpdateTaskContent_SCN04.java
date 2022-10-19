package testCases;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testBase.TestBase;
import utilities.ExcelOperations;
import utilities.ReadProperties;

public class UpdateTaskContent_SCN04 extends TestBase {

	public static RequestSpecification httpRequest;
	public static Response response;

	ExcelOperations excel = new ExcelOperations("UpdateTaskContent");
	HashMap<String,String> testData;


	@BeforeClass
	public void setup() {
		logger.info("*** SCN04 Started *******");

	}
	
	@Test(dataProvider="projectData", priority=1)
	public void updateTaskContent_verify_response_not_null(Object obj1) throws InterruptedException {


		testData = (HashMap<String, String>) obj1;

		httpRequest = RestAssured.given(); //.auth().oauth2(ReadProperties.get_property_value_by_key("BearerToken"));

		//header
		httpRequest.header("Content-Type", ReadProperties.get_property_value_by_key("Content-Type"));
		httpRequest.header("X-Request-Id", ReadProperties.get_property_value_by_key("X-Request-Id"));
		httpRequest.header("Authorization", ReadProperties.get_property_value_by_key("Authorization"));

		//payload
		JSONObject requestParams = new JSONObject();
		requestParams.put("content", testData.get("due_string"));

		//attach to body
		httpRequest.body(requestParams.toJSONString());

		String path = "/tasks/"+ testData.get("project_id");
		//Response
		response = httpRequest.request(Method.POST, path);
		Thread.sleep(4000);
		
		String responseBody = response.body().toString();
		System.out.println("Response body="+ responseBody);
		Assert.assertTrue(responseBody!=null, "No data found in response body");

	}

	@Test
	public void verify_status_code() {
		int status_code = response.getStatusCode();
		System.out.println("Status body="+ status_code);

		//validation
		Assert.assertEquals(status_code, 204,"Status code is not as Expected");
	}




	@Test
	public void verfy_header_connection() {
		String connection = response.header("Connection");
		Assert.assertEquals(connection, "keep-alive", "Header: value for Connection not as expected");
	}


	@Test
	public void verfy_header_content_type() {
		String content_type = response.header("Content-Type");
		Assert.assertEquals(content_type, "text/plain; charset=utf-8", "Header: value for Content-Type not as expected");
	}

	@AfterClass
	public void tear_down() {
		logger.info("*** SCN04 Completed *******");

	}


	//Test data provider
	@DataProvider(name="projectData")
	public Object[][] get_test_data() throws Exception {

		Object[][] obj = new Object[excel.getRowCount()][1];
		for (int i = 1; i <= excel.getRowCount(); i++) {
			HashMap<String, String> testData = excel.getTestDataInMap(i);
			obj[i-1][0] = testData;
		}
		return obj;

	}


}
