package calendarautomation.pages;

import calendarautomation.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalendarPage extends BasePage {
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

    @FindBy(xpath = "//mg-date-range-control")
    private WebElement dateRangeControl;


    public CalendarPage(WebDriver driver) {
        super(driver);
    }

    private By dateLocator(LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return By.xpath("//button[@aria-label='" + formattedDate + "']");
    }

    public void openCalendar() {
        wait.until(ExpectedConditions.visibilityOf(dateRangeInput));
        click(dateRangeInput);
    }

    public String getCurrentPeriodLabel() {
        wait.until(ExpectedConditions.visibilityOf(calendarPeriodLabel));
        return getText(calendarPeriodLabel);
    }

    public boolean isTodayHighlighted() {
        WebElement todayCell = driver.findElement(dateLocator(LocalDate.now()));
        wait.until(ExpectedConditions.visibilityOf(todayCell));
        return "date".equals(todayCell.getAttribute("aria-current"));
    }


    public void selectDate(LocalDate date) {
        By locator = dateLocator(date);
        WebElement dateCell = driver.findElement(locator);
        click(dateCell);
    }

    public String getDateRangeText() {
        wait.until(ExpectedConditions.visibilityOf(dateRangeInput));
        return dateRangeInput.getAttribute("value");
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.visibilityOf(continueButton));
        click(continueButton);
    }

    public void closeInsuredModal() {
        wait.until(ExpectedConditions.visibilityOf(closeModalButton));
        click(closeModalButton);
    }

    public void waitForUrlToContain(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    public String getValidationMessageText() {
        wait.until(ExpectedConditions.visibilityOf(validationMessage));
        return getText(validationMessage);
    }

    public void refreshPage() {
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOf(dateRangeInput));
    }

    public boolean isDateRangeInvalid() {
        wait.until(ExpectedConditions.visibilityOf(dateRangeControl));
        return dateRangeControl.getAttribute("class").contains("ng-invalid");
    }
}