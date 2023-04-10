package org.jhey.captcha_breaker.stt.html.elements.captcha.challenge;

import org.jhey.custom.element.document.DocumentElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BlindInputAudioText extends DocumentElement {
   public static final String XPATH = "//*[@id=\"audio-response\"]";
   public BlindInputAudioText(WebElement webElement, WebDriver webDriver) {
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
