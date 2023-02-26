package org.jhey.captcha_breaker.stt.html.elements.captcha.challenge;

import org.jhey.captcha_breaker.stt.html.elements.DocumentElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DeafInputAudioText extends DocumentElement {
   public DeafInputAudioText(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);
   }
   @Override
   protected String getIframeXpath() {
      return CaptchaChallengesBox.XPATH;
   }
}
