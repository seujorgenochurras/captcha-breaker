package org.jhey;


import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v109.network.Network;

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

      LoggingPreferences logPrefs = new LoggingPreferences();
      logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
      options.setCapability("goog:loggingPrefs", logPrefs);
      options.addArguments("--disable-extensions");
      options.addArguments("--disable-popup-blocking");
      options.addArguments("--incognito");
      options.addArguments("--start-maximized");
      options.addArguments("--headless");

      createService();
      driver = new ChromeDriver(options);
      initAndEnableChromeDevtools();

      driver.get("https://nsa.cps.sp.gov.br/");
      fillAccountInfo();
      breakCaptcha();
      attemptLogin();
      chromeDevTools.send(Network.getAllCookies()).stream().filter(cookie -> cookie.getName().equals("NSA_OnLine_SessionId"))
              .forEach(cookie -> {
                 System.out.println("Name : " + cookie.getName());
                 System.out.println("Value : " +  cookie.getValue());
              });

   }
      static void attemptLogin(){
         WebElement inputButton = driver.findElement(By.xpath("//*[@id=\"btnEntrar\"]"));
         inputButton.click();
      }
      static void breakCaptcha() throws InterruptedException {
         driver.findElement(By.xpath("//*[@id=\"ctrlGoogleReCaptcha\"]/div/div/div/iframe")).click();
         Thread.sleep(10000);
      }
      static void fillAccountInfo(){
         Dotenv dotenv = Dotenv.load();
         String etecId = dotenv.get("ETEC_ID");
         String studentId = dotenv.get("STUDENT_ID");
         String studentPass = dotenv.get("STUDENT_PASS");

         WebElement inputEtecID = driver.findElement(By.xpath("//*[@id=\"txtCod\"]"));
         WebElement inputStudentID = driver.findElement(By.xpath("//*[@id=\"txtlogin\"]"));
         WebElement inputStudentPass = driver.findElement(By.xpath("//*[@id=\"txtSenha\"]"));

         inputEtecID.sendKeys(etecId);
         inputStudentID.sendKeys(studentId);
         inputStudentPass.sendKeys(studentPass);

   }
      static void createService() throws IOException {
         service = new ChromeDriverService.Builder()
                 .usingDriverExecutable(new File("src\\drive\\chromedriver.exe"))
                 .usingAnyFreePort()
                 .build();
         service.start();
      }

      static void initAndEnableChromeDevtools() {
         chromeDevTools = driver.getDevTools();
         chromeDevTools.createSession();
         chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
      }
   }