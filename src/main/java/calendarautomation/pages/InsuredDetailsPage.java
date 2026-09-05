package calendarautomation.pages;

import calendarautomation.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InsuredDetailsPage extends BasePage {

    @FindBy(xpath = "//*[contains(@class, 'mg-wizard-nav-button') and contains(@class, 'secondary')]")
    private WebElement backButton;

    public InsuredDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void clickBack() {
        wait.until(ExpectedConditions.visibilityOf(backButton));
        click(backButton);
    }
}











