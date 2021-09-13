import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HerokuApp {

	WebDriver driver;
	WebElement element;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Mark\\Desktop\\Code\\selenium\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	/**
	 * Go here "https://the-internet.herokuapp.com/dropdown" Select option 1 from
	 * the dropdown Assert that option 1 is selected Assert that option 2 is NOT selected
	 **/
	@Test
	public void dropdown() {
		driver.get("https://the-internet.herokuapp.com/dropdown");
		element = driver.findElement(By.id("dropdown"));
		element.click();
		WebElement option1 = element.findElement(By.cssSelector("option[value='1']"));
		WebElement option2 = element.findElement(By.cssSelector("option[value='2']"));
		option1.click();
		assertTrue(option1.isSelected());
		assertFalse(option2.isSelected());
		
	}

	/**
	 * Go to "https://the-internet.herokuapp.com/hovers" Hover over the first image
	 * Assert that on hover there is text that is displayed below "name: user1"
	 **/
	@Test
	public void hover() {
		driver.get("https://the-internet.herokuapp.com/hovers");
		element = driver.findElement(By.className("figure"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
		
		element = driver.findElement(By.xpath("//*[contains(text(),'name: user1')]"));
		assertTrue("user1 should appear because we hovered over that image",
					element.isDisplayed());
	}

	/**
	 * https://the-internet.herokuapp.com/context_menu Right click close alert
	 * driver.switchTo().alert().accept();
	 **/
	@Test
	public void rightClick() {
		driver.get("https://the-internet.herokuapp.com/context_menu");
		element = driver.findElement(By.id("hot-spot"));
		Actions actions = new Actions(driver);
		actions.contextClick(element).perform();
		driver.switchTo().alert().accept();
	}

	/**
	 * go to "https://the-internet.herokuapp.com/key_presses" // send right arrow
	 * key to the input box // assert that you got this text back "You entered: RIGHT"
	 **/
	@Test
	public void keyPresses() {
		driver.navigate().to("https://the-internet.herokuapp.com/key_presses");
		element = driver.findElement(By.id("target"));
		element.click();
		
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.ARROW_RIGHT).pause(1000).perform();
		element = driver.findElement(By.id("result"));
		assertEquals("Clicked right arrow key", "You entered: RIGHT", element.getText());
	}
	
	/**
	 * go to "https://the-internet.herokuapp.com/login"
	 * Login (user: "tomsmith" and pass: "SuperSecretPassword!")
	 */
	@Test
	public void basicAuth() {
		driver.get("https://the-internet.herokuapp.com/login");
		element = driver.findElement(By.id("username"));
		element.sendKeys("tomsmith");
		element = driver.findElement(By.id("password"));
		element.sendKeys("SuperSecretPassword!");
		element = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/button/i"));
		element.click();
		
		//redirected to successful login (Secure Area)
		element = driver.findElement(By.xpath("/html/body/div[2]/div/div/h4"));
		assertEquals("not secure area", "Welcome to the Secure Area. When you are done click logout below.", element.getText());
	}
}
