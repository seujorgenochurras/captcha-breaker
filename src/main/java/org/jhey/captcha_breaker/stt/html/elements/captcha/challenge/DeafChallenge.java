package org.jhey.captcha_breaker.stt.html.elements.captcha.challenge;

import org.jhey.captcha_breaker.stt.html.elements.captcha.ui.CaptchaSubmitButton;
import org.jhey.captcha_breaker.stt.html.elements.document.DocumentUtils;
import org.jhey.captcha_breaker.stt.selenium.captcha.CaptchaBreaker;
import org.jhey.custom.element.document.DocumentElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeafChallenge extends DocumentElement {

   public static final String XPATH = "//*[@id=\"recaptcha-audio-button\"]";
   private DeafInputAudioText deafInputAudioText;
   private CaptchaSubmitButton submitButton;
   private WebElement audioButtonElement;
   public DeafChallenge(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);
   }

   @Override
   public String getXpath() {
      return XPATH;
   }

   @Override
   public void click() {
      super.click();
      webDriver.switchTo().frame(webDriver.findElement(By.xpath(CaptchaChallengesBox.XPATH)));
      DocumentUtils.waitExistenceOf(this.toWebElement(), webDriver, Duration.ofSeconds(2));

      this.deafInputAudioText = new DeafInputAudioText(
              webDriver.findElement(By.xpath("//*[@id=\"audio-response\"]")),
              webDriver);

      this.audioButtonElement = webDriver.findElement(By.xpath("//*[@id=\"rc-audio\"]/div[7]/a"));
      this.submitButton = new CaptchaSubmitButton(webDriver.findElement(By.xpath(CaptchaSubmitButton.XPATH)), webDriver);

      webDriver.switchTo().defaultContent();
   }
   public CaptchaSubmitButton getSubmitButton() {
      return submitButton;
   }

   @Override
   protected String getIframeXpath() {
      return CaptchaChallengesBox.XPATH;
   }

   public URL getAudioURL() {
      webDriver.switchTo().frame(webDriver.findElement(By.xpath(CaptchaChallengesBox.XPATH)));
      try{
         URL result =  new URL(audioButtonElement.getAttribute("href"));
         webDriver.switchTo().defaultContent();
         return result;
      }catch (MalformedURLException e){
        Logger logger = Logger.getLogger(CaptchaBreaker.class.getName());
        logger.log(Level.SEVERE,
                e.getMessage().concat(" Couldn't get the captcha audio URL"));
      }
      webDriver.switchTo().defaultContent();
      return null;
   }
   public DeafInputAudioText getInputAudio(){
      return this.deafInputAudioText;
   }

}
