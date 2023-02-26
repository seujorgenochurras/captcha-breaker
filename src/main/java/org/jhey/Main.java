package org.jhey;


import org.jhey.captcha_breaker.stt.html.elements.captcha.Captcha;
import org.jhey.captcha_breaker.stt.selenium.captcha.CaptchaFinder;
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

      Captcha captcha = CaptchaFinder.findCaptchaElement(driver);
      captcha.solveCaptcha();

   }
}