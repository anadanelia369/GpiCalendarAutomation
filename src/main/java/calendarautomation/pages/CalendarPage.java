package calendarautomation.pages;

import calendarautomation.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CalendarPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(CalendarPage.class);

    @FindBy(id = "gpi-date-range-policy-input")
    private WebElement dateRangeInput;

    @FindBy(xpath = "//*[contains(@class, 'mg-input-error-message')]")
    private WebElement validationMessage;

    @FindBy(xpath = "//button[contains(@class, 'mat-calendar-period-button')]//span[contains(@class, 'mdc-button__label')]")
    private WebElement calendarPeriodLabel;

    @FindBy(xpath = "//*[contains(@class, 'mg-wizard-nav-button') and contains(@class, 'primary')]")
    private WebElement continueButton;

    @FindBy(xpath = "//img[@alt='close icon']")
    private WebElement closeModalButton;

    @FindBy(xpath = "//button[@aria-label='Next month']")
    private WebElement nextMonthButton;

    public CalendarPage(WebDriver driver) {
        super(driver);
    }

    private By dateLocator(LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return By.xpath("//button[@aria-label='" + formattedDate + "']");
    }

    public void openCalendar() {
        waitForVisibility(dateRangeInput);
        click(dateRangeInput);
    }

    public String getCurrentPeriodLabel() {
        waitForVisibility(calendarPeriodLabel);
        return getText(calendarPeriodLabel);
    }

    public boolean isTodayHighlighted() {
        WebElement todayCell = driver.findElement(dateLocator(LocalDate.now()));
        waitForVisibility(todayCell);
        boolean highlighted = "date".equals(todayCell.getAttribute("aria-current"));
        logger.debug("Today's date aria-current check result: {}", highlighted);
        return highlighted;
    }

    public void selectDate(LocalDate date) {
        By locator = dateLocator(date);
        if (driver.findElements(locator).isEmpty()) {
            logger.info("Target date {} not visible, navigating to next month", date);
            click(nextMonthButton);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        click(driver.findElement(locator));
        logger.info("Selected date: {}", date);
    }

    public String getDateRangeText() {
        waitForVisibility(dateRangeInput);
        return dateRangeInput.getAttribute("value");
    }

    public void clickContinue() {
        waitForVisibility(continueButton);
        click(continueButton);
    }

    public void closeInsuredModal() {
        waitForVisibility(closeModalButton);
        click(closeModalButton);
    }

    public void waitForUrlToContain(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    public String getValidationMessageText() {
        waitForVisibility(validationMessage);
        return getText(validationMessage);
    }

    public void refreshPage() {
        driver.navigate().refresh();
        logger.info("Page refreshed");
        waitForVisibility(dateRangeInput);
    }
}