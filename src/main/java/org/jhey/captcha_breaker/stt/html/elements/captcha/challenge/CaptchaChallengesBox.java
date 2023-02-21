package org.jhey.captcha_breaker.stt.html.elements.captcha.challenge;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface CaptchaChallengesBox extends WebElement {
   default DeafChallenge getDeafChallenge(){
     return (DeafChallenge) this.findElement(By.xpath( "//*[@id=\"recaptcha-audio-button\"]"));
   }

   default void openDeafChallenge(){
      getDeafChallenge().click();
   }
}
