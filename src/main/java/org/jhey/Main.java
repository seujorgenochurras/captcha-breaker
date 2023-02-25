package org.jhey;


import org.jhey.captcha_breaker.stt.html.elements.Captcha;
import org.jhey.captcha_breaker.stt.selenium.captcha.CaptchaFinder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
   static ChromeDriver driver;
   static WebElement element;
   public static void main(String[] args) {
      System.setProperty("webdriver.chrome.driver", "src/drive/chromedriver.exe");
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--disable-extensions");
      options.addArguments("--disable-popup-blocking");
      options.addArguments("--incognito");
      options.addArguments("--start-maximized");

      driver = new ChromeDriver(options);

      driver.get("https://www.google.com/recaptcha/api2/demo");

      Captcha captcha = CaptchaFinder.findCaptchaElement(driver);
      captcha.solveCaptcha();

      }
   private static void isAbleToGetOutside(WebDriver driver){
      try {
         System.out.println(driver.findElement(By.xpath("//*[@id=\"input1\"]")).getTagName());
         System.out.println("using :" + driver.hashCode() + "\n\n\n\n");
      } catch (Exception es) {
         System.out.println("not found it\n\n");
      }
   }
   public interface IDriver{
      WebDriver getDriver();
      default void switchTo(WebElement frame){
         getDriver().switchTo().frame(frame);
      }
      default void done(){
         getDriver().switchTo().defaultContent();
      }
   }
}
