package org.jhey.captcha_breaker.stt.html.elements.document;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentUtils {
   private static final Logger logger = Logger.getLogger(DocumentUtils.class.getName());
   private DocumentUtils(){
      //Utility class
       }

   /**
    * Sometimes if the element you are trying to use is refreshing
    * it gives an error, this method waits until the element is fully refreshed
    * */
   public static void waitToBeStalenessOf(WebElement webElement, WebDriver webDriver, Duration duration) {
      try {
         new WebDriverWait(webDriver, duration)
                 .until(ExpectedConditions.stalenessOf(webElement));
      } catch (NoSuchElementException e) {
         logger.log(Level.WARNING, "Did not find the element ");
      } catch (TimeoutException e) {
         logger.log(Level.WARNING, "Timeout");
      }
   }

   public static void waitExistenceOf(WebElement webElement, WebDriver webDriver, Duration duration){
      try {
         new WebDriverWait(webDriver, duration)
                 .until(ExpectedConditions.stalenessOf(webElement));
      }
      catch (NoSuchElementException | TimeoutException e){
         logger.log(Level.WARNING, " Probably caused by the captcha blocking your ip address ");
      }
   }
}
