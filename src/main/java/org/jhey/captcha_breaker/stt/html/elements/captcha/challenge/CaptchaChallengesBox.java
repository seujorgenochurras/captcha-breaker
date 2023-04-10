package org.jhey.captcha_breaker.stt.html.elements.captcha.challenge;

import org.jhey.custom.element.CustomElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CaptchaChallengesBox extends CustomElement {
   public static final String XPATH = "//iframe[starts-with(@src, 'https://www.google.com/recaptcha/api2/bframe?')]";
   private final BlindChallenge blindChallenge;
   public CaptchaChallengesBox(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);

      webDriver.switchTo().frame(this.toWebElement());
      this.blindChallenge = new BlindChallenge
              (webDriver.findElement(By.xpath(BlindChallenge.XPATH)
                      ), webDriver);

      webDriver.switchTo().defaultContent();
   }

   @Override
   public String getXpath() {
      return XPATH;
   }

   public BlindChallenge getBlindChallenge(){
      return this.blindChallenge;
   }

   public void openBlindChallenge(){
      getBlindChallenge().click();
   }
}
