package com.school;

import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

public class School {
	
	private static final Class[] ElementNotFoundException = null;
	WebDriver driver;
	
	@BeforeTest
	public void setUp(){
		
		System.setProperty("webdriver.chrome.driver", "/Users/Murodil Ruzimatov/Documents/Librares/drivers/chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
//		public static void maximizeScreen(WebDriver driver) { 
//			java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
//			Point position = new Point(0, 0); 
//			driver.manage().window().setPosition(position); 
//			Dimension maximizedScreenSize = 
//			new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight()); 
//			driver.manage().window().setSize(maximizedScreenSize); 
//			}+
		
		
		
	}
	
	@Test(enabled=false)
	public void test(){
//		go to  https://www.w3schools.com/
		driver.get("https://www.w3schools.com/");
//		Find the number of elements in the page with the tag a
		System.out.println(driver.findElements(By.tagName("a")).size());
//		for each of those elements, if it is displayed on the page, print the text and the url of that element.
		List<WebElement> links=driver.findElements(By.tagName("a"));
	
		for(WebElement link : links){
			String linkList=link.getText();
			//System.out.println(linkList);
			
		System.out.println(link.getText()+"\n"+link.getAttribute("href"));	
		}
		
	}
	@Test(enabled=true)
	  
	public void amazonTest1(){
//		go to amazon.com
		driver.get("http://www.amazon.com/");
//		search for "bag" and click
		driver.findElement(By.name("field-keywords")).sendKeys("Bag"+Keys.ENTER);
//		take note of the price of the top result
		String expectedPrice=driver.findElement(By.xpath("//span[@class='sx-price sx-price-large']"))
				.getText().replaceAll(" ", "");
		String newExpectedPrice = expectedPrice.substring(0,3)+"."+expectedPrice.substring(3);
		
//		click on the top result  
		driver.findElement(By.cssSelector(".s-access-image.cfMarker")).click();	
//		verify default number of items is 1
		Select select=new Select(driver.findElement(By.id("quantity")));
		String expectedQ="1";
		String actualQ=select.getFirstSelectedOption().getAttribute("value");
		System.out.println(actualQ);
		Assert.assertEquals(expectedQ, actualQ);
		
//		verify that price is the same as the one from step 4
		String actualPrice=driver.findElement(By.cssSelector("#priceblock_ourprice")).getText();
		System.out.println(newExpectedPrice);
		System.out.println(actualPrice);
			Assert.assertEquals(newExpectedPrice, actualPrice);
//			verify message "Added to Cart" is visible
		driver.findElement(By.id("add-to-cart-button")).click();
		driver.findElement(By.cssSelector(".a-size-medium.a-text-bold")).isDisplayed();
		}
	
	@Test(enabled=false)
	public void amazonTest2() throws Exception{
//		go to amazon.com
		driver.get("http://www.amazon.com/");
//		search for "bag" and click search
		driver.findElement(By.name("field-keywords")).sendKeys("Bag"+Keys.ENTER);
//		take note of the results from the first line
		String actualBagResults=driver.findElement(By.id("s-result-count")).getText();
//		select checkbox coach
		WebElement checkBox=driver.findElement(By.xpath("(//span[@class='a-size-small a-color-base "
				+ "s-ref-text-link s-ref-link-cursor'])[11]"));
		checkBox.click();
//		verify that the results from the first line changed
		String actuallCoachRes=driver.findElement(By.cssSelector("#s-result-count")).getText();
		Assert.assertFalse(actualBagResults.equals(actuallCoachRes));
//		unselect checkbox Coach

		String checkCoach=driver.findElement(By.xpath("//input[@name='s-ref-checkbox-Coach']")).getText();
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("span[class$='s-ref-text-link s-ref-link-cursor a-text-bold']"))));
		driver.findElement(By.xpath("(//input[@type='checkbox'])[12]")).click();
		//verify that original results appear on the top line
		String lastBagsResults=driver.findElement(By.id("s-result-count")).getText();
		System.out.println(actualBagResults);
		System.out.println("last Bags res: "+ lastBagsResults);
		Assert.assertFalse(actualBagResults.equals(lastBagsResults));

}
	
	@Test(enabled=false)
	public void amazonTest3(){
		
//		go to amazon.com
		driver.get("http://www.amazon.com/");
//		search for "bag" and click
		driver.findElement(By.name("field-keywords")).sendKeys("Bag"+Keys.ENTER);
//		verify that none of the checkboxes under brand are checked
		List <WebElement> brandList=driver.findElements(By.cssSelector("#leftNavContainer > ul:nth-child(14) > div > li")); 
		for (int i = 0; i < brandList.size(); i++) { 
		Assert.assertTrue(!brandList.get(i).isSelected());	
		} 
		//select the first checkbox //UGRACE 
		driver.findElement(By.xpath("//span[contains(text(), 'MICHAEL Michael Kors')]//parent::span[@class='a-label a-checkbox-label']//preceding-sibling::input[@name='s-ref-checkbox-MICHAEL Michael Kors']")).click();
		//select the last checkbox 
		driver.findElement(By.xpath("//span[text()='AmazonBasics']//parent::span[@class='a-label a-checkbox-label']//preceding-sibling::input[@name='s-ref-checkbox-AmazonBasics']")).click();
		//verify that other checkboxes are still not checked 

		List <WebElement> brandList2=driver.findElements(By.xpath("//div[@id='leftNavContainer']//ul[5]"));
		
		for (int j =0 ; j <=brandList2.size()-1; j++) { 
			if(brandList2.get(j).isSelected()==false){
				 
//				Assert.assertFalse(brandList2.get(j).isSelected());
			System.out.println("Brand is selected----> "+brandList2.get(j).getText());
			}}		 
			//click on link Clear 
		//driver.findElement(By.linkText("Clear")).click();	
		//verify that none of the checkboxes under brand are checked 
		}
	//@Test
	public void amazonTest4(){
		
//		go to amazon.com
		driver.get("http://www.amazon.com/");
//		search for "bag" and click
		driver.findElement(By.name("field-keywords")).sendKeys("Bag"+Keys.ENTER);
	try{
		System.out.println("Try");
		driver.findElement(By.xpath("//input[@type='checkbo']")).click();
	
	}catch(NoSuchElementException e){
		System.out.println("Catch");
	}
	finally{
		System.out.println("Finally");
		driver.close();
	}
	 
	//input[text()='text_']
	//input[starts-with, 'text_']
	//input[ends-with, '_text_']
	
	
	
		
	
	}
}
	
	
		
	
	//@Test(expectedExceptions = NotFoundException.class)
	//public void divisionWithException() {
		
	
	
	


