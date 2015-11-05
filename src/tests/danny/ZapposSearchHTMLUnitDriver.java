package tests.danny;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * This test will search on the Zappos home page, click a product
 *  that has been returned on the search results page, open that
 *  product page, select a size, add item to cart, then remove
 *  the item from the cart.
 *  
 *  HTMLUnit does not allow javascript to be enabled for tests.
 *  
 */

public class ZapposSearchHTMLUnitDriver {

	protected static WebDriver driver;
	protected static String zappUrl = "http://www.zappos.com";

	@Before
	public void setUp() throws Exception {

		driver = new HtmlUnitDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	//Average Time: 3.783 s, 3.882 s, 7.027 s, 3.955
	
	@Test
	public void ZapposSearch_Test_HTMLUnit() throws InterruptedException, IOException {
		//Variables:
		String emptyCart = "Your shopping cart is empty";
		String size = "1 Little Kid";

		driver.get(zappUrl);

		driver.findElement(By.xpath("/html/body/div[3]/div[1]/form/fieldset/input[1]")).clear();
		driver.findElement(By.xpath("/html/body/div[3]/div[1]/form/fieldset/input[1]")).sendKeys("Ugg Footwear");
		driver.findElement(By.xpath("/html/body/div[3]/div[1]/form/fieldset/button")).click();

		driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[1]/a[1]/img")).click();
		String productName = driver.findElement(By.className("ProductName")).getText();

		Select sizeSelector = new Select(driver.findElement(By.id("d3")));

		sizeSelector.selectByVisibleText(size);
		driver.findElement(By.id("addToCart")).click();
		String cartProduct = driver.findElement(By.className("name")).getText();

		assertTrue(cartProduct.contains(productName));
		assertTrue(driver.findElement(By.xpath("/html/body/div[4]/div[1]/table/tbody/tr/td[1]/ul/li[3]"))
				.getText().equalsIgnoreCase("size: "+ size));

		driver.findElement(By.xpath("//html/body/div[4]/div[1]/table/tbody/tr/td[3]/p/a[1]")).click();

		driver.navigate().refresh();
		
		assertTrue(driver.findElement(By.xpath("//html/body/div[4]/div[1]/div[1]/div[1]/h1"))
				.getText().equals(emptyCart));

	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}
