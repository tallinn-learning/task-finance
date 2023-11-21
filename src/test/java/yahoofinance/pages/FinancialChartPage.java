package yahoofinance.pages;

import com.microsoft.playwright.*;

public class FinancialChartPage {

    private final Page page;
    private final Locator agreeButtonSelector;
    private final Locator qspPriceSelector;

    public FinancialChartPage(Page page) {
        this.page = page;
        this.agreeButtonSelector = page.locator("[name='agree']");
        this.qspPriceSelector = page.locator("[data-test='qsp-price']");
    }

    public void navigate(String url) {
        page.navigate(url);
    }

    public void agreeCookiePolicy() {
        agreeButtonSelector.click();
    }

    public double getFinancialInformation() {
        return Double.parseDouble( qspPriceSelector.first().textContent() );
    }

}
