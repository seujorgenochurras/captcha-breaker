package org.jhey.captcha_breaker.stt.selenium;

import org.jhey.captcha_breaker.stt.api.AudioParser;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Finds and breaks any captcha on the site.
 * <b>Warning</b> as it uses a shallow copy of your {@code WebDriver}
 * it is not recommended to do selenium with multi-threads with it.
 * It's likely to break the code and give an unwanted error
 * */
public class CaptchaBreaker {
   private final WebDriver webDriver;
   private static final String CAPTCHA_CLICKABLE_BOX_XPATH = "//iframe[starts-with(@title, 'reCAPTCHA')]";
   private static final String VERIFIED_BOX_XPATH = "//*[@id='recaptcha-anchor']";

   private static final Logger logger = Logger.getLogger(CaptchaBreaker.class.getName());

   public CaptchaBreaker(WebDriver webDriver) {
      this.webDriver = webDriver;
   }

   /**
    * Method used to find and break any captcha that is on the site.
    *
    * @see AudioParser
    * */
   public void breakCaptcha()throws InterruptedException{
     WebElement captchaBox = webDriver.findElement(By.xpath(CAPTCHA_CLICKABLE_BOX_XPATH));
     captchaBox.click();
     waitUntilIsUsable(CAPTCHA_CLICKABLE_BOX_XPATH);
     if (isCaptchaCompleted(captchaBox)) return;

     String biggerBoxXpath = "//iframe[starts-with(@src, 'https://www.google.com/recaptcha/api2/bframe?')]";

      WebElement captchaBiggerBox = webDriver.findElement(By.xpath(biggerBoxXpath));
      waitUntilIsUsable(biggerBoxXpath);
      try {

         solveCaptcha(captchaBiggerBox);

      }catch (IOException | ExecutionException e){
         logger.log(Level.SEVERE, e.getMessage());
      }
   }
   
   private boolean isCaptchaCompleted(WebElement captchaElement){
      webDriver.switchTo().frame(captchaElement);
      waitUntilPropertyHasAttributeToBe(VERIFIED_BOX_XPATH,"aria-checked", "true" );
      WebElement captchaCheckBox = webDriver.findElement(By.xpath(VERIFIED_BOX_XPATH));
      String boxState = captchaCheckBox.getAttribute("aria-checked");
      webDriver.switchTo().defaultContent();
      return boxState.equals("true");
   }
   private void solveCaptcha(WebElement captchaRectangleBox) throws IOException, ExecutionException, InterruptedException {
      webDriver.switchTo().frame(captchaRectangleBox);

      String captchaForDeafButtonXpath = "//*[@id=\"recaptcha-audio-button\"]";
      waitUntilIsUsable(captchaForDeafButtonXpath);

      WebElement audioButton = webDriver.findElement(By.xpath(captchaForDeafButtonXpath));
      audioButton.click();

      String audioUrlXpath = "//*[@id=\"rc-audio\"]/div[7]/a";
      waitUntilIsUsable(audioUrlXpath);

      String audioUrl = webDriver.findElement(By.xpath(audioUrlXpath))
              .getAttribute("href");

      String transcribedAudio = getAudioText(audioUrl);

     String inputAudioXpath = "//*[@id=\"audio-response\"]";
     webDriver.findElement(By.xpath(inputAudioXpath)).sendKeys(transcribedAudio);

     finishCaptcha();
   }

   private String getAudioText(String audioUrl) throws IOException, ExecutionException, InterruptedException {
      AudioParser audioParser = new AudioParser();
      audioParser.setAudioUrl(audioUrl);
      return audioParser.transcribeAudio().getTranscribedAudio();
   }

   /**
    * Usable means anything, from checking the element attributes to clicking on it
    *<br> Sometimes if the element you are trying to use is refreshing
    * it gives an error, this method waits until the element is fully refreshed
    * */
   private void waitUntilIsUsable(String elementXpath){
      try {
      new WebDriverWait(webDriver, Duration.ofSeconds(4))
              .until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath))));
      }catch (NoSuchElementException | TimeoutException e){
         logger.log(Level.SEVERE, e.getMessage().concat(" Probably caused by the captcha blocking your ip address "));
      }
   }
   private void waitUntilPropertyHasAttributeToBe(String elementXpath, String attribute, String valueToBe){
      try {
         new WebDriverWait(webDriver, Duration.ofSeconds(4))
                 .until(ExpectedConditions.attributeToBe(By.xpath(elementXpath), attribute, valueToBe));
      }catch (NoSuchElementException | TimeoutException e){
         logger.log(Level.SEVERE, e.getMessage().concat(" Probably caused by the captcha blocking your ip address "));
      }
   }

   private void finishCaptcha() throws InterruptedException {
      String recaptchaVerifyButtonXpath = "//*[@id=\"recaptcha-verify-button\"]";
      webDriver.findElement(By.xpath(recaptchaVerifyButtonXpath)).click();
      webDriver.switchTo().defaultContent();
      waitUntilIsUsable(CAPTCHA_CLICKABLE_BOX_XPATH);
      if (!isCaptchaCompleted(webDriver.findElement(By.xpath(CAPTCHA_CLICKABLE_BOX_XPATH)))) {
         breakCaptcha();
      }
   }

}
