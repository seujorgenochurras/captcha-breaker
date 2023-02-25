package org.jhey.captcha_breaker.stt.html.elements.captcha.service;

import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaCheckbox;
import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaSquareElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;

public class CheckboxService {
   private final WebDriver webDriver;

   public CheckboxService(WebDriver webDriver) {
      this.webDriver = webDriver;
   }
   public void updateCheckbox(CaptchaCheckbox checkbox, @Nonnull WebElement newCheckBox){
      switchToCaptchaFrame();
      checkbox.updateElement(newCheckBox);
      switchToDefault();
   }
   public void updateCheckbox(CaptchaCheckbox checkbox){
      switchToCaptchaFrame();
      checkbox.updateElement(webDriver.findElement(By.xpath(CaptchaCheckbox.XPATH)));
      switchToDefault();
   }
   private void switchToCaptchaFrame(){
      webDriver.switchTo().frame(webDriver.findElement(By.xpath(CaptchaSquareElement.XPATH)));
   }
   private void switchToDefault(){
      webDriver.switchTo().defaultContent();
   }
}
