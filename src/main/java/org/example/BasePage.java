package org.example;

import org.example.utils.ConfigReader;
import org.example.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getLong("wait")));
        PageFactory.initElements(driver, this);

    }
    public void sendKeys(WebElement locator, String text) {
        locator.clear();
        Utils.logInfo("Locator [" + locator + "] is cleared");
        locator.sendKeys(text);
        Utils.logInfo("Send KEy: " + text);
    }

    public void click(WebElement locator) {
        locator.click();
        Utils.logInfo("click to: " + locator);
    }

    public String getText(WebElement locator) {
        Utils.logInfo("returned Text: " + locator.getText());
        return locator.getText();
    }

    public String getCssValue(WebElement locator, String propertyName) {
        return locator.getCssValue(propertyName);
    }

}
