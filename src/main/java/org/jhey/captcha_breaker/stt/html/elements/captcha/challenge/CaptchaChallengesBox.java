package org.jhey.captcha_breaker.stt.html.elements.captcha.challenge;

import org.jhey.captcha_breaker.stt.html.elements.CustomElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CaptchaChallengesBox extends CustomElement {
   public static final String XPATH = "//iframe[starts-with(@src, 'https://www.google.com/recaptcha/api2/bframe?')]";
   private final DeafChallenge deafChallenge;
   public CaptchaChallengesBox(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);

      webDriver.switchTo().frame(this.toWebElement());
      this.deafChallenge = new DeafChallenge
              (webDriver.findElement(By.xpath(DeafChallenge.XPATH)
                      ), webDriver);

      webDriver.switchTo().defaultContent();
   }
   public DeafChallenge getDeafChallenge(){
      return this.deafChallenge;
   }

   public void openDeafChallenge(){
      getDeafChallenge().click();
   }
}
