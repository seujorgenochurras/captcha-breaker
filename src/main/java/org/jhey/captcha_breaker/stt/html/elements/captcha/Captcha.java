package org.jhey.captcha_breaker.stt.html.elements.captcha;

import org.jhey.captcha_breaker.stt.html.elements.captcha.ui.CaptchaCheckbox;
import org.jhey.captcha_breaker.stt.html.elements.captcha.ui.CaptchaSquareElement;
import org.jhey.captcha_breaker.stt.html.elements.captcha.ui.CaptchaSubmitButton;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.CaptchaChallengesBox;
import org.jhey.captcha_breaker.stt.selenium.captcha.CaptchaBreaker;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Captcha {
   private final CaptchaSquareElement captchaSquareElement;
   private CaptchaChallengesBox captchaChallengeElement;
   private final CaptchaCheckbox checkbox;
   private final WebDriver webDriver; // This is needed to be able to break the captcha
   private static final Logger logger = Logger.getLogger(Captcha.class.getName());
   public void solveCaptcha(){
      CaptchaBreaker captchaBreaker = new CaptchaBreaker(this);
      captchaBreaker.breakCaptcha();
   }

   public WebDriver getWebDriver() {
      return webDriver;
   }

   public CaptchaCheckbox getCheckbox() {
      return this.checkbox;
   }
   public CaptchaSquareElement getCaptchaSquareElement() {
      return captchaSquareElement;
   }

   public CaptchaChallengesBox getCaptchaChallengeElement() {
      return captchaChallengeElement;
   }

   private void setCaptchaChallengeElement(CaptchaChallengesBox captchaChallengeElement) {
      this.captchaChallengeElement = captchaChallengeElement;
   }
   public CaptchaSubmitButton getSubmitButton() {
      return getCaptchaChallengeElement().getBlindChallenge().getSubmitButton();
   }

   private static final String CAPTCHA_CHALLENGE_BOX = "//iframe[starts-with(@src, 'https://www.google.com/recaptcha/api2/bframe?')]";

   /**
    * Captcha don't render the challenge before you click the captcha box
    * use this method to tell this class to get the challenge when you are sure that it has been rendered
    * */
   public void generateChallengeElement(){
      try {
        setCaptchaChallengeElement(new CaptchaChallengesBox
              (webDriver.findElement(By.xpath(CAPTCHA_CHALLENGE_BOX)), webDriver));
      }catch (NoSuchElementException e){
         logger.log(Level.SEVERE, "Did not found a challenge element ");
         logger.log(Level.SEVERE, e.getMessage());
      }

   }
   private Captcha(CaptchaFactory captchaFactory){
      this.captchaSquareElement = captchaFactory.getCaptchaSquareElement();
      this.webDriver = captchaFactory.getWebDriver();
      this.checkbox = captchaFactory.getCheckbox();
   }

   public static class CaptchaFactory {
      private CaptchaSquareElement captchaSquareElement;
      private CaptchaCheckbox checkbox;
      private WebDriver webDriver;

      private CaptchaSquareElement getCaptchaSquareElement() {
         return this.captchaSquareElement;
      }
      public CaptchaFactory setCaptchaSquareElement(CaptchaSquareElement captchaSquareElement) {
         this.captchaSquareElement = captchaSquareElement;
         return this;
      }
      private CaptchaCheckbox getCheckbox() {
         return this.checkbox;
      }
      private WebDriver getWebDriver() {
         return this.webDriver;
      }

      public CaptchaFactory setWebDriver(WebDriver webDriver) {
         this.webDriver = webDriver;
         return this;
      }

      public Captcha create(){
         this.webDriver.switchTo().frame(captchaSquareElement.toWebElement());
         this.checkbox = (
                 new CaptchaCheckbox(
                         webDriver.findElement(By.xpath(CaptchaCheckbox.XPATH))
                         , webDriver));
         this.webDriver.switchTo().defaultContent();
         return new Captcha(this);
      }
   }

}

