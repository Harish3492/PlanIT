/**
 * This class forms the base class for all the tests
 * It creates driver, initializes the other framework libraries.
 * It also has configuration methods to run before starting the test
 */

package planITutils.utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners({planITutils.utils.TestBaseListener.class})
public class TestBase extends BasePage{
	
	//Declare ThreadLocal Driver (ThreadLocalMap) for ThreadSafe Tests
	public static WebDriver driver;
	public static TestContext context;	
	public static Logger log;
	
	public static ExtentTest test;
	public static ExtentReports report;
	
	public static DesiredCapabilities capabilities = null;
	static int first=0;
	
	public WebDriver getDriver(){
		context = new TestContext(log, System.getProperty("user.dir") + "\\TestData\\config.properties");
        return driver;
	}
	
	public Logger getLogger(){
		return log;
	}
	
	public TestContext getContext(){
		return context;
	}
	
	/**
	 * Creates the driver object
	 * @param URL
	 * @param browser
	 */
	
	public void createWebDriver(String URL){
		capabilities=new DesiredCapabilities();
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\lib\\drivers\\chromedriver.exe");
		capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		driver = new ChromeDriver(capabilities);
		getDriver().get(URL);
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public WebDriver createDriver(String URL, String browser,WebDriver driver) 
		{

		switch (browser) 
		{
		case "chrome":
			    try
				{				
				createWebDriver(URL);
				}
				catch (Exception e) 
			   {
				if(null==driver){
				e.printStackTrace();
			   }
			break;
			   }
		}
		return getDriver();
	}
	
	/**
	 * Creates the object to read the config.properties file
	 */
	
	public void initializeTestcontext(){
		context = new TestContext(log, System.getProperty("user.dir") + "\\TestData\\config.properties");
	}
	
	
	/**
	 * Closes all the browser instances
	 * @param driver2 
	 */
	
	public void closeDriver(WebDriver driver2){		
		System.out.println("close driver");		
		driver2.close();
		
	}
	
	/**
	 * Archives the previous run results
	 */
	public void archiveTestOutput(){
		TestResultsArchiver testarchive = new TestResultsArchiver(log);
		testarchive.copyDir();
	}

	/**
	 * Executes before the test suite is started
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	
	@BeforeSuite(alwaysRun=true)
	public void onStart() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	   {	
		Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
		waitFor(1);
		System.out.println("BeforeSuite");
		archiveTestOutput();
		initializeTestcontext();
		File files = new File(System.getProperty("user.dir") + "\\Extentreport");
		if (files.exists()) {
			FileUtils.deleteDirectory(files);
		}
		}

	@AfterSuite(alwaysRun=true)
	public void flushReports1() throws IOException, ParseException{
		System.out.println("AfterSuite");	

		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	 public String getResultStatusName(int status) {
	    	String resultName = null;
	    	if(status==1)
	    		resultName = "Pass";
	    	if(status==2)
	    		resultName = "Fail";
	    	if(status==3)
	    		resultName = "Fail";
			return resultName;
	    }
	
	
}
