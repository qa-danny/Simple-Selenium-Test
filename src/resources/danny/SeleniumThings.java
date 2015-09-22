package resources.danny;

import org.junit.Test;
import org.openqa.selenium.By;

public class SeleniumThings extends SeleniumBase {
	
	@Test
	public void test1() {	
		driver.get(baseUrl);
		isElementPresent(By.xpath("//*[@id=\"6pm-logo\"]"));
	}
	
	@Test
	public void test2() {
		driver.get("www.google.com");
		isElementPresent(By.xpath("//*[@id=\"hplogo\"]"));
	}

}
