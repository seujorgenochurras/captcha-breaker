package org.jhey.captcha_breaker.stt.html.elements;

import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaCheckbox;
import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaSquareElement;
import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaSubmitButton;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.CaptchaChallengesBox;
import org.jhey.captcha_breaker.stt.html.elements.captcha.service.CheckboxService;
import org.jhey.captcha_breaker.stt.selenium.captcha.CaptchaBreaker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Captcha {
   private CaptchaSquareElement captchaSquareElement;
   private CaptchaChallengesBox captchaChallengeElement;
   private WebDriver webDriver; // This is needed to be able to break the captcha
   private CaptchaCheckbox checkbox;
   private CheckboxService checkboxService;
   private void setCheckbox(CaptchaCheckbox checkbox) {
      this.checkbox = checkbox;
   }

   public void solveCaptcha(){
      CaptchaBreaker captchaBreaker = new CaptchaBreaker(this);
      captchaBreaker.breakCaptcha();
   }
   private void registerNewCheckbox(){
      getWebDriver().switchTo().frame(getCaptchaSquareElement().toWebElement());
      setCheckbox(new CaptchaCheckbox
              (webDriver.findElement(By.id("recaptcha-anchor")), webDriver));
      getWebDriver().switchTo().defaultContent();
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

   public Captcha setCaptchaChallengeElement(CaptchaChallengesBox captchaChallengeElement) {
      this.captchaChallengeElement = captchaChallengeElement;
      return this;
   }

   public CaptchaSubmitButton getSubmitButton(){
      return new CaptchaSubmitButton(captchaSquareElement.findElement(By.id("recaptcha-verify-button")), webDriver);
   }
   public Captcha build(){
      checkboxService = new CheckboxService(getWebDriver());
      registerNewCheckbox();
      return this;
   }
}
