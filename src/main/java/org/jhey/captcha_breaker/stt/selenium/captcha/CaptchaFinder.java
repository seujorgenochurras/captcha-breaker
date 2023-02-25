package org.jhey.captcha_breaker.stt.selenium.captcha;

import org.jhey.captcha_breaker.stt.html.elements.Captcha;
import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaSquareElement;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.CaptchaChallengesBox;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CaptchaFinder {
   private static final String CAPTCHA_CLICKABLE_BOX_XPATH = "//iframe[starts-with(@title, 'reCAPTCHA')]";
   private static final String CAPTCHA_CHALLENGE_BOX = "//iframe[starts-with(@src, 'https://www.google.com/recaptcha/api2/bframe?')]";

   private CaptchaFinder() {
      //Utility class
   }

   public static Captcha findCaptchaElement(final WebDriver driver){
      try {
         CaptchaSquareElement captchaBoxElement = new CaptchaSquareElement(driver.findElement(By.xpath(CAPTCHA_CLICKABLE_BOX_XPATH)), driver);

         CaptchaChallengesBox captchaChallengeElement = new CaptchaChallengesBox
                 (driver.findElement(By.xpath(CAPTCHA_CHALLENGE_BOX)), driver);

         return new Captcha()
                 .setCaptchaChallengeElement(captchaChallengeElement)
                 .setCaptchaSquareElement(captchaBoxElement)
                 .setWebDriver(driver)
                 .build();

      }catch (NoSuchElementException e){
         Logger logger = Logger.getLogger(CaptchaFinder.class.getName());
         logger.log(Level.SEVERE, "Captcha element not found");
        throw e;
      }
   }

}
