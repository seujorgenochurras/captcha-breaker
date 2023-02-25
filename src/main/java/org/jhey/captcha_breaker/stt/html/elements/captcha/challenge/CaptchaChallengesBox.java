package org.jhey.captcha_breaker.stt.html.elements.captcha.challenge;

import org.jhey.captcha_breaker.stt.html.elements.CustomElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CaptchaChallengesBox extends CustomElement {
   WebDriver webDriver;

   public CaptchaChallengesBox(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);
      this.webDriver = webDriver;
   }

   public DeafChallenge getDeafChallenge(){
     return new DeafChallenge(this.findElement(By.xpath( "//*[@id=\"recaptcha-audio-button\"]")), webDriver);
   }

   public void openDeafChallenge(){
      getDeafChallenge().click();
   }
}
