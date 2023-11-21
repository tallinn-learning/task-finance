package yahoofinance.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import yahoofinance.api.BaseSetupApi;

import static io.restassured.RestAssured.given;

public class ApiClient extends BaseSetupApi {

    private final static String MODULE_PARAM_NAME = "module";
    private final static String TICKER_PARAM_NAME = "ticker";

    public static Response executeGetRequest(RequestSpecification spec, String moduleName, String tickerName){

        return given()
                .spec(spec)
                .log()
                .all()
                .param(MODULE_PARAM_NAME, moduleName)
                .param(TICKER_PARAM_NAME, tickerName)
                .get( )
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

}