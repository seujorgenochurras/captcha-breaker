package org.jhey.captcha_breaker.stt.html.elements.captcha;

import org.jhey.captcha_breaker.stt.html.elements.DocumentElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CaptchaCheckbox extends DocumentElement {
   public static final String XPATH = "//*[@id='recaptcha-anchor']";
   public CaptchaCheckbox(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);
   }
   @Override
   protected String getIframeXpath() {
      return CaptchaSquareElement.XPATH;
   }

   public boolean isVerified(){
      return this.getAttribute("aria-checked").equals("true");
   }
}
