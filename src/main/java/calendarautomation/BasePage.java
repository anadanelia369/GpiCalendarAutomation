package calendarautomation;

import calendarautomation.utils.ConfigReader;
import calendarautomation.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {

    private static final Logger logger = LogManager.getLogger(BasePage.class);

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getLong("wait")));
        PageFactory.initElements(driver, this);
    }

    public void click(WebElement locator) {
        locator.click();
        Utils.logInfo("click to: " + locator);
        logger.info("Clicked on locator: {}", locator);
    }

    public String getText(WebElement locator) {
        String text = locator.getText();
        Utils.logInfo("returned Text: " + text);
        logger.debug("Retrieved text '{}' from locator: {}", text, locator);
        return text;
    }

}