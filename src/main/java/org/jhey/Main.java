package org.jhey;


import org.jhey.captchaBreaker.speech2text.AudioParser;
import org.jhey.captchaBreaker.speech2text.api.responseBodyHandler.DTO.AssemblyResponseDTO;

import java.io.IOException;

public class Main {
//   static ChromeDriver driver;
//   static DevTools chromeDevTools;
//   static ChromeDriverService service;
   public static void main(String[] args) throws InterruptedException, IOException {
      AudioParser parser = new AudioParser();
      parser.setAudioUrl("https://www.google.com/recaptcha/api2/payload/audio.mp3?p=06AFY_a8VZpcxB0y5Fq4x7QMPuujPrvNR17GAdrTvNHYrgQtstAWGeEGkjPzaHCfoPX6C6pbxEtm2GkcDjwa_QTXLc__40lvqCND2hF2e9K8ujbGSZeFcRD3tV5JSPBRIBcoZQKED6_V5bFcS8OvQYrGhuunZAuuM9xMiBf8qc116bBnVcMhDUscnmlQsEHxdNPGqaD566iec2&k=6Le-wvkSAAAAAPBMRTvw0Q4Muexq9bi0DJwx_mJ-");
      AssemblyResponseDTO assemblyResponseDTO = parser.transcribeAudio();
      System.out.println(assemblyResponseDTO.getRequestState());
   }
//      System.setProperty("webdriver.chrome.driver", "src/drive/chromedriver.exe");
//      ChromeOptions options = new ChromeOptions();
//
//      LoggingPreferences logPrefs = new LoggingPreferences();
//      logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
//      options.setCapability("goog:loggingPrefs", logPrefs);
//      options.addArguments("--disable-extensions");
//      options.addArguments("--disable-popup-blocking");
//      options.addArguments("--incognito");
//      options.addArguments("--start-maximized");
//      options.addArguments("--headless");
//
//      createService();
//      driver = new ChromeDriver(options);
//      initAndEnableChromeDevtools();
//
//      driver.get("https://nsa.cps.sp.gov.br/");
//      fillAccountInfo();
//      breakCaptcha();
//      attemptLogin();
//      chromeDevTools.send(Network.getAllCookies()).stream().filter(cookie -> cookie.getName().equals("NSA_OnLine_SessionId"))
//              .forEach(cookie -> {
//                 System.out.println("Name : " + cookie.getName());
//                 System.out.println("Value : " +  cookie.getValue());
//              });
//
//   }
//      static void attemptLogin(){
//         WebElement inputButton = driver.findElement(By.xpath("//*[@id=\"btnEntrar\"]"));
//         inputButton.click();
//      }
//      static void breakCaptcha() throws InterruptedException {
//         driver.findElement(By.xpath("//*[@id=\"ctrlGoogleReCaptcha\"]/div/div/div/iframe")).click();
//         Thread.sleep(10000);
//      }
//      static void fillAccountInfo(){
//         Dotenv dotenv = Dotenv.load();
//         String etecId = dotenv.get("ETEC_ID");
//         String studentId = dotenv.get("STUDENT_ID");
//         String studentPass = dotenv.get("STUDENT_PASS");
//
//         WebElement inputEtecID = driver.findElement(By.xpath("//*[@id=\"txtCod\"]"));
//         WebElement inputStudentID = driver.findElement(By.xpath("//*[@id=\"txtlogin\"]"));
//         WebElement inputStudentPass = driver.findElement(By.xpath("//*[@id=\"txtSenha\"]"));
//
//         inputEtecID.sendKeys(etecId);
//         inputStudentID.sendKeys(studentId);
//         inputStudentPass.sendKeys(studentPass);
//
//   }
//      static void createService() throws IOException {
//         service = new ChromeDriverService.Builder()
//                 .usingDriverExecutable(new File("src\\drive\\chromedriver.exe"))
//                 .usingAnyFreePort()
//                 .build();
//         service.start();
//      }
//
//      static void initAndEnableChromeDevtools() {
//         chromeDevTools = driver.getDevTools();
//         chromeDevTools.createSession();
//         chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//      }
   }