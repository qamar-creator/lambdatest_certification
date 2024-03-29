package lambdatest_p;

//TestNG Todo : Sample App
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestNGTodo{
   public String username = "Qamar";
  public String accesskey = "wQgHKY1XQUdSGQBjnsX7fk2Adibv0yHlb1QnfvfnBUZfJPfDoO";
  public static RemoteWebDriver driver = null;
  public String gridURL = "@hub.lambdatest.com/wd/hub";
  boolean status = false;

  @BeforeClass
  public void setUp() throws Exception {
     DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("browserName", "chrome");
      capabilities.setCapability("version", "70.0");
      capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get the any available one
      capabilities.setCapability("build", "#build no.1");
      capabilities.setCapability("name", "Test_lambda1");
     
      try {
          driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
      } catch (MalformedURLException e) {
          System.out.println("Invalid grid URL");
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }

  }

  @Test
  public void scrollink_integration() throws Exception {
     try {
            //Change it to production page
          driver.get("https://www.lambdatest.com/"); // Navigate to this URL
           
          // do Explicit wait until lambdatest logo appears
          WebDriverWait wait = new WebDriverWait(driver,30);
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Logo']")));
            //Let's mark done first two items in the list.
            JavascriptExecutor je = (JavascriptExecutor) driver;
            WebElement elmnt = driver.findElementByXPath("//a[normalize-space()='See All Integrations']");
            je.executeScript("arguments[0].scrollIntoView(true);",elmnt);
            System.out.println(elmnt.getText());
            elmnt.click();
            
           
           // Let's add an item in the list.
            driver.findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list");
          driver.findElement(By.id("addbutton")).click();
           
            // Let's check that the item we added is added in the list.
          String enteredText = driver.findElementByXPath("/html/body/div/div/div/ul/li[6]/span").getText();
          if (enteredText.equals("Yey, Let's add it to list")) {
              status = true;
          }
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
  }

  @AfterClass
  public void tearDown() throws Exception 
  {
     if (driver != null)
     {
          ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
          driver.quit();
      }
  }
}