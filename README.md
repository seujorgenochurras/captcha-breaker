# captcha-breaker
A Google Selenium Captcha Breaker that I'll use on future projects.

### How it works?
It transcribes the captcha for deafs audio using Assembly AI
Note that the application is not able to get the captcha token that is released when the captcha is 
finished

### How to use it?
The application is dividided in 2 parts, the transcriber and the selenium
If you want to break any captcha on the site simply use an instance of the CaptchaBreaker class
or use the code snippet bellow.
    
```java
Webdriver driver = new ChromeDriver();
driver.get("https://www.google.com/recaptcha/api2/demo"); 
 
CaptchaBreaker captchaBreaker = new CaptchaBreaker(driver); 
captchaBreaker.breakCaptcha();
```

As I said, the lib is divided in 2 parts, the audio transcriber and the selenium.
If you want to use the audio transcriber, you can use the AudioParser class

```java
 AudioParser audioParser = new AudioParser();
      audioParser.setAudioUrl("https://s3-us-west-2.amazonaws.com/blog.assemblyai.com/audio/8-7-2018-post/7510.mp3");
      AssemblyResponseDTO assemblyResponseDTO = audioParser.transcribeAudio();
      System.out.println(assemblyResponseDTO.getTranscribedAudio());
```

### Is it good?
I think good is a strong word for this project.
It takes about 15 seconds to break each captcha
if this delay gets too much I'll change the way to transcribe the audio.
btw it takes that long mostly because of the api that I'm using 
to trasncribe the audio.
