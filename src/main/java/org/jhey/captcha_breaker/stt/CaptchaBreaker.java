package org.jhey.captcha_breaker.stt;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class CaptchaBreaker {
   private final WebDriver webDriver;
   private static final String CAPTCHA_XPATH = "//iframe[starts-with(@title, 'reCAPTCHA')]";
   private static final String VERIFIED_BOX_XPATH = "//*[@id='recaptcha-anchor']";

   public CaptchaBreaker(WebDriver webDriver) {
      this.webDriver = webDriver;
   }
   public void breakCaptcha() throws IOException, ExecutionException, InterruptedException {
     WebElement captchaElement = webDriver.findElement(By.xpath(CAPTCHA_XPATH));
     captchaElement.click();
     if (isCaptchaCompleted(captchaElement)) return;

     String biggerBoxXpath = "//iframe[starts-with(@src, 'https://www.google.com/recaptcha/api2/bframe?')]";
     webDriver.switchTo().defaultContent();
     WebElement captchaBiggerBox = webDriver.findElement(By.xpath(biggerBoxXpath));

     solveCaptcha(captchaBiggerBox);
   }
   private boolean isCaptchaCompleted(WebElement captchaElement){
      webDriver.switchTo().frame(captchaElement);
      WebElement captchaCheckBox = webDriver.findElement(By.xpath(VERIFIED_BOX_XPATH));
      String boxState = captchaCheckBox.getAttribute("aria-checked");
      return boxState.equals("true");
   }
   private void solveCaptcha(WebElement captchaRectangleBox) throws IOException, ExecutionException, InterruptedException {
      String audioButtonXpath = "//*[@id=\"recaptcha-audio-button\"]";
      webDriver.switchTo().frame(captchaRectangleBox);
      Thread.sleep(723); //random numbers, it's because of a theory that i have about google's bot patch
      WebElement audioButton = webDriver.findElement(By.xpath(audioButtonXpath));
      audioButton.click();

      Thread.sleep(765);
      String audioUrlXpath = "//*[@id=\"rc-audio\"]/div[7]/a";
      String audioUrl = webDriver.findElement(By.xpath(audioUrlXpath))
              .getAttribute("href");
     String transcribedAudio = getAudioText(audioUrl);

     String inputAudioXpath = "//*[@id=\"audio-response\"]";
     Thread.sleep(432);
     webDriver.findElement(By.xpath(inputAudioXpath)).sendKeys(transcribedAudio);

     webDriver.findElement(By.xpath("//*[@id=\"recaptcha-verify-button\"]")).click();
      webDriver.switchTo().defaultContent();
      if (!isCaptchaCompleted(webDriver.findElement(By.xpath(CAPTCHA_XPATH)))) {
         breakCaptcha();
      }
   }
   private String getAudioText(String audioUrl) throws IOException, ExecutionException, InterruptedException {
      AudioParser audioParser = new AudioParser();
      audioParser.setAudioUrl(audioUrl);
      return audioParser.transcribeAudio().getTranscribedAudio();
   }

}
