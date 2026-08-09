package org.example.pages;

import org.example.BasePage;
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

    @FindBy(css = ".mg-input-error-message")
    private WebElement validationMessage;

    @FindBy(css = ".mat-calendar-period-button .mdc-button__label")
    private WebElement calendarPeriodLabel;

    @FindBy(css = ".mat-calendar-body-today")
    private WebElement todayCell;

    @FindBy(css = ".mg-wizard-nav-button.primary")
    private WebElement continueButton;

    @FindBy(css = "img[alt='close icon']")
    private WebElement closeModalButton;

    @FindBy(css = "mg-date-range-control")
    private WebElement dateRangeControl;

    public CalendarPage(WebDriver driver) {
        super(driver);
    }

    private By dateLocator(LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        return By.cssSelector("[aria-label='" + formattedDate + "']");
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
        wait.until(ExpectedConditions.visibilityOf(todayCell));
        return todayCell.getAttribute("class").contains("mat-calendar-body-today");
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