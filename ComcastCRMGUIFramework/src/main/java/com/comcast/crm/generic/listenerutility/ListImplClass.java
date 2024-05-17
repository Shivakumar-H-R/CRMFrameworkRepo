package com.comcast.crm.generic.listenerutility;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;

public class ListImplClass implements ITestListener,ISuiteListener {
	
	public ExtentSparkReporter spark;
	public ExtentReports report;
	public  ExtentTest test;
	
	
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("======"+result.getMethod().getMethodName()+"=====START===");
		 test=report.createTest(result.getMethod().getMethodName());
		 //set the test object to access in all the testScript using getTest()
		 UtilityClassObject.setTest(test);
		 test.log(Status.INFO, result.getMethod().getMethodName()+" ====> STARTED <===");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("======"+result.getMethod().getMethodName()+"=====END===");
		 test.log(Status.PASS, result.getMethod().getMethodName()+" ====> COMPLETED <===");
			
	}

	@Override
	public void onTestFailure(ITestResult result) {

		//screenshot for failure element
		String testName=result.getMethod().getMethodName();
		String time=new Date().toString().replace(" ", "_").replace(":", "_");
	//	screenshort for extentreport
		TakesScreenshot sc=(TakesScreenshot)BaseClass.sdriver;
		String fileSrc=sc.getScreenshotAs(OutputType.BASE64);
		test.addScreenCaptureFromBase64String(fileSrc,testName+"_"+time);
		 test.log(Status.FAIL, result.getMethod().getMethodName()+" ====> FAILED <===");
			
	}

	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		String time=new Date().toString().replace(" ", "_").replace(":", "_");
	    spark=new ExtentSparkReporter("./AdvanceReport/report"+time+".html");
		spark.config().setDocumentTitle("CRM Test Suite Result");
		spark.config().setReportName("CRM Report");
		spark.config().setTheme(Theme.DARK);
		
		 report=new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "Windows-10");
		report.setSystemInfo("BROWSER", "chrome-100");
		
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		report.flush();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	}

	
}
