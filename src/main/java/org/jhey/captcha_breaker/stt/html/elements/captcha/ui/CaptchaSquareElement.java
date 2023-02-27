package org.jhey.captcha_breaker.stt.html.elements.captcha.ui;

import org.jhey.custom.element.CustomElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class CaptchaSquareElement extends CustomElement {
    public static final String XPATH = "//iframe[starts-with(@title, 'reCAPTCHA')]";
    public CaptchaSquareElement(WebElement element, WebDriver webDriver) {
        super(element, webDriver);
    }

    @Override
    public String getXpath() {
        return XPATH;
    }

}
