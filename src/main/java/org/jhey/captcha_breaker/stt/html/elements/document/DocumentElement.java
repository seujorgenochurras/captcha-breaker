package org.jhey.captcha_breaker.stt.html.elements.document;

import org.jhey.captcha_breaker.stt.html.elements.CustomElement;
import org.openqa.selenium.*;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the #document that is inside the Iframe tag
 */
public abstract class DocumentElement extends CustomElement {
   private static final Logger logger = Logger.getLogger(DocumentElement.class.getName());

   protected DocumentElement(WebElement webElement, WebDriver webDriver) {
      super(webElement, webDriver);
   }

   /**
    * This should return the Iframe that needs to be accessed to get this Element
    * */
   protected abstract String getIframeXpath();

   @Override
   public void click() {
      voidedHandleStaleElement(super::click);
   }

   @Override
   public void submit() {
      voidedHandleStaleElement(super::submit);
   }

   @Override
   public void sendKeys(CharSequence... keysToSend) {
      voidedHandleStaleElement(() -> super.sendKeys(keysToSend));
   }

   @Override
   public void clear() {
      voidedHandleStaleElement(super::clear);
   }

   @Override
   public String getTagName() {
      return handleStaleElement(super::getTagName);
   }

   @Override
   public String getAttribute(String name) {
      return handleStaleElement(() -> super.getAttribute(name));
   }

   @Override
   public boolean isSelected() {
      return handleStaleElement(super::isSelected);
   }

   @Override
   public boolean isEnabled() {
      return handleStaleElement(super::isEnabled);
   }

   @Override
   public String getText() {
      return handleStaleElement(super::getText);
   }

   @Override
   public List<WebElement> findElements(By by) {
      return handleStaleElement(() -> super.findElements(by));
   }

   @Override
   public WebElement findElement(By by) {
      return handleStaleElement(() -> super.findElement(by));
   }

   @Override
   public boolean isDisplayed() {
      return handleStaleElement(super::isDisplayed);
   }

   @Override
   public Point getLocation() {
      return handleStaleElement(super::getLocation);
   }

   @Override
   public Dimension getSize() {
      return handleStaleElement(super::getSize);
   }

   @Override
   public Rectangle getRect() {
      return handleStaleElement(super::getRect);
   }

   @Override
   public String getCssValue(String propertyName) {
      return handleStaleElement(() -> super.getCssValue(propertyName));
   }

   @Override
   public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
      return handleStaleElement(() -> super.getScreenshotAs(target));
   }

   private <T> T handleStaleElement(Callable<T> callable){
      T result;
      try {
          result = handleCall(callable);
      } catch (StaleElementReferenceException e) {
         switchToFrame();
          result = handleCall(callable);
         switchBack();
      }
      return result;
   }
   private void voidedHandleStaleElement(Runnable runnable){
      try {
         runnable.run();
      } catch (StaleElementReferenceException e){
         switchToFrame();
         runnable.run();
         switchBack();
      }
   }
   private <T> T handleCall(Callable<T> callable){
      try{
         return callable.call();
      } catch (Exception e) {
         logger.log(Level.SEVERE, "An error happen when calling ".concat(super.getTagName()));
         logger.log(Level.SEVERE, e.getMessage());
         return null;
      }
   }
   private void switchToFrame(){
      webDriver.switchTo().defaultContent();
      try {
         webDriver.switchTo().frame(webDriver.findElement(By.xpath(getIframeXpath())));
      }catch (StaleElementReferenceException e){
         logger.log(Level.SEVERE, " Couldn't click in the webElement ".concat(this.getTagName()));
         logger.log(Level.SEVERE, e.getMessage());
      }
   }
   private void switchBack(){
      webDriver.switchTo().defaultContent();
   }
}
