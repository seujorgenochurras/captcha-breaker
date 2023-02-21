package org.jhey.captcha_breaker.stt.html.elements.captcha;

import org.openqa.selenium.WebElement;

public interface CaptchaCheckbox extends WebElement {
   String XPATH = "//*[@id='recaptcha-anchor']";
   default boolean isVerified(){
      return this.getAttribute("aria-checked").equals("true");
   }
}
