package org.jhey;

import org.jhey.captcha_breaker.stt.html.elements.captcha.Captcha;
import org.jhey.captcha_breaker.stt.selenium.captcha.CaptchaFinder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
   public static void main(String[] args) {
      ChromeOptions options = new ChromeOptions().addArguments("--remote-allow-origins=*"); //needed to fix a common bug among new chromeDriver versions (112.0)
      WebDriver driver = new ChromeDriver(options);

      driver.get("https://www.google.com/recaptcha/api2/demo");

      Captcha captcha = CaptchaFinder.findCaptchaElement(driver);
      captcha.solveCaptcha("e4e63db875154d3ebc0c966d8b1bd793");
   }
}