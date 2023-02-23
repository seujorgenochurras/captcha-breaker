package org.jhey.captcha_breaker.stt.html.elements.captcha.challenge;

import org.jhey.captcha_breaker.stt.html.elements.CustomElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CaptchaChallengesBox extends CustomElement {
   public CaptchaChallengesBox(WebElement webElement) {
      super(webElement);
   }

   public DeafChallenge getDeafChallenge(){
     return new DeafChallenge(this.findElement(By.xpath( "//*[@id=\"recaptcha-audio-button\"]")));
   }

   public void openDeafChallenge(){
      getDeafChallenge().click();
   }
}
