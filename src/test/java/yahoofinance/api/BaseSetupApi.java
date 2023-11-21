package yahoofinance.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.jupiter.api.BeforeAll;

public class BaseSetupApi {
    private final static String PATH_TO_CONFIG = "application.yaml";
    protected static PropertiesConfiguration configuration;

    @BeforeAll
    public static void setUp() throws ConfigurationException {

        configuration = new PropertiesConfiguration();
        configuration.load(PATH_TO_CONFIG);

        RestAssured.baseURI = configuration.getString("base-url");
        RestAssured.basePath = configuration.getString("stocks-endpoint");

    }

    public RequestSpecification getAuthenticatedRequestSpecification(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("accept", "application/json, text/plain, */*");
        builder.addHeader("x-rapidapi-key", configuration.getString("api-key"));
        return builder.build();
    }

    public RequestSpecification getUnauthenticatedRequestSpecification(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("accept", "application/json, text/plain, */*");
        return builder.build();
    }

}
