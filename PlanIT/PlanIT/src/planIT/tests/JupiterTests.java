package planIT.tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import planITutils.utils.TestContext;
import planIT.objects.JupiterObjects;
import planIT.pages.JupiterPages;
import planITutils.utils.BasePage;
import planITutils.utils.TestBase;

public class JupiterTests extends TestBase
{
	JupiterObjects objJupiterobjects;
	JupiterPages objJupiterpages;
	TestContext testcontext;
	BasePage BaseObj;
	public static ExtentTest test;
	public static ExtentReports report;
	
	private Logger log = LogManager.getLogger(JupiterTests.class);
	public static boolean isFirstTest=true;
	public static WebDriver driver;

			 @Test(priority=1)
			  public void mandatoryfieldserror() {
				 
				  log.info("Running First @test to check mandatory fields");
				  
				  test.log(LogStatus.INFO, "Entering First @test to check mandatory fields");
				  Reporter.log("Running First @test to check mandatory fields");
				  String userdetails= TestContext.getStringProperty("userdetails");

				  Reporter.log("Navigating to the Contact Menu");
				  objJupiterpages.navigateto(driver,objJupiterobjects,"Contact");
				  waitForPageToLoad(driver);
				  
				  Reporter.log("Incorrect details to validate the Error messages");
				  objJupiterpages.validatecontact(driver,objJupiterobjects,"Error",userdetails);
				  
				  Reporter.log("Correct details to validate No Error messages displayed");
				  objJupiterpages.validatecontact(driver,objJupiterobjects,"No Error",userdetails);	  
				  
			  }
			  
			  @Test(priority=2)
			  public void successsubmission() {
				  
				  log.info("Running Second @test to check success submission");
				  
				  test.log(LogStatus.INFO, "Entering second @test to check success submission");
				  Reporter.log("Running Second @test to check success submission");
				  String userdetails= TestContext.getStringProperty("userdetails");
				  
				  Reporter.log("Navigating to the Contact Menu");
				  objJupiterpages.navigateto(driver,objJupiterobjects,"Contact");
				  waitForPageToLoad(driver);
				  
				  Reporter.log("Details for the user submitted");
				  objJupiterpages.validatecontact(driver,objJupiterobjects,"submit",userdetails);
			  }
			  
			  @Test(priority=3)
			  public void invaliddatavalidation() {
				  
				  log.info("Running third @test to check invalid fields");
				  
				  test.log(LogStatus.INFO, "Entering third @test to check invalid field details");
				  Reporter.log("Running third @test to check invalid field details");
				  String userdetails= TestContext.getStringProperty("userdetails");
				
				  Reporter.log("Navigating to the Contact Menu");
				  objJupiterpages.navigateto(driver,objJupiterobjects,"Contact");
				  waitForPageToLoad(driver);
				  
				  Reporter.log("Invalid details to validate the Error messages");
				  objJupiterpages.validatecontact(driver,objJupiterobjects,"invalid data",userdetails);	  
			  }
			  
			  @Test(priority=4)
			  public void cartvalidation() {
				  
				  log.info("Running fourth @test to check cart");
				  
				  test.log(LogStatus.INFO, "Entering fourth @test to check invalid field details");
				  Reporter.log("Running fourth @test to check success submission");
				  String itemlist =  TestContext.getStringProperty("items");
				  String quantitylist =  TestContext.getStringProperty("quantity");
				  
				  Reporter.log("Navigating to the shop Menu");
				  objJupiterpages.navigateto(driver,objJupiterobjects,"shop");
				  waitForPageToLoad(driver);
				  
				  Reporter.log("Adding items to the cart");
				  objJupiterpages.addtocart(driver,objJupiterobjects,itemlist,quantitylist);
				 
				  objJupiterpages.navigateto(driver,objJupiterobjects,"cart");
				 
				  waitFor(2);
				  Reporter.log("Validating items in the cart");
				  objJupiterpages.validatecart(driver,objJupiterobjects,itemlist,quantitylist);
			  }
	 @BeforeMethod(alwaysRun=true)
	 public void setup(Method m9,ITestContext testcontext) throws IOException, InterruptedException
			  {
		 		System.out.println("BeforeMethod");
				initializeTestcontext();
				  try
				  {
				  if(isFirstTest)
				  {
						driver=createDriver(TestContext.getStringProperty("baseURL"), TestContext.getStringProperty("browser"),driver);
				  }
				  else
				  {
						String title = driver.getCurrentUrl();
						if(title.contains(TestContext.getStringProperty("baseURL")))
						{
							driver.navigate().refresh();
						}
						else
						{
							driver.navigate().to(TestContext.getStringProperty("baseURL"));
							log.info("Navigated back to home page");
						}
				  }			
				  isFirstTest=false;
				  test = report.startTest(m9.getName().toString().trim());
				  objJupiterobjects = PageFactory.initElements(driver, JupiterObjects.class);
				  objJupiterpages = PageFactory.initElements(driver, JupiterPages.class);
				  BaseObj = PageFactory.initElements(driver, BasePage.class);		    	  
				  }
				  catch(Exception e)
				  {
					  e.printStackTrace();
				  }
			  }
	 @AfterMethod(alwaysRun=true)
	 public void flushReports(ITestResult result9,Method m9) throws IOException, ParseException {
			System.out.println("AffterMethod");
			report.endTest(test);
			report.flush();			
				}
	 @BeforeClass(alwaysRun=true)
	 public void initialize() throws IOException, InterruptedException
	 {
		 		System.out.println("BeforeClass");
	    	   	report = new ExtentReports(System.getProperty("user.dir")+"\\Extentreport\\"+this.getClass().getSimpleName()+".html");
	 }
		
	 @AfterClass(alwaysRun=true)
	 public void tearDown() throws IOException, ParseException
	 {
				waitFor(1);
				System.out.println("AfterClass");
				report.endTest(test);
				report.flush();
				closeDriver(driver);
	 }

			  
}
