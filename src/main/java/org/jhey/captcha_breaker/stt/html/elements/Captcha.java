package org.jhey.captcha_breaker.stt.html.elements;

import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaCheckbox;
import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaSquareElement;
import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaSubmitButton;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.CaptchaChallengesBox;
import org.jhey.captcha_breaker.stt.selenium.captcha.CaptchaBreaker;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Captcha {
   private CaptchaSquareElement captchaSquareElement;
   private CaptchaChallengesBox captchaChallengeElement;
   private CaptchaCheckbox checkbox;
   private WebDriver webDriver; // This is needed to be able to break the captcha
   private static final Logger logger = Logger.getLogger(Captcha.class.getName());
   private void setCheckbox(CaptchaCheckbox checkbox) {
      this.checkbox = checkbox;
   }

   public void solveCaptcha(){
      CaptchaBreaker captchaBreaker = new CaptchaBreaker(this);
      captchaBreaker.breakCaptcha();
   }
   public CaptchaCheckbox getCheckbox() {
      return this.checkbox;
   }

   public WebDriver getWebDriver() {
      return webDriver;
   }

   public Captcha setWebDriver(WebDriver webDriver) {
      this.webDriver = webDriver;
      return this;
   }

   public CaptchaSquareElement getCaptchaSquareElement() {
      return captchaSquareElement;
   }

   public Captcha setCaptchaSquareElement(CaptchaSquareElement captchaSquareElement) {
      this.captchaSquareElement = captchaSquareElement;
      return this;
   }

   public CaptchaChallengesBox getCaptchaChallengeElement() {
      return captchaChallengeElement;
   }

   private void setCaptchaChallengeElement(CaptchaChallengesBox captchaChallengeElement) {
      this.captchaChallengeElement = captchaChallengeElement;
   }
   public CaptchaSubmitButton getSubmitButton() {
      return getCaptchaChallengeElement().getDeafChallenge().getSubmitButton();
   }
   public Captcha build(){
      webDriver.switchTo().frame(captchaSquareElement.toWebElement());
      setCheckbox(
              new CaptchaCheckbox(
                      webDriver.findElement(By.xpath(CaptchaCheckbox.XPATH))
                      , webDriver));
      webDriver.switchTo().defaultContent();

      return this;
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
}
