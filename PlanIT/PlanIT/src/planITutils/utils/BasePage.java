package planITutils.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import planITutils.utils.TestContext;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class BasePage{

	private Logger log;
	private TestContext context;

	public static WebDriver driver;
	public static String className;
	public static String methodName;
	public static Multimap<String, String> extentList = ArrayListMultimap
			.create();
	public static boolean isFailure = false;

	public static ArrayList<String> classList = new ArrayList<String>();
	public static HashMap<String, Integer> tcCt = new HashMap<String, Integer>();

	public static String startTime = "";
	public static String endTime = "";
	public static HashMap<String, Integer> sceCtMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> extMap = new HashMap<String, Integer>();

	
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	protected void setContext(TestContext context) {
		this.context = context;
	}

	protected void setLogger(Logger log) {
		this.log = log;
	}

	public WebDriver getDriver() {
		return driver;
	}

	protected TestContext getContext() {
		return context;
	}

	protected Logger getLogger() {
		return log;
	}
	
	/** For Modifying the Xpath values
	 	*/
	public WebElement prepareWebElementWithDynamicXpathWithInt(
			String xpathValue, int i, WebDriver driver) {
		return driver.findElement(By.xpath(xpathValue
				.replace("dynamic", "" + i)));
	}
	
	public WebElement prepareWebElementWithDynamicXpathWithIntInc(
			String xpathValue, int i, WebDriver driver) {
		i=i+1;
		return driver.findElement(By.xpath(xpathValue
				.replace("dynamic", "" + i)));
	}
	
	public WebElement prepareWebElementWithDynamicXpathWithIntInc2(
			String xpathValue, int i, int j, WebDriver driver) {
		i=i+1;
		return driver.findElement(By.xpath(xpathValue
				.replace("dynamic", "" + i).replace("quantity", "" + j)));
	}
	
	public List<WebElement> prepareListWebElementWithDynamicXpathWithString(
			String xpathValue, String s, WebDriver driver) {
		return driver.findElements(By.xpath(xpathValue.replace("dynamic", s)));
	}
	
	
	public int generateRandomNumber() {
		Random random = new Random();
		int randomNumber = random.nextInt();
		return randomNumber;
	}
	
	/**
	 * Waits for a page to be loaded
	 */
	
	public void waitForPageToLoad(WebDriver driver) {
		new WebDriverWait(driver, 60).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				return (((JavascriptExecutor) arg0)
						.executeScript("return document.readyState")
						.equals("complete"));
			}
		});
	}

	/**
	 * Waits for a specified element to be visible
	 * 
	 * @param element
	 */
	
	public void waitForElement(WebElement element, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	/**
	 * Waits for a specified locator to be Cllickable
	 * 
	 * @param lnkReports
	 */
	
	public void waitForClickOfElement(WebElement lnkReports, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.elementToBeClickable(lnkReports));
		} catch (Exception e) {
			try {
				throw new CustomException(e, getLogger(), lnkReports.toString());
			} catch (CustomException e1) {
				getLogger().error(
						"TimeOut Exception occurred while waiting for the element: "
								+ lnkReports.toString());
			}
		}
	}

	/**
	 * Waits for a specified page to be displayed
	 * 
	 * @param page
	 */
	
	public void waitForPage(String page, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.titleContains(page));
		} catch (Exception e) {
			try {
				throw new CustomException(e, getLogger(), page);
			} catch (CustomException e1) {
				getLogger().error(
						"TimeOut Exception occurred while waiting for the page: "
								+ page);
			}
		}
	}

	/**
	 * Waits for a specified number of seconds
	 * 
	 * @param seconds
	 */
	
	public void waitFor(double seconds) {
		try {
			Thread.sleep((long) seconds * 1000);
		} catch (InterruptedException e) {
			log.error("Interruption occured during waiting", e);
		}
	}

	
	// ================= Common Weblement methods============================
	
	public String getElementLocator(WebElement element) {
		String name = element.toString();
		int index = name.lastIndexOf("->");
		return name.substring(index + 2, (name.length() - 1));
	}

	
	/**
	 * Gets the value of a html attribute for an element
	 * 
	 * @param element
	 *            - WebElement to be clicked
	 * @return String - value of a html attribute
	 */
	
	public String getValueAttribute(WebElement element) {
		String elementname = getElementLocator(element);
		String value = null;
		try {
			value = element.getAttribute("value");
			getLogger().info(
					"Value attribute: " + value + " from the element: "
							+ elementname);
		} catch (Exception e) {
			try {
				throw new CustomException(e, getLogger(), elementname);
			} catch (CustomException e1) {
				getLogger().error(
						"Error in retrieving the value attribute from the element: "
								+ elementname);
			}
		}
		return value;
	}

	/**
	 * Performs vertical scrolling in the browser/window
	 */
	
	public static void verticalScrollLong(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,900)");
	}

	public static void verticalScroll(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400)");
	}

	public static void scrollUp(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("scrollBy(0, -1000);");

	}


	/**
	 * Clicks on an Element(button, link)
	 * 
	 * @param element
	 *            - WebElement to be clicked
	 */
	
	public void click(WebElement element) {
		String elementname = getElementLocator(element);
		try {
			element.click();
		} catch (Exception e) {
			try {
				throw new CustomException(e, getLogger(), elementname);
			} catch (CustomException e1) {
				getLogger().error(
						"Error in clicking the element: " + elementname);
			}
		}
	}
    
	/* Get values from table set
	 * Not used for POC
	 */
	
	public List<List<Object>> getValuesFromTable(List<WebElement> objectTable) {
		List<List<Object>> tableValues = new ArrayList<List<Object>>();
		List<Object> row = null;
		List<WebElement> tr_collection = objectTable;
		int row_Count = objectTable.size();
		System.out.println("Number Of Rows = " + row_Count);
		int row_num, col_num;
		row_num = 1;
		for (WebElement trElement : tr_collection) {
			List<WebElement> td_collection = trElement.findElements(By
					.xpath("td"));
			System.out.println("NUMBER OF COLUMNS=" + td_collection.size());
			col_num = 0;
			row = new ArrayList<>(td_collection.size());
			for (WebElement tdElement : td_collection) {
				row.add(tdElement.getText());
				col_num++;
			}

			tableValues.add(row);

			row_num++;
		}

		return tableValues;
	}

	public List<List<Object>> getValuesFromTables(List<WebElement> objectTable) {
		List<List<Object>> tableValues = new ArrayList<List<Object>>();
		List<Object> row = null;
		List<WebElement> tr_collection = objectTable;
		int row_Count = objectTable.size();
		System.out.println("Number Of Rows = " + row_Count);
		int row_num, col_num;
		row_num = 1;
		for (WebElement trElement : tr_collection) {
			List<WebElement> td_collection = trElement.findElements(By
					.xpath("td"));
			System.out.println("NUMBER OF COLUMNS=" + td_collection.size());
			col_num = 0;
			row = new ArrayList<>(td_collection.size());
			for (WebElement tdElement : td_collection) {
				if (tdElement.getText().isEmpty()) {
				} else {
					row.add(tdElement.getText());
					col_num++;
				}
			}

			tableValues.add(row);
			row_num++;
		}

		for (int i = tableValues.size() - 1; i >= 0; i--) {
			if (tableValues.get(i).isEmpty()) {
				tableValues.remove(i);
			}
		}
		return tableValues;
	}

	public List<List<Object>> getValuesFromTableST(List<WebElement> objectTable) {
		List<List<Object>> tableValues = new ArrayList<List<Object>>();
		List<Object> row = null;
		List<WebElement> tr_collection = objectTable;
		int row_Count = objectTable.size();
		System.out.println("Number Of Rows = " + row_Count);
		int row_num, col_num;
		row_num = 1;
		for (WebElement trElement : tr_collection) {
			List<WebElement> td_collection = trElement.findElements(By
					.xpath("td"));
			System.out.println("NUMBER OF COLUMNS=" + td_collection.size());
			col_num = 0;
			int count = td_collection.size();
			row = new ArrayList<>(td_collection.size());
			if (count == 1) {
			} else {
				for (WebElement tdElement : td_collection) {
					count++;
					// System.out.println("row # "+row_num+", col # "+col_num+
					// "text="+tdElement.getText());
					row.add(tdElement.getText());
					col_num++;
				}
				tableValues.add(row);
			}
			row_num++;
		}

		return tableValues;
	}

	/* Wait for JS&Jquery to be loaded
	 * As part of Base framework
	 */
	
	public boolean waitForJSandJQueryToLoad(WebDriver driver1) {

		WebDriverWait wait = new WebDriverWait(driver1, 150);

		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver1) {
				try {
					setDriver(driver1);
					return ((Long) ((JavascriptExecutor) driver1)
							.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					// no jQuery present
					return true;
				}
			}
		};
		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver1) {
				setDriver(driver1);
				return ((JavascriptExecutor) driver1)
						.executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		waitFor(1);
		waitForPageToLoad(driver1);waitFor(1);
		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

}
