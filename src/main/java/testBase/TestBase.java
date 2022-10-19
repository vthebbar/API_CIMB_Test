package testBase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;



public class TestBase {
	
		
	//Logging
	public Logger logger;
	
	@BeforeClass
	public void set_up() {
		
		logger= Logger.getLogger("todoistAPI");
		PropertyConfigurator.configure(System.getProperty("user.dir")+"/Config/log4j.properties");
		logger.setLevel(Level.DEBUG);
		
		RestAssured.baseURI="https://api.todoist.com/rest/v1/";
	}
}
