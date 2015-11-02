package tests.danny;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.Select;

import resources.danny.SeleniumBase;

/**
 * This test will search on the Zappos home page, click a product
 *  that has been returned on the search results page, open that
 *  product page, select a size, add item to cart, then remove
 *  the item from the cart.
 */

//FixMethodOrder will make the @Test methods run in Name Ascending order.
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZapposSearch extends SeleniumBase {
	
	//Static variable, data persists throughout test class.
	private static String productName;
	private String emptyCart = "Your shopping cart is empty";
	
	@Test
	public void A_ZapposSearch_Test() throws InterruptedException, IOException {
		driver.get(zappUrl);
		jQueryWait(driver);
		
		driver.findElement(By.xpath("/html/body/div[3]/div[1]/form/fieldset/input[1]")).clear();
		driver.findElement(By.xpath("/html/body/div[3]/div[1]/form/fieldset/input[1]")).sendKeys("Ugg Footwear");
		driver.findElement(By.xpath("/html/body/div[3]/div[1]/form/fieldset/button")).click();
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("SeleniumTestScreenShots/ZapposSearch/SearchA.jpg"));
		
	}
	
	@Test
	public void B_ZapposSearchToPDP_Test() throws InterruptedException, IOException {
		driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[1]/a[1]/img")).click();
		Thread.sleep(500);
		jQueryWait(driver);
		
		productName = driver.findElement(By.className("ProductName")).getText();
		
		//System.out.println(productName);
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("SeleniumTestScreenShots/ZapposSearch/SearchB.jpg"));
		
	}
	
	@Test
	public void C_ZapposPDPAddToCart_Test() throws InterruptedException, IOException {
		
		Select sizeSelector = new Select(driver.findElement(By.id("d3")));
		//Size 1 Little Kid
		String size = "1 Little Kid";
		sizeSelector.selectByVisibleText(size);
		jQueryWait(driver);
		driver.findElement(By.id("addToCart")).click();
		
		jQueryWait(driver);
		Thread.sleep(1000);
		String cartProduct = driver.findElement(By.className("name")).getText();
		
		assertTrue(cartProduct.contains(productName));
		assertTrue(driver.findElement(By.xpath("/html/body/div[4]/div[1]/table/tbody/tr/td[1]/ul/li[3]"))
				.getText().equalsIgnoreCase("size: "+ size));
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("SeleniumTestScreenShots/ZapposSearch/SearchC.jpg"));
		
	}
	
	@Test
	public void D_ZapposRemoveItemFromCart() throws InterruptedException, IOException {
		
		driver.findElement(By.xpath("//html/body/div[4]/div[1]/table/tbody/tr/td[3]/p/a[1]")).click();
		
		driver.navigate().refresh();
		Thread.sleep(2000);
		assertTrue(driver.findElement(By.xpath("//html/body/div[4]/div[1]/div[1]/div[1]/h1"))
				.getText().equals(emptyCart));
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("SeleniumTestScreenShots/ZapposSearch/SearchD.jpg"));
	}
	
}
