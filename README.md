# yahoo financial information assignment
This repository contains end-to-end test suites for financial 
data retrieval, encompassing both API and UI scenarios. 
The tests are designed to ensure the consistency and correctness of financial data retrieved from different sources.

## Tech Stack
- [Java](https://www.java.com): Programming language used for test automation.
- [Maven](https://maven.apache.org): Build and dependency management tool.
- [RestAssured](https://rest-assured.io): Java library for testing RESTful APIs.
- [Playwright](https://playwright.dev): Browser automation library for testing web applications.

**API Tests (FinancialDataAPITest)**

The FinancialDataAPITest class focuses on testing the financial data API. 
It verifies the response content, including the status code, module information, and body details. Additionally, the class includes tests for scenarios such as unauthorized access and retrieving data for nonexistent tickers.

**End-to-End Tests (UiAndApiTest)**

The UiAndApiTest class combines API and UI testing to ensure the consistency of 
financial data between these two sources. The test scenario involves retrieving 
financial data from the API, navigating to a financial chart UI using Playwright, 
and comparing the data for consistency.

**Prerequisites**

Before running the tests, make sure you have the following installed: Java, Maven, Chrome browser

**Usage**
```
mvn clean test  
mvn allure:serve 
```

To run a specific test class using Maven, execute the following command in the terminal:

```
mvn clean test -Dtest=UiAndApiTest