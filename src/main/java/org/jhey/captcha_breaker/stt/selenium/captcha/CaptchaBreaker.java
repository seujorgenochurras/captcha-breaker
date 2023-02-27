package org.jhey.captcha_breaker.stt.selenium.captcha;

import org.jhey.captcha_breaker.api.AudioParser;
import org.jhey.captcha_breaker.stt.html.elements.captcha.Captcha;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.CaptchaChallengesBox;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.DeafChallenge;
import org.jhey.captcha_breaker.stt.html.elements.captcha.ui.CaptchaSquareElement;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jhey.captcha_breaker.stt.html.elements.document.DocumentUtils.waitToBeStalenessOf;

/**
 * Finds and breaks any captcha on the site.
 * <b>Warning</b> as it uses a shallow copy of your {@code WebDriver}
 * it is not recommended to do selenium with multi-threads with it.
 * It's likely to break the code and give an unwanted error
 * */
public class CaptchaBreaker {
   private static final Logger logger = Logger.getLogger(CaptchaBreaker.class.getName());
   private final Captcha captcha;
   private final WebDriver webDriver;


   public CaptchaBreaker(Captcha captcha) {
      this.captcha = captcha;
      this.webDriver = captcha.getWebDriver();
   }

   private static final byte waitToBeStalenessSeconds = 1;

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
      } catch (NoSuchElementException e){
         logger.log(Level.SEVERE, "No such element occurred, probably caused by captcha blocking your IP address");
      }
   }
   
   private boolean isCaptchaCompleted(){
     waitToBeStalenessOf(captcha.getCheckbox().toWebElement(), webDriver, Duration.ofSeconds(waitToBeStalenessSeconds));
     return captcha.getCheckbox().isVerified();
   }

   private void solveCaptcha() throws IOException, ExecutionException, InterruptedException {
      waitToBeStalenessOf(captcha.getCaptchaSquareElement().toWebElement(), webDriver, Duration.ofSeconds(waitToBeStalenessSeconds));

      captcha.generateChallengeElement();
      CaptchaChallengesBox captchaChallengesBox = captcha.getCaptchaChallengeElement();

      captchaChallengesBox.openDeafChallenge();

      DeafChallenge deafChallenge = captchaChallengesBox.getDeafChallenge();

      waitToBeStalenessOf(captcha.getCaptchaChallengeElement().getDeafChallenge().toWebElement(),
              webDriver,
              Duration.ofSeconds(waitToBeStalenessSeconds));

      String transcribedAudio = getAudioText(deafChallenge.getAudioURL().toString());
        deafChallenge.getInputAudio().sendKeys(transcribedAudio);
        finishCaptcha();
   }

   private static String getAudioText(String audioUrl) throws IOException, ExecutionException, InterruptedException {
      AudioParser audioParser = new AudioParser();
      audioParser.setAudioUrl(audioUrl);
      return audioParser.transcribeAudio().getTranscribedAudio();
   }

   private void finishCaptcha(){
      captcha.getCaptchaChallengeElement().getDeafChallenge().getSubmitButton().click();

      waitToBeStalenessOf(captcha.getCaptchaSquareElement().toWebElement(),
              webDriver,
              Duration.ofSeconds(2));

      if (!isCaptchaCompleted()) {
         breakCaptcha();
      }
   }
}
