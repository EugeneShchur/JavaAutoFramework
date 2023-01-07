package sample.automation.steps.api;

import static org.apache.http.HttpStatus.SC_OK;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BaseApiRequests {

  protected CookieFilter cookieFilter;
  protected Gson gson = new GsonBuilder().setPrettyPrinting().create();

  @Getter
  protected Header bearerHeader;

  @Getter
  protected String token;

  @Getter
  protected Cookies cookies;

  public BaseApiRequests() {
    RestAssured.useRelaxedHTTPSValidation();
    RestAssured.urlEncodingEnabled = false;
    cookieFilter = new CookieFilter();
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.responseSpecification = new ResponseSpecBuilder().expectStatusCode(SC_OK).build();
  }

  @Step
  public void setAuthToken(String idToken) {
    token = idToken;
    bearerHeader = new Header("Authorization", "Bearer " + idToken);
  }
}
