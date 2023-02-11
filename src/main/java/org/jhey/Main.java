package org.jhey;


import org.openqa.selenium.By;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.network.Network;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class Main {
   static ChromeDriver driver;
   static DevTools chromeDevTools;
   static ChromeDriverService service;
   public static void main(String[] args) throws InterruptedException, IOException {
      System.setProperty("webdriver.chrome.driver", "src/drive/chromedriver.exe");


      ChromeOptions options = new ChromeOptions();
      Map<String, Object> perfLogPrefs = new HashMap<>();
      perfLogPrefs.put("enableNetwork", true);
      options.setExperimentalOption("perfLoggingPrefs", perfLogPrefs);
         options.addArguments("--disable-extensions");
      options.addArguments("--disable-popup-blocking");
      options.addArguments("--disable-translate");
      options.addArguments("--disable-notifications");
     // options.setExperimentalOption("debuggerAddress", "localhost:9222");

      LoggingPreferences logPrefs = new LoggingPreferences();
      logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

      options.setCapability("goog:loggingPrefs", logPrefs);
      createService();
      driver = new ChromeDriver(options);
      initAndEnableChromeDevtools();
       chromeDevTools.addListener(Network.loadingFinished(), requestIntercepted->{
         System.out.println("Body: " + chromeDevTools.send(Network.getResponseBody(requestIntercepted.getRequestId())).getBody());
      });


      driver.get("https://nsa.cps.sp.gov.br/");


      //driver.navigate().to("devtools://devtools/bundled/devtools_app.html?ws=localhost:9222/devtools/page/1");

//      ((JavascriptExecutor) driver).executeScript("window.open()");
//      ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
//
//     // driver.switchTo().window(tabs.get(1));
//
//      // Access the Network Tab in the DevTools panel
//      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//      wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Network']")));
//      driver.findElement(By.xpath("//div[text()='Network']")).click();
//      wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Doc']")));


      driver.findElement(By.xpath("//*[@id=\"ctrlGoogleReCaptcha\"]/div/div/div/iframe")).click();
      Thread.sleep(10000);

      driver.quit();
      }


      static void createService() throws IOException {
         service = new ChromeDriverService.Builder()
                 .usingDriverExecutable(new File("src\\drive\\chromedriver.exe"))
                 .usingAnyFreePort()
                 .build();
         service.start();
      }
      static void printLog(String type){
         List<LogEntry> logEntries = driver.manage().logs().get(type).getAll();
         logEntries.forEach(logEntry -> System.out.println(logEntry.toString()));
      }


      static void initAndEnableChromeDevtools() {
         chromeDevTools = driver.getDevTools();
         chromeDevTools.createSession();
         chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));


      }
   }