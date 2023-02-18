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
### Is it good?
    I think good is a strong word for this project.
    It takes about 15 seconds to break each captcha
    if this delay gets too much I'll change the way to transcribe the audio.
    btw it takes that long mostly because of the api that I'm using 
    to trasncribe the audio.
