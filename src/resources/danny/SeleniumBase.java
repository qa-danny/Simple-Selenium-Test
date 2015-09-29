package resources.danny;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class SeleniumBase {

	protected static WebDriver driver;
	protected static String baseUrl;
	
	/**
	 * BeforeClass creates the driver and has it set for the entire duration
	 *  of the test class.
	 * @throws Exception
	 */
	
	@BeforeClass
	public static void setUp() throws Exception {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(false);
		
		baseUrl = "http://www.6pm.com";
		driver = new FirefoxDriver(profile);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	/**
	 * AfterClass tears down the driver, ending the tests. Hence the 
	 *  name, tearDown.
	 * @throws Exception
	 */
	@AfterClass
    public static void tearDown() throws Exception {
           driver.quit();
    }

    public boolean isElementPresent(By by) {
           try {
                  driver.findElement(by);
                  return true;
           } catch (NoSuchElementException e) {
                  return false;
           }
    }
	
	public static void jQueryWait(WebDriver driver) {
		int i = 0;
		try {
			do {
		        Thread.sleep(300);
		        i++;
		        if(i == 50){
	                driver.navigate().refresh();
	            }
		    }
		    while (!((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete") && i < 100);
		} catch (InterruptedException e) {}
	}
    
}
