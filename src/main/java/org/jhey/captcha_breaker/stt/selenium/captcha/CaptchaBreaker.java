package org.jhey.captcha_breaker.stt.selenium.captcha;

import org.jhey.captcha_breaker.api.AudioParser;
import org.jhey.captcha_breaker.stt.html.elements.captcha.Captcha;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.BlindChallenge;
import org.jhey.captcha_breaker.stt.html.elements.captcha.challenge.CaptchaChallengesBox;
import org.jhey.captcha_breaker.stt.html.elements.captcha.ui.CaptchaSquareElement;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jhey.captcha_breaker.stt.html.elements.document.DocumentUtils.waitToBeClickable;
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

   private final String assemblyAiToken;

   public CaptchaBreaker(Captcha captcha, String assemblyAiToken) {
      this.captcha = captcha;
      this.assemblyAiToken = assemblyAiToken;
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
      }catch (IOException | ExecutionException | InterruptedException e){
         logger.log(Level.SEVERE, e.getMessage());
         Thread.currentThread().interrupt();
      } catch (NoSuchElementException e){
         logger.log(Level.SEVERE, "No such element occurred, probably caused by captcha blocking your IP address");
      }
   }
   
   private boolean isCaptchaCompleted(){
     waitToBeClickable(captcha.getCheckbox().toWebElement(), webDriver, Duration.ofSeconds(waitToBeStalenessSeconds));
     return captcha.getCheckbox().isVerified();
   }
   private void solveCaptcha() throws IOException, ExecutionException, InterruptedException {
      waitToBeStalenessOf(captcha.getCaptchaSquareElement().toWebElement(),
              webDriver,
              Duration.ofSeconds(waitToBeStalenessSeconds));

      captcha.generateChallengeElement();
      CaptchaChallengesBox captchaChallengesBox = captcha.getCaptchaChallengeElement();

      captchaChallengesBox.openBlindChallenge();

      BlindChallenge blindChallenge = captchaChallengesBox.getBlindChallenge();

      waitToBeStalenessOf(blindChallenge.toWebElement(),
              webDriver,
              Duration.ofSeconds(waitToBeStalenessSeconds));

      String transcribedAudio = getAudioText(blindChallenge.getAudioURL().toString(), assemblyAiToken);

      blindChallenge.getInputAudio().sendKeys(transcribedAudio);
      finishCaptcha();
   }

   private static String getAudioText(String audioUrl, String assemblyAiToken) throws IOException, ExecutionException, InterruptedException {
      AudioParser audioParser = new AudioParser(assemblyAiToken);
      audioParser.setAudioUrl(audioUrl);
      return audioParser.transcribeAudio().getTranscribedAudio();
   }

   private void finishCaptcha(){
      captcha.getCaptchaChallengeElement().getBlindChallenge().getSubmitButton().click();

      waitToBeStalenessOf(captcha.getCaptchaSquareElement().toWebElement(),
              webDriver,
              Duration.ofSeconds(2));

      if (!isCaptchaCompleted()) {
         breakCaptcha();
      }
   }
}
