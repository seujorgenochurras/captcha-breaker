package org.jhey.captcha_breaker.stt.html.elements;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class only provides custom methods for elements
 * */
public abstract class CustomElement extends Element {
   protected CustomElement(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);
   }
   public WebElement toWebElement(){
       return this.webElement;
   }
}
