package org.jhey.captcha_breaker.stt.html.elements.captcha.service;

import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaCheckbox;
import org.jhey.captcha_breaker.stt.html.elements.captcha.CaptchaElement;
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
      checkbox.updateRawWebElement(newCheckBox);
      switchToDefault();
   }
   public void updateCheckbox(CaptchaCheckbox checkbox){
      switchToCaptchaFrame();
      checkbox.updateRawWebElement(webDriver.findElement(By.xpath(CaptchaCheckbox.XPATH)));
      switchToDefault();
   }
   private void switchToCaptchaFrame(){

      webDriver.switchTo().frame(webDriver.findElement(By.xpath(CaptchaElement.XPATH)));
   }
   private void switchToDefault(){
      webDriver.switchTo().defaultContent();
   }
}
