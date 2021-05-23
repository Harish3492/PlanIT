package planIT.objects;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JupiterObjects {
	
		@FindBy(xpath="//*[@id='nav-contact']/a")
		public WebElement contact;
		
		@FindBy(xpath ="//*[@id='forename']")
		public WebElement forename;
		
		@FindBy(xpath ="//*[@id='email']")
		public WebElement email;
		
		@FindBy(xpath ="//*[@id='message']")
		public WebElement message;
		
		@FindBy(xpath ="//*[@id='forename-err']")
		public WebElement forenameerr;
		
		@FindBy(xpath ="//*[@id='email-err']")
		public WebElement emailerr;
		
		@FindBy(xpath ="//*[@id='message-err']")
		public WebElement messageerr;
		
		@FindBy(xpath ="//*[@id='nav-shop']/a")
		public WebElement shop;
		
		@FindBy(xpath ="//*[@id='nav-cart']/a")
		public WebElement cart;
		
		@FindBy(xpath ="//*[@class='btn-contact btn btn-primary']")
		public WebElement submit;
		
		@FindBy(xpath ="//*[@class='products ng-scope']/ul/li")
		public List<WebElement> productlist;
		
		@FindBy(xpath ="//*[@class='table table-striped cart-items']/tbody/tr")
		public List<WebElement> cartlist;
		
		public final String buy = "//*[@class='products ng-scope']/ul/li[dynamic]//*[@class='btn btn-success']";
		
		public final String productlistClick = "//*[@class='products ng-scope']/ul/li[dynamic]//*[@class='product-title ng-binding']";
	
		public final String cartlistitems = "//*[@class='table table-striped cart-items']/tbody/tr[dynamic]/td[1]";
		
		public final String cartlistquantity = "//*[@class='table table-striped cart-items']/tbody/tr[dynamic]/td[3]//*[@value='quantity']";
		
		@FindBy(xpath ="//*[@class='alert alert-success']")
		public WebElement success;
		
		@FindBy(xpath ="//*[@class='btn']")
		public WebElement back;
	}
