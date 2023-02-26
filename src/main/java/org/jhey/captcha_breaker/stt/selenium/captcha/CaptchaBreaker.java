package org.jhey.captcha_breaker.stt.selenium.captcha;

import org.jhey.captcha_breaker.api.AudioParser;
import org.jhey.captcha_breaker.stt.html.elements.Captcha;
import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaSquareElement;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.CaptchaChallengesBox;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.DeafChallenge;
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
   private static final Logger logger = Logger.getLogger(CaptchaBreaker.class.getName());
   private final Captcha captcha;

   public CaptchaBreaker(Captcha captcha) {
      this.captcha = captcha;
   }

   /**
    * Method used to find and break any captcha that is on the site.
    *
    * @see AudioParser
    * */
   public void breakCaptcha() {
      CaptchaSquareElement captchaBoxElement = captcha.getCaptchaSquareElement();
      captchaBoxElement.click();

      if (isCaptchaCompleted()) return;

      try {
         solveCaptcha();
      }catch (IOException | ExecutionException e){
         logger.log(Level.SEVERE, e.getMessage());
      }catch (InterruptedException e){
         logger.log(Level.SEVERE, e.getMessage());
         Thread.currentThread().interrupt();
      }
   }
   
   private boolean isCaptchaCompleted(){
      System.out.println("DID IT AND IS " + captcha.getCheckbox().isVerified());
     waitToBeStaleness(captcha.getCheckbox().toWebElement());
     return captcha.getCheckbox().isVerified();
   }

   private void solveCaptcha() throws IOException, ExecutionException, InterruptedException {
      Thread.sleep(4000);
      captcha.generateChallengeElement();
      CaptchaChallengesBox captchaChallengesBox = captcha.getCaptchaChallengeElement();

      captchaChallengesBox.openDeafChallenge();

      DeafChallenge deafChallenge = captchaChallengesBox.getDeafChallenge();

      waitToBeStaleness(captcha.getCaptchaChallengeElement().getDeafChallenge().toWebElement());

      String transcribedAudio = getAudioText(deafChallenge.getAudioURL().toString());
        deafChallenge.getInputAudio().sendKeys(transcribedAudio);
        finishCaptcha();
   }

   private static String getAudioText(String audioUrl) throws IOException, ExecutionException, InterruptedException {
      AudioParser audioParser = new AudioParser();
      audioParser.setAudioUrl(audioUrl);
      return audioParser.transcribeAudio().getTranscribedAudio();
   }

   /**
    * Sometimes if the element you are trying to use is refreshing
    * it gives an error, this method waits until the element is fully refreshed
    * */
   private void waitToBeStaleness(WebElement webElement) {
      try {
      new WebDriverWait(captcha.getWebDriver(), Duration.ofSeconds(2))
              .until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(webElement)));
      }
      catch (NoSuchElementException | TimeoutException e){
         logger.log(Level.WARNING, e.getMessage().concat(" Probably caused by the captcha blocking your ip address "));
      }
   }
   private void waitToBeClickable(WebElement webElement){
      WebDriver webDriver = captcha.getWebDriver();
      try {
         new WebDriverWait(webDriver, Duration.ofSeconds(4))
                 .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(webElement)));
      }
      catch (NoSuchElementException | TimeoutException e){
         logger.log(Level.WARNING, e.getMessage().concat(" Probably caused by the captcha blocking your ip address "));
      }
   }
   private void finishCaptcha(){
      captcha.getCaptchaChallengeElement().getDeafChallenge().getSubmitButton().click();
      waitToBeStaleness(captcha.getCaptchaSquareElement().toWebElement());
      if (!isCaptchaCompleted()) {
         breakCaptcha();
      }
   }
}
