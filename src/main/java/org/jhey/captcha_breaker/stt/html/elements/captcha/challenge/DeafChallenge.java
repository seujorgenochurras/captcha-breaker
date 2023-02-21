package org.jhey.captcha_breaker.stt.html.elements.captcha.challenge;

import org.jhey.captcha_breaker.stt.selenium.CaptchaBreaker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface DeafChallenge extends WebElement {
   default URL getAudioURL() {
         String audioUrlXpath = "//*[@id=\"rc-audio\"]/div[7]/a";
         WebElement audioButtonElement = this.findElement(By.xpath(audioUrlXpath));
      try{
         return new URL(audioButtonElement.getAttribute("href"));
      }catch (MalformedURLException e){
        Logger logger = Logger.getLogger(CaptchaBreaker.class.getName());
        logger.log(Level.SEVERE,
                e.getMessage().concat(" Couldn't get the captcha audio URL"));
      }
      return null;
   }
   default DeafInputAudio getInputAudio(){
      return (DeafInputAudio) this.findElement(By.xpath("//*[@id=\"audio-response\"]"));
   }
   String XPATH = "//*[@id=\"recaptcha-audio-button\"]";

}
