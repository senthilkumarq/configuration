package com.BedRock;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;


public class Init {
	    public static ExtentReports reports;
	    public static ExtentSparkReporter htmlReporter;
	    public static ExtentTest test;
    	public static WebDriver driver;
	    static int i=0;
	@BeforeMethod
	public void precondition1(ITestResult scenario) throws IOException
	{    
		if(i==0) {
		String str=System.getProperty("user.dir") + "//test-output//Extentreport1.html";
		reports = new ExtentReports();
		htmlReporter= new ExtentSparkReporter(str);
		htmlReporter.loadXMLConfig(new File(".\\extent-config.xml"));
	    reports.setSystemInfo("Machine", "Ajeet-772G");
        reports.setSystemInfo("Env", "DevOps");
        reports.setSystemInfo("Build", "Integration");
        reports.setSystemInfo("Browser", "IE");
        reports.attachReporter(htmlReporter);
        i++;
	  }   
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\senthilkumar\\Downloads\\geckodriver-v0.32.2-win64\\geckodriver.exe");
		 driver=new FirefoxDriver();
		  driver.get("file:///C:/Users/senthilkumar/Desktop/demo.html");
	  
	driver.manage().window().maximize();
	//LocalTime s1= LocalTime.now();
	test = reports.createTest(scenario.getMethod().getMethodName());
	System.out.println(scenario.getMethod().getMethodName());
	}
	@AfterMethod
	public void post(ITestResult result)
	{
		  if (!result.isSuccess()) {
			  
	String screenShot = null;
	
	try {
		screenShot = Init.captureScreen(driver);
	} catch (Exception e) {

	}
	System.out.println(result.isSuccess());
	 test.log(Status.FAIL,MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
		reports.flush();
        }
		  else {
			  String screenShot = null;
				
				try {
					screenShot = Init.captureScreen(driver);
				} catch (Exception e) {

				}
				System.out.println("else= "+ result.isSuccess());
				 test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
					reports.flush();
		  }
        
        driver.close();

	}
	
	public static String captureScreen(WebDriver driver1) throws IOException
	{
		 TakesScreenshot scrShot =((TakesScreenshot)driver1);
		 File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		 String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+"senthil.png";
		 File finalDestination = new File(destination);
		 Files.copy(SrcFile, finalDestination);	      
		 return destination;

	}
	
}



