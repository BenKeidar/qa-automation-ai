# QA Automation Framework — Java & Selenium

A UI test automation framework built with **Java, Selenium WebDriver, TestNG, Maven, and Allure**, targeting the [Automation Exercise](https://automationexercise.com/) demo e-commerce website.

This project demonstrates practical test automation skills, including Page Object Model design, cross-browser testing, test data management, reporting, and continuous integration.

## Tech Stack

- **Java 21** — Programming language
- **Selenium WebDriver 4** — Browser automation
- **TestNG** — Test execution and assertions
- **Maven** — Build and dependency management
- **Allure Report** — Test reporting
- **GitHub Actions** — Continuous integration
- **Chrome & Firefox** — Cross-browser testing

## Test Coverage

The framework includes **18 automated test cases**, covering:

- User registration and account cleanup
- Valid and invalid login scenarios
- Logout functionality
- Home page verification
- Product listing and product search
- Adding multiple products to the shopping cart
- Removing products from the cart
- Empty cart validation
- Checkout authentication requirements
- Checkout flow for authenticated users
- Successful order placement

TestNG DataProviders are used for data-driven test scenarios.

## Framework Design

The project follows the **Page Object Model (POM)** design pattern to separate test logic from page interactions.

Key components include:

- `BaseTest` — WebDriver setup and teardown
- `BasePage` — Shared Selenium interactions and explicit waits
- Page Object classes — Encapsulated page elements and actions
- `TestData` — Centralized test data
- `ScreenshotListener` — Screenshot capture for failed tests and configuration failures
- TestNG test classes — Automated test scenarios and assertions

The framework also includes handling for certain advertisement overlays that may interfere with browser interactions.

## Running the Tests

### Prerequisites

- Java 21 or newer
- Maven
- Google Chrome and/or Mozilla Firefox

### Environment Variables

Some tests require an existing test account. Configure these environment variables before execution:

- `TEST_USER_EMAIL`
- `TEST_USER_PASSWORD`
- `TEST_USER_FULL_NAME`

Use a dedicated test account rather than personal credentials.

### Run All Tests

```bash
mvn clean test
```

### Run on Chrome

```bash
mvn clean test -Dbrowser=chrome -Dheadless=true
```

### Run on Firefox

```bash
mvn clean test -Dbrowser=firefox -Dheadless=true
```

Set `-Dheadless=false` to run tests with a visible browser window.

## Test Reporting

The project integrates **Allure** for test execution reports.

After running the tests, generate and open the report:

```bash
allure serve target/allure-results
```

Screenshots are captured for certain test and configuration failures to support debugging.

## Continuous Integration

GitHub Actions runs the automated tests on an Ubuntu runner using a browser matrix:

- Chrome (headless)
- Firefox (headless)

The workflow also uploads Allure results and failure screenshots as build artifacts.

### Known Limitations

The target website is a public demo application that includes third-party advertisements and occasionally experiences server-side errors, including Cloudflare HTTP 520 responses.

As a result, some UI tests may behave inconsistently in CI environments, particularly when page navigation or external resources are delayed.

These limitations are documented rather than treating every external website failure as an automation defect.

## Future Improvements

- Improve diagnostic logging for intermittent browser navigation failures
- Add more reliable test environment and data management
- Expand negative and edge-case test coverage
- Enhance CI reporting and failure analysis

## Purpose

This project was developed as part of a hands-on QA automation portfolio to demonstrate maintainable automation architecture, realistic end-to-end testing, and CI integration.