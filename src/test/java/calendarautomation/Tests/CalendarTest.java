package calendarautomation.Tests;

import calendarautomation.BaseTest;
import calendarautomation.pages.CalendarPage;
import calendarautomation.pages.InsuredDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CalendarTest extends BaseTest {

    @Test(groups = "positive", priority = 1)
    public void calendarShowsCurrentPeriod() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.openCalendar();

        String expectedPeriod = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.ENGLISH))
                .toUpperCase();
        Assert.assertEquals(calendarPage.getCurrentPeriodLabel(), expectedPeriod);

//        String expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("M/d/yyyy"));
//        Assert.assertEquals(calendarPage.getCurrentPeriodLabel(), expectedDate);
    }

    @Test(groups = "positive", priority = 2)
    public void calendarHighlightsToday() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.openCalendar();
        Assert.assertTrue(calendarPage.isTodayHighlighted());
    }

    @Test(groups = "positive", priority = 3)
    public void calendarSelectsDateRange() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.openCalendar();

        LocalDate startDate = LocalDate.now().plusDays(7);
        LocalDate endDate = LocalDate.now().plusDays(13);

        calendarPage.selectDate(startDate);
        calendarPage.selectDate(endDate);

        Locale georgian = new Locale("ka");
        String expectedText = startDate.format(DateTimeFormatter.ofPattern("dd MMM, yyyy", georgian))
                + " - " + endDate.format(DateTimeFormatter.ofPattern("dd MMM, yyyy", georgian));
        Assert.assertEquals(calendarPage.getDateRangeText(), expectedText);


        calendarPage.clickContinue();
        calendarPage.waitForUrlToContain("travellers");
        Assert.assertTrue(driver.getCurrentUrl().contains("travellers"));

    }

    @Test(groups = "negative", priority = 4)
    public void calendarShowsValidationForEmptyPeriod() {
        CalendarPage calendarPage = new CalendarPage(driver);

        calendarPage.clickContinue();
        Assert.assertEquals(calendarPage.getValidationMessageText(), "მოგზაურობის პერიოდის არჩევა სავალდებულოა");
    }

    @Test(groups = "positive", priority = 5)
    public void calendarKeepsPeriodAfterBack() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.openCalendar();

        LocalDate startDate = LocalDate.now().plusDays(7);
        LocalDate endDate = LocalDate.now().plusDays(13);
        calendarPage.selectDate(startDate);
        calendarPage.selectDate(endDate);

        String expectedText = calendarPage.getDateRangeText();

        calendarPage.clickContinue();
        calendarPage.closeInsuredModal();

        InsuredDetailsPage insuredPage = new InsuredDetailsPage(driver);
        insuredPage.clickBack();

        CalendarPage calendarPageAfterBack = new CalendarPage(driver);
        Assert.assertEquals(calendarPageAfterBack.getDateRangeText(), expectedText);
    }

    @Test(groups = "edge", priority = 6)
    public void calendarClearsDateRangeAfterRefresh() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.openCalendar();

        LocalDate startDate = LocalDate.now().plusDays(7);
        LocalDate endDate = LocalDate.now().plusDays(13);
        calendarPage.selectDate(startDate);
        calendarPage.selectDate(endDate);

        calendarPage.refreshPage();
        Assert.assertTrue(calendarPage.isDateRangeInvalid());
    }
}