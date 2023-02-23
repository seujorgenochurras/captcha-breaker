package org.jhey.captcha_breaker.stt.html.elements.captcha;

import org.jhey.captcha_breaker.stt.html.elements.CustomElement;
import org.openqa.selenium.WebElement;


public class CaptchaElement extends CustomElement {
    public static final String XPATH = "//iframe[starts-with(@title, 'reCAPTCHA')]";
    public CaptchaElement(WebElement element) {
        super(element);
    }
}
