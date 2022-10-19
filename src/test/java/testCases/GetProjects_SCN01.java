package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testBase.TestBase;
import utilities.ReadProperties;

public class GetProjects_SCN01 extends TestBase {

	public static RequestSpecification httpRequest;
	public static Response response;

	@BeforeClass
	public void get_project_details() throws InterruptedException {
		
		logger.info("*** SCN01 started *******");

		logger.info("*** started getting project details***");

		String token = ReadProperties.get_property_value_by_key("BearerToken");
		httpRequest = RestAssured.given().auth().oauth2(token);
		response = httpRequest.request(Method.GET, "/projects");
		Thread.sleep(4000);

	}


	@Test
	public void check_header_content_type() {
		String content_type=response.header("Content-Type");
		System.out.println("Header - Content Type="+ content_type);
		Assert.assertEquals(content_type, "application/json");
	}

	@Test
	public void check_response_body() {
		String responseBody = response.body().asString();
		System.out.println("Response Body="+responseBody);
		Assert.assertTrue(responseBody!=null);
	}

	@Test
	public void check_status_code() {
		int status_code = response.getStatusCode();
		System.out.println("Status code="+status_code);
		Assert.assertEquals(status_code, 200);;
	}

	@Test
	public void check_status_line() {

		String status_line = response.getStatusLine();
		System.out.println("Status line="+ status_line);
		Assert.assertEquals(status_line, "HTTP/1.1 200 OK");;

	}

	@Test
	public void check_response_time() {
		
		long response_time = response.time();
		System.out.println("Response time="+response_time );
		Assert.assertTrue(response_time<5000);
	}
	
	@AfterClass
	public void tear_down() {
		logger.info("*** SCN01 completed *******");
	}
}
