package yahoofinance.end2end;

import com.microsoft.playwright.*;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import yahoofinance.api.BaseSetupApi;
import yahoofinance.pages.FinancialChartPage;
import yahoofinance.utils.ApiClient;

public class UiAndApiTest extends BaseSetupApi {

    private Playwright playwright;
    private Page page;

    @BeforeEach
    void setUpPlaywright() {
        try {
            playwright = Playwright.create();
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            page = browser.newPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDownPlaywright() {
        if (playwright != null) {
            playwright.close();
        }
    }

    @Feature("Financial data End2End")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("test API and UI stock financial data - positive scenario")
    @Description("This test verifies the consistency of financial data between the API and UI. "
            + "It navigates to a financial chart UI, retrieves financial data from the API, "
            + "and compares the data for consistency.")
    @Test
    void testFinancialDataConsistencyBetweenApiAndUI() {

        Response response = ApiClient.executeGetRequest(getAuthenticatedRequestSpecification(), "financial-data", "NVDA");

        FinancialChartPage yahooPage = new FinancialChartPage(page);
        yahooPage.navigate(configuration.getString("site"));
        yahooPage.agreeCookiePolicy();
        yahooPage.getFinancialInformation();

        Assertions.assertEquals(response.jsonPath().getDouble("body.currentPrice.raw"),
                yahooPage.getFinancialInformation(),
                yahooPage.getFinancialInformation() * configuration.getDouble("delta-price-percentage"));
    }
}