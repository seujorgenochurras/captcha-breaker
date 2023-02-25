package org.jhey.captcha_breaker.stt.html.elements.captcha;

import org.jhey.captcha_breaker.stt.html.elements.DocumentElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CaptchaSubmitButton extends DocumentElement {
   public CaptchaSubmitButton(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);
   }

   @Override
   protected String getIframeXpath() {
      return null;
   }
}
