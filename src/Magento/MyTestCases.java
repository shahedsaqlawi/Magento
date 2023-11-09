package Magento;

import org.testng.Assert;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyTestCases extends parameter {
	@BeforeTest
	public void MySetup() {
		driver.get(myURL);
		driver.manage().window().maximize();
	}

	@Test() // invocationCount = 10
	public void AddOneRandomItem() throws InterruptedException, IOException {
		driver.get(myURL);
		int myRandomIndex = Rand.nextInt(3);// for category
		int RandomITem = Rand.nextInt(4);// for element of category

		WebElement[] myIDs = { driver.findElement(By.id("ui-id-4")), driver.findElement(By.id("ui-id-5")),
				driver.findElement(By.id("ui-id-6")) };
		myIDs[myRandomIndex].click();
		Thread.sleep(1000);

		if (driver.getCurrentUrl().contains("gear")) {
			WebElement container = driver.findElement(By.cssSelector(".product-items.widget-product-grid"));
			List<WebElement> myItems = container.findElements(By.tagName("li"));

			// choose random element from gear
			myItems.get(RandomITem).click();
			Thread.sleep(1000);
			driver.findElement(By.id("product-addtocart-button")).click();
			Thread.sleep(3000);

			// check added to cart msg if the element add or not
			String ActualMsg = driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div[1]/div[2]/div")).getText();
			System.out.println(ActualMsg);
			TakeScreenShotFunction();
			Assert.assertEquals(ActualMsg.contains("You added"), true);
		
		} else {
			WebElement container = driver.findElement(By.cssSelector(".product-items.widget-product-grid"));
			List<WebElement> myItems = container.findElements(By.tagName("li"));
			// choose random element from man and woman
			myItems.get(RandomITem).click();
			Thread.sleep(1000);
			// choose random size for items
			WebElement SizeBox = driver
					.findElement(By.cssSelector("div[class='swatch-attribute size'] div[role='listbox']"));
			List<WebElement> sizeBoxOptions = SizeBox.findElements(By.tagName("div"));
			int randomSize = Rand.nextInt(sizeBoxOptions.size());
			sizeBoxOptions.get(randomSize).click();

			// choose random color for items
			WebElement ColorBox = driver
					.findElement(By.cssSelector("div[class='swatch-attribute color'] div[role='listbox']"));
			List<WebElement> ColorBoxOptions = ColorBox.findElements(By.tagName("div"));
			int randomColor = Rand.nextInt(ColorBoxOptions.size());
			ColorBoxOptions.get(randomColor).click();
			Thread.sleep(1000);
			driver.findElement(By.id("product-addtocart-button")).click();
			Thread.sleep(3000);

			// check added to cart msg if the element add or not
			String ActualMsg = driver.findElement(By.xpath("//*[@id=\"maincontent\"]/div[1]/div[2]/div")).getText();
			System.out.println(ActualMsg);
			TakeScreenShotFunction();
			Assert.assertEquals(ActualMsg.contains("You added"), true);//
		

		}
	}

	@AfterTest
	public void PostTesting() {

	}

	static void TakeScreenShotFunction() throws IOException {
		Date date = new Date();

		String ActualDate = date.toString().replace(":", "-");
		System.out.println(ActualDate);
// take screenshot from driver
		TakesScreenshot src = ((TakesScreenshot) driver);

//get it from src and give to my picture
		File srcFile = src.getScreenshotAs(OutputType.FILE);

//		place of saved photo (destnation)
		File dest = new File(".//myPictures/" + ActualDate + ".png");
		FileUtils.copyFile(srcFile, dest);
	}
}
