package org.jhey.captcha_breaker.stt.selenium.captcha;

import org.jhey.captcha_breaker.stt.html.elements.captcha.Captcha;
import org.jhey.captcha_breaker.stt.html.elements.captcha.ui.CaptchaSquareElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CaptchaFinder {
   private static final String CAPTCHA_CLICKABLE_BOX_XPATH = "//iframe[starts-with(@title, 'reCAPTCHA')]";


   private CaptchaFinder() {
      //Utility class
   }

   public static Captcha findCaptchaElement(final WebDriver driver){
      try {
         CaptchaSquareElement captchaBoxElement = new CaptchaSquareElement(driver.findElement(By.xpath(CAPTCHA_CLICKABLE_BOX_XPATH)), driver);
         return new Captcha.CaptchaFactory()
                 .setCaptchaSquareElement(captchaBoxElement)
                 .setWebDriver(driver)
                 .create();

      }catch (NoSuchElementException e){
         Logger logger = Logger.getLogger(CaptchaFinder.class.getName());
         logger.log(Level.SEVERE, "Captcha element not found");
        throw e;
      }
   }

}
