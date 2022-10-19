package utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class MyListener implements ITestListener {

	static ExtentReports report;
	    ExtentTest test;
	
	public void onTestStart(ITestResult result) {
				//before each test case
			
	
		
				test = report.createTest(result.getMethod().getMethodName());
				ExtentFactory.get_instance().set_extent(test);
	}

	public void onTestSuccess(ITestResult result) {
		ExtentFactory.get_instance().get_extent().log(Status.PASS, "Test Case: "+result.getMethod().getMethodName()+ " is Passed.");
		ExtentFactory.get_instance().remove_extent_objet();
	}

	public void onTestFailure(ITestResult result) {
		ExtentFactory.get_instance().get_extent().log(Status.FAIL, "Test Case: "+result.getMethod().getMethodName()+ " is Failed.");
		ExtentFactory.get_instance().get_extent().log(Status.FAIL, result.getThrowable());
		
		
	}

	public void onTestSkipped(ITestResult result) {
		ExtentFactory.get_instance().get_extent().log(Status.SKIP, "Test Case: "+result.getMethod().getMethodName()+ " is skipped.");
		ExtentFactory.get_instance().remove_extent_objet();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}

	public void onTestFailedWithTimeout(ITestResult result) {
			}

	public void onStart(ITestContext context) {
		try {
			 report = ExtentSetup.setup_extent_report();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onFinish(ITestContext context) {
			//remove extent object
				report.flush();
	}


	
}
