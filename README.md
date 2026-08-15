# GPI Calendar Automation

Test automation for the travel-insurance calendar on [mygpi.ge](https://mygpi.ge/ka-GE/mg-purchase/travel/policy) — my final project for the Quality Academy Java/Selenium course. I'd already written manual test cases for this same calendar in an earlier manual QA course (finished January 2026) — see them in my [QA portfolio](https://github.com/anadanelia369/QA-Portfolio#gpi-holding--20-test-cases) — and this project automates 6 of those scenarios, plus 1 API test.

![Test Report Screenshot](docs/report-screenshot.png)

7 automated tests in total (6 UI + 1 API), all passing.

## Tech Stack

Java 21 · Maven · Selenium WebDriver 4.45.0 · WebDriverManager 6.3.4 · TestNG 7.12.0 · RestAssured 6.0.1 · ExtentReports 5.1.2

## Design Pattern

Page Object Model (POM) with PageFactory — each page (calendar, "insured details") has its own class for locators and actions, and the test classes only call those actions. I chose POM over BDD/Cucumber to move faster within the timeline.

## Project Structure

```
GpiCalendarAutomation/
├── pom.xml
├── testNG.xml
├── config.properties
├── report/ExtentReport.html
└── src/
    ├── main/java/org/example/
    │   ├── pages/          (CalendarPage, InsuredDetailsPage)
    │   ├── utils/           (ApiClient, ConfigReader, DriverManager, ExtentReportManager, TestListener, Utils)
    │   └── BasePage.java
    └── test/java/org/example/
        ├── Tests/            (ApiTest, CalendarTest)
        └── BaseTest.java
```

## Test Cases

| # | Test Case | Type | What it checks |
|---|---|---|---|
| 1 | Calendar shows the current period | Positive | Clicking the input opens the calendar and it shows the correct current month |
| 2 | Calendar highlights today | Positive | Today's date is visually highlighted when the calendar opens |
| 3 | Select a date range | Positive | Start/end dates fill the input correctly and "Continue" moves to the next step |
| 4 | Empty period shows an error | Negative | Clicking "Continue" with no dates shows the validation message |
| 5 | Dates stay after Back navigation | Positive | Going to the next step and back keeps the selected dates |
| 6 | Page refresh clears the dates | Edge case | Refreshing after selecting dates resets the calendar |
| API | GET request check | Positive | GET to reqres.in returns status 200 |

**Why these cases:** together they cover the calendar's basic flow — open, use correctly, use incorrectly, and leave/return two different ways (Back keeps dates, Refresh clears them, so it's worth testing both). I skipped rapid-clicking since Selenium can't easily copy real human click timing. Dates are generated dynamically (`LocalDate.now()`) so the tests keep working regardless of when they run.

## Prerequisites

Java 21, Maven, and Google Chrome installed.

## Setup

1. Clone or download this repo
2. Open it in IntelliJ (or any Maven-aware IDE) and let Maven fetch the dependencies in `pom.xml`
3. Check that `config.properties` (project root) has the right values — see Configuration below

## How to Run

```bash
mvn clean test
```

Runs the suite defined in `testNG.xml`. Report opens at `report/ExtentReport.html` afterward.

## Configuration

`config.properties` (project root):

```properties
wait=10
base.url=https://mygpi.ge/ka-GE/mg-purchase/travel/policy
```

## A few things I learned

- The date input is `readonly`, so dates are selected by clicking the calendar, not typed.
- The page's load time after selecting a date wasn't consistent (it depends on things like whether you're logged in), so instead of guessing a fixed number, I used Explicit Wait — it only waits as long as the element actually needs, up to a max timeout set in `config.properties`.
- The calendar UI had changed since I wrote my manual test cases, so I re-checked everything in the browser instead of trusting my old notes.
- The site formats dates in Georgian ("11 აგვ, 2026") — I learned Java's `Locale` class (`new Locale("ka")`) can match that.
- I caught a test that only failed sometimes by re-running it until it failed, traced it to a missing wait, and fixed it — stable ever since.

## Author

Ana Danelia — Junior QA Manual Tester, moving into Automation QA (Java/Selenium).