package org.jhey;


import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
   static ChromeDriver driver;

   public static void main(String[] args) {
      System.setProperty("webdriver.chrome.driver", "src/drive/chromedriver.exe");
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--disable-extensions");
      options.addArguments("--disable-popup-blocking");
      options.addArguments("--incognito");
      options.addArguments("--start-maximized");

      driver = new ChromeDriver(options);

      driver.get("https://www.google.com/recaptcha/api2/demo");
      try{
         System.out.println(driver.findElement(By.xpath("//*[@id=\"input1\"]")).getText());
      } catch (Exception e){
         System.out.println("not found it");
      driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"recaptcha-demo\"]/div/div/iframe")));
      try{
         System.out.println(driver.findElement(By.xpath("//*[@id=\"input1\"]")).getText());
         } catch (Exception es){
         System.out.println("not found it");
      }


      //
//         Captcha captcha = CaptchaFinder.findCaptchaElement(driver);
//         captcha.solveCaptcha();
   }
}
