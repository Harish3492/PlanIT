package planIT.pages;

import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import planIT.objects.JupiterObjects;
import planIT.tests.JupiterTests;
import planITutils.utils.BasePage;

public class JupiterPages extends BasePage{
	ExtentTest test=JupiterTests.test;
	private Logger log = LogManager.getLogger(JupiterTests.class);
	public void navigateto(WebDriver driver,JupiterObjects objJupiterobjects, String type)
	{
		try
		{
			switch (type)
			{
			case "Contact":	
			    objJupiterobjects.contact.click();
			    break;
			case "shop":
				objJupiterobjects.shop.click();
			    break;
			case "cart":
			    objJupiterobjects.cart.click();
			    break;
			}
			test.log(LogStatus.PASS, "Navigating to the Menu " +type+ " is as expected");
			log.info("Option clicked -->" +type);
		}
		catch(Exception E)
		{
			test.log(LogStatus.FAIL, "Navigating to the Menu " +type+ " is not as expected");
			log.error("Issue with the Menu click option" +type);
			System.out.println("Unable to click on menu -" +type);
			Assert.assertTrue(false);
		}
	}
	
	public void validatecontact(WebDriver driver,JupiterObjects objJupiterobjects, String type,String details)
	{
		waitFor(2);
		String[] userdetails = details.split(",");
		try
		{
		switch (type)
		{
		case "Error":	
				BasePage.verticalScrollLong(driver);
		    	objJupiterobjects.submit.click();
		    	if(objJupiterobjects.forenameerr.isDisplayed() && objJupiterobjects.emailerr.isDisplayed() && objJupiterobjects.messageerr.isDisplayed())
		    	{
		    		System.out.println("Error Displayed when details not entered");
		    		test.log(LogStatus.PASS, "Error Displayed as expected when details not entered");
		    	}
		    break;
		    
		case "No Error":
			BasePage.scrollUp(driver);
			try
			{				
			objJupiterobjects.forename.sendKeys(userdetails[0]);
			if(objJupiterobjects.forenameerr.isDisplayed())
		    {
				System.out.println("Error for Forename displayed even after proper values sent");
				test.log(LogStatus.FAIL, "Error for Forename displayed even after proper values sent");
				Assert.assertTrue(false);
		    }
			}
			catch (Exception E) 
			{
			test.log(LogStatus.PASS, "Error for forename not displayed as expected");
			System.out.println("Error for forename not displayed as expected");	
			}
				try
				{
				objJupiterobjects.email.sendKeys(userdetails[1]);
				if(objJupiterobjects.emailerr.isDisplayed())
			    {
					System.out.println("Error for email displayed even after proper values sent");
					test.log(LogStatus.FAIL, "Error for email displayed even after proper values sent");
					Assert.assertTrue(false);
			    }
				}
				catch(Exception E)
				{
					test.log(LogStatus.PASS, "Error for mail not displayed as expected");
					System.out.println("Error for mail not displayed as expected");
				}
						try
						{
						objJupiterobjects.message.sendKeys(userdetails[2]);
						if(objJupiterobjects.messageerr.isDisplayed())
					    {
							System.out.println("Error for message displayed even after proper values sent");
							test.log(LogStatus.FAIL, "Error for message displayed even after proper values sent");
							Assert.assertTrue(false);
					    }
						}	
						catch( Exception E)
						{
							test.log(LogStatus.PASS, "Error for message not displayed as expected");
							System.out.println("Error for message not displayed as expected");
						}			
		    break;
		    
		case "invalid data":			
				objJupiterobjects.email.sendKeys("123456");
				if(objJupiterobjects.emailerr.isDisplayed())
		    	{
					test.log(LogStatus.PASS, "Error Displayed when details are invalid");
					System.out.println("Error Displayed when details are invalid");
		    	}
		    break;
		    
		case "submit":				
				objJupiterobjects.forename.sendKeys(userdetails[0]);
				objJupiterobjects.email.sendKeys(userdetails[1]);
				objJupiterobjects.message.sendKeys(userdetails[2]);
				
				BasePage.verticalScrollLong(driver);
				objJupiterobjects.submit.click();
				
				log.info("Submit clicked with correct user details");
				waitForClickOfElement(objJupiterobjects.back, driver);
				
				if(objJupiterobjects.success.isDisplayed())
				{
					test.log(LogStatus.PASS, "Details saved successfully as expected");
					System.out.println("Details saved successfully as expected");	
				}
		    break;
		}
		}
		catch(Exception E)
		{
			test.log(LogStatus.FAIL, "Issue with the Error message validations for " +type+ " causing exception");
			log.error("Issue with the Error message validations for" +type);
			System.out.println("Error when validating " +type);
			Assert.assertTrue(false);
		}
	}
	
	public void addtocart(WebDriver driver,JupiterObjects objJupiterobjects,String itemlist,String quantitylist)
	{
		String[] temparray = itemlist.split(",");
		String[] temparray1 = quantitylist.split(",");
		try
		{
			for(int i=0;i<temparray.length;i++)
			{    
				int size = objJupiterobjects.productlist.size();
				for(int j=1;j<=size;j++)
				{
					WebElement element = prepareWebElementWithDynamicXpathWithInt(objJupiterobjects.productlistClick, j,driver);
					String productname = temparray[i];
					if(element.getText().equals(productname))
					{
						WebElement element1 = prepareWebElementWithDynamicXpathWithInt(objJupiterobjects.buy, j,driver);
						int quantitytoorder = Integer.parseInt(temparray1[i]);
						for(int k=0;k<quantitytoorder;k++)
						{
							element1.click();							
							waitFor(5);
						}
						test.log(LogStatus.PASS, "Article " +productname+ " added to the cart with Quantity " +quantitytoorder+ " as expected");
						log.info("Article " +productname+ " added to the cart with Quantity " +quantitytoorder);
					}				
				}				
			}
		}
		catch(Exception E)
		{
			test.log(LogStatus.FAIL, "Error when adding article to cart");
			log.error("Error when adding article to cart");
			System.out.println("Error when adding article to cart");
			Assert.assertTrue(false);
		}
	}
	
	public void validatecart(WebDriver driver,JupiterObjects objJupiterobjects,String itemlist,String quantitylist)
	{
		String[] temparray = itemlist.split(",");
		String[] temparray1 = quantitylist.split(",");
		int size = objJupiterobjects.cartlist.size();
		try
		{
			for(int i=0;i<size;i++)
			{
				WebElement element = prepareWebElementWithDynamicXpathWithIntInc(objJupiterobjects.cartlistitems, i,driver);
				
				int j = Integer.parseInt(temparray1[i]);
				WebElement element1 = prepareWebElementWithDynamicXpathWithIntInc2(objJupiterobjects.cartlistquantity, i,j,driver);
				log.info("Cart validation is in progress");
				if(element.getText().equals(temparray[i]) && element1.isDisplayed())
				{
					test.log(LogStatus.PASS, "Details for " +temparray[i]+ " are as expected");
					System.out.println("Details for " +temparray[i]+ " are as expected");
					log.info("Cart validation is completed" );
				}
				else
				{
					test.log(LogStatus.FAIL, "Details are not as expected");
					log.error("Cart validation is not as expected" );
					System.out.println("Details are not as expected");
					Assert.assertTrue(false);
				}
			}
		}
		catch(Exception E)
		{
			test.log(LogStatus.FAIL, "Details are not as expected");
			log.error("Error when validating article in cart");
			System.out.println("Error when validating the cart");
			Assert.assertTrue(false);
		}
	}
	
}
