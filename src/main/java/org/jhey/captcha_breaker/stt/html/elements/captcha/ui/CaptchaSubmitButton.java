package org.jhey.captcha_breaker.stt.html.elements.captcha.ui;

import org.jhey.captcha_breaker.stt.html.elements.document.DocumentElement;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.CaptchaChallengesBox;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CaptchaSubmitButton extends DocumentElement {
   public static final String XPATH = "//*[@id=\"recaptcha-verify-button\"]";

   public CaptchaSubmitButton(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);
   }
   @Override
   public String getXpath() {
      return XPATH;
   }

   @Override
   protected String getIframeXpath() {
      return CaptchaChallengesBox.XPATH;
   }
}