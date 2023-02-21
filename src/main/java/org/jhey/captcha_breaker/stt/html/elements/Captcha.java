package org.jhey.captcha_breaker.stt.html.elements;

import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaCheckbox;
import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaElement;
import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaSubmitButton;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.CaptchaChallengesBox;
import org.jhey.captcha_breaker.stt.selenium.CaptchaBreaker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Captcha {
   private CaptchaElement captchaBoxElement;
   private CaptchaChallengesBox captchaChallengeElement;

   private WebDriver webDriver; // This is needed to be able to break the captcha
   //see CaptchaBreaker class

   public void solveCaptcha(){
      CaptchaBreaker captchaBreaker = new CaptchaBreaker(this);
      captchaBreaker.breakCaptcha();
   }

   public CaptchaCheckbox getCheckbox(){
         return (CaptchaCheckbox) getCaptchaBoxElement().findElement(By.xpath("//*[@id='recaptcha-anchor']"));
   }

   public WebDriver getWebDriver() {
      return webDriver;
   }

   public Captcha setWebDriver(WebDriver webDriver) {
      this.webDriver = webDriver;
      return this;
   }

   public CaptchaElement getCaptchaBoxElement() {
      return captchaBoxElement;
   }

   public Captcha setCaptchaBoxElement(CaptchaElement captchaBoxElement) {
      this.captchaBoxElement = captchaBoxElement;
      return this;
   }

   public CaptchaChallengesBox getCaptchaChallengeElement() {
      return captchaChallengeElement;
   }

   public Captcha setCaptchaChallengeElement(CaptchaChallengesBox captchaChallengeElement) {
      this.captchaChallengeElement = captchaChallengeElement;
      return this;
   }

   public CaptchaSubmitButton getSubmitButton(){
      return (CaptchaSubmitButton) captchaBoxElement.findElement(By.id("recaptcha-verify-button"));
   }
}
