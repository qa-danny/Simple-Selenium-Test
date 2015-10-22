package tests.danny;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class GoogleSearchTests {

	private static WebDriver driver;
	ArrayList<String> searchTerms = new ArrayList<String>();
	String goog = "http://www.google.com/";
	int searchTermSize;
	String title;

	@Before
	public void setUp() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(false);

		driver = new FirefoxDriver(profile);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void GoogleSearch() throws IOException, InterruptedException {
		prepSearchTerms();

		for (int i = 0; i < searchTerms.size(); i++) {

			driver.get(goog);
			driver.navigate().refresh();

			driver.findElement(By.name("q")).clear();
			driver.findElement(By.name("q")).sendKeys(searchTerms.get(i));

			jQueryWait(driver);

			if (i == 0) {
				Thread.sleep(1000);
				Assert.assertEquals("Las Vegas, NV 89148",
						driver.findElement(By.xpath("//*[@id=\"wob_loc\"]")).getText());
			}

			if (i == 1) {
				Thread.sleep(1000);
				title = driver.getTitle();
				Assert.assertTrue(title.equalsIgnoreCase("The Avengers - Google Search"));
			}

			if (i == 2) {
				Thread.sleep(1000);
				//Clicks search button
				driver.findElement(By.className("sbico")).click();

				//Grabs first result, which is a paid advertisement.
				WebElement entry = driver.findElement(By.id("vs0p1"));

				//What does the link say!?
				String vacation = entry.getText();
				//System.out.println("\n" + vacation);
				Assert.assertTrue(vacation.contains("Beaches® Resorts for Kids - Beaches.com"));

				//Where does the link point to?
				String vacationLink = entry.getAttribute("href");
				//System.out.println(vacationLink);
				Assert.assertTrue(vacationLink.contains("beaches.com/vacation/kids"));
			}


		}//Close For Loop
	}

	/**
	 * This will load the SearchTerms.txt file, line by line, into 
	 * 	an array list 
	 * @throws FileNotFoundException
	 */
	private void prepSearchTerms() throws FileNotFoundException {

		Scanner scan = new Scanner(new File("SearchTerms.txt"));
		while (scan.hasNextLine()){
			searchTerms.add(scan.nextLine());
		}
		scan.close();

		//For reading each entry in searchTerms:
		for (String words : searchTerms) {
			System.out.println(words);
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


	@After
	public void tearDown() {
		driver.close();
	}
}
