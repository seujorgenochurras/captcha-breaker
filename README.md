# captcha-breaker
A Google Selenium Captcha Breaker that I'll use on future projects.

### How it works?
It transcribes the captcha for deaf audio using Assembly AI
Note that the application is not able to get the captcha token that is released when the captcha is 
finished

### How to use it?
First, checkout my [Selenium Custom Elements](https://github.com/seujorgenochurras/captcha-breaker.git) to get all the needed dependencies. <br>
The application is divided in 2 parts, the transcriber and the selenium
If you want to break any captcha on a page the first thing you have to set is the 
captcha Element.
The captcha element can be automatically found by using the `CaptchaFinder.findCaptchaElement()` method,
note that the method needs your WebDriver as a parameter.
<br>
After defining the captcha element you can simply call the `solveCaptcha()` method
look at the example below.

    
```java
   WebDriver driver = new ChromeDriver();
   driver.get("https://www.google.com/recaptcha/api2/demo");
   
   
   Captcha captcha = CaptchaFinder.findCaptchaElement(driver);
   captcha.solveCaptcha();
```
### How do I implement it?
Well this is one of my major problems, right now I have no idea on 
how to create a dependency to the world,
I'll probably study a bit more about Gradlew and Maven.
<br>    I'm so sorry you have to hear that, but I also don't know how
to implement this on the code without Gradlew or Maven.

### Is it good?
I think good is a strong word for this project.
It takes about 15 seconds to break each captcha
if this delay gets too much I'll change the way to transcribe the audio.
btw it takes that long mostly because of the api that I'm using 
to transcribe the audio.
