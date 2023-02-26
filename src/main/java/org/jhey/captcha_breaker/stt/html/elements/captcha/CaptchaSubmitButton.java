package org.jhey.captcha_breaker.stt.html.elements.captcha;

import org.jhey.captcha_breaker.stt.html.elements.DocumentElement;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.CaptchaChallengesBox;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CaptchaSubmitButton extends DocumentElement {
   public static final String XPATH = "//*[@id=\"recaptcha-verify-button\"]";
   public CaptchaSubmitButton(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);
   }
   @Override
   protected String getIframeXpath() {
      return CaptchaChallengesBox.XPATH;
   }

   @Override
   public void click() {
      super.click();
      System.out.println("Clciked");
   }
}