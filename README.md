# captcha-breaker
A Google Selenium Captcha Breaker that I'll use on future projects.

### How it works?
It transcribes the captcha for deaf audio using Assembly AI.
<br>
Note that the application is not able to get the captcha token that is released when the captcha is 
finished

### How to use it?
First, checkout the AssemblyAi API docs which can be found [here](https://www.assemblyai.com/docs#quickstart) <br>
you need to create an account and get a token<br><br>
The application is divided in 2 parts, the transcriber and the selenium. <br>
If you want to break any captcha on a page the first thing you have to set is the 
captcha Element.
<br><br>The captcha element can be automatically found by using the method `CaptchaFinder.findCaptchaElement()`,
note that the method needs your WebDriver as a parameter. You can also force it to get a specific captcha xpath, but that's not recomended
<br>
After defining the captcha element you can simply call the method `solveCaptcha`.
<br>
look at the example below.

    
```java
   WebDriver driver = new ChromeDriver();
   driver.get("https://www.google.com/recaptcha/api2/demo");
   
   Captcha captcha = CaptchaFinder.findCaptchaElement(driver);
   captcha.solveCaptcha("ASSEMBLY-AI-TOKEN-HERE");
```
### How do I implement it?
    Gralde:
 
     implementation 'io.github.seujorgenochurras:selenium-captcha-breaker:1.2.0'
_

```
   Maven:
   <dependency>
       <groupId>io.github.seujorgenochurras</groupId>
       <artifactId>selenium-captcha-breaker</artifactId>
       <version>1.2.0</version>
    </dependency>
   ```

### Is it good?
I think good is a strong word for this project.
It takes about 15 seconds to break each captcha
if this delay gets too much I'll change the way to transcribe the audio.
btw it takes that long mostly because of the api that I'm using 
to transcribe the audio.
