/**
 * This class categorizes the exception that occurs during execution 
 * and updates the reports and log files in readable format
 * 
 */

package planITutils.utils;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriverException;


public class CustomException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public CustomException(Throwable e, Logger log, String elementname){
		exceptionHandler(e,log,elementname);
		
	}
	
	public void exceptionHandler(Throwable e, Logger log, String elementname){
		if(e instanceof NoSuchElementException){
			log.error("The element: " 
					+ elementname
					+ " could not be found", e.getLocalizedMessage());
			throw new NoSuchElementException("The element: " 
					+ elementname
					+ " could not be found");
		}else if(e instanceof InvalidSelectorException){
			log.error("The locator: " 
					+ elementname
					+ " is invalid", e.getLocalizedMessage());
			throw new InvalidSelectorException("The locator: " 
					+ elementname
					+ " is invalid");
		}else if(e instanceof StaleElementReferenceException){
			log.error("The element: " 
					+ elementname
					+ " is no longer attached to the DOM/The page is refreshed", e.getLocalizedMessage());
			throw new InvalidSelectorException("The element: " 
					+ elementname
					+ " is no longer attached to the DOM/The page is refreshed");
		}else if(e instanceof NullPointerException){
			log.error("The element: " 
					+ elementname
					+ " is null", e.getLocalizedMessage());
			throw new NullPointerException("The element: " 
					+ elementname
					+ " is null");
		}else if(e instanceof WebDriverException){
			log.error("WebDriver instance is no longer valid"
					+ e.getLocalizedMessage());
			throw new WebDriverException();
		}else if(e instanceof TimeoutException){
			log.error("TimeOutException occurred in webdriverwait"
					+ e.getLocalizedMessage());
			throw new TimeoutException();
		}else if(e instanceof UnsupportedCommandException){
			log.error("Command used by driver is not supported"
					+ e.getLocalizedMessage());
			throw new UnsupportedCommandException();
		}
	}
	
}
