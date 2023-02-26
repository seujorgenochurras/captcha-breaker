package org.jhey.captcha_breaker.stt.html.elements.captcha.challenge;

import org.jhey.captcha_breaker.stt.html.elements.DocumentElement;
import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaSubmitButton;
import org.jhey.captcha_breaker.stt.selenium.captcha.CaptchaBreaker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeafChallenge extends DocumentElement {

   public static final String XPATH = "//*[@id=\"recaptcha-audio-button\"]";
   private DeafInputAudioText deafInputAudioText;
   private final WebDriver webDriver1;
   private CaptchaSubmitButton submitButton;
   private WebElement audioButtonElement;
   public DeafChallenge(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);
      this.webDriver1 = webDriver;
   }

   @Override
   public void click() {
      super.click();
      webDriver1.switchTo().frame(webDriver1.findElement(By.xpath(CaptchaChallengesBox.XPATH)));
      try {
      Thread.sleep(3000);

      }catch (Exception e){
         System.out.println("EXCEPTIONS! " + e.getStackTrace());
      }

      this.deafInputAudioText = new DeafInputAudioText(
              webDriver1.findElement(By.xpath("//*[@id=\"audio-response\"]")),
              webDriver1);

      this.audioButtonElement = webDriver1.findElement(By.xpath("//*[@id=\"rc-audio\"]/div[7]/a"));

      this.submitButton = new CaptchaSubmitButton(webDriver1.findElement(By.xpath(CaptchaSubmitButton.XPATH)), webDriver1);

      webDriver1.switchTo().defaultContent();
   }
   public CaptchaSubmitButton getSubmitButton() {
      return submitButton;
   }

   @Override
   protected String getIframeXpath() {
      return CaptchaChallengesBox.XPATH;
   }

   public URL getAudioURL() {
      webDriver1.switchTo().frame(webDriver1.findElement(By.xpath(CaptchaChallengesBox.XPATH)));
      try{
         URL result =  new URL(audioButtonElement.getAttribute("href"));
         webDriver1.switchTo().defaultContent();
         return result;
      }catch (MalformedURLException e){
        Logger logger = Logger.getLogger(CaptchaBreaker.class.getName());
        logger.log(Level.SEVERE,
                e.getMessage().concat(" Couldn't get the captcha audio URL"));
      }
      webDriver1.switchTo().defaultContent();
      return null;
   }
   public DeafInputAudioText getInputAudio(){
      return this.deafInputAudioText;
   }

}
