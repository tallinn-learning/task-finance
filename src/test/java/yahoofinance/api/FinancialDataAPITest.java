package yahoofinance.api;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import yahoofinance.utils.ApiClient;

public class FinancialDataAPITest extends BaseSetupApi {

    @Feature("Financial data API")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Get Financial Data and Check Response Content")
    @Description("Retrieve financial data from the API and verify " +
            "the content of the response")
    @ParameterizedTest
    @CsvFileSource(resources="/testdata.csv")
    void getFinancialDataAndCheckResponseContent(String moduleName, String tickerName) {

        Response response = ApiClient.executeGetRequest(getAuthenticatedRequestSpecification(), moduleName, tickerName);

        Assertions.assertAll("Meta Information",
                () -> Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Status code is OK"),
                () -> Assertions.assertEquals(moduleName, response.jsonPath().getString("meta.modules"))
        );

        Assertions.assertAll("Body Information",
                () -> Assertions.assertTrue(response.jsonPath().getDouble("body.currentPrice.raw") > 0, "Current price is  greater than 0"),
                () -> Assertions.assertTrue(response.jsonPath().getDouble("body.totalRevenue.raw") > 0, "Total Cash is  greater than 0")
        );
    }

    @Feature("Financial data API")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Get Financial Data and Check Unauthorized Response Content")
    @Description("Attempt to retrieve financial data from the API with unauthorized " +
            "access and verify that the response has the expected status code")
    @ParameterizedTest
    @CsvFileSource(resources="/testdata.csv")
    void getFinancialDataAndCheckBadResponseContent(String moduleName, String tickerName) {

        Response response = ApiClient.executeGetRequest(getUnauthenticatedRequestSpecification(), moduleName, tickerName);
        Assertions.assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusCode(), "Status code is unauthorized");
    }

    @Feature("Financial data API")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Get Financial Data for Nonexistent Ticker")
    @Description("Attempt to retrieve financial data for a nonexistent ticker using an authenticated request. " +
            "Verify that the response has the expected status code")
    @ParameterizedTest
    @CsvFileSource(resources="/testdata.csv")
    void getFinancialDataForNonexistentTicker(String moduleName, String tickerName) {

        Response response = ApiClient.executeGetRequest(getAuthenticatedRequestSpecification(), moduleName, "!" + tickerName);
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode(), "Status code is Not Found");
    }

}
