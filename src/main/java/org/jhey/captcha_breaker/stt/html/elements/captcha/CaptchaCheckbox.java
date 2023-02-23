package org.jhey.captcha_breaker.stt.html.elements.captcha;

import org.jhey.captcha_breaker.stt.html.elements.CustomElement;
import org.openqa.selenium.WebElement;

public class CaptchaCheckbox extends CustomElement {
   public static final String XPATH = "//*[@id='recaptcha-anchor']";
   public CaptchaCheckbox(WebElement webElement) {
      super(webElement);
   }
   public boolean isVerified(){
      return this.getAttribute("aria-checked").equals("true");
   }
}
