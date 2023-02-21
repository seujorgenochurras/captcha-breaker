package org.jhey.captcha_breaker.stt.html.elements.captcha;

import org.openqa.selenium.WebElement;


public interface CaptchaElement extends WebElement {
    String XPATH = "//iframe[starts-with(@title, 'reCAPTCHA')]";

}
