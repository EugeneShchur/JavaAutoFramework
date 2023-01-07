package sample.automation.steps.api;

import static sample.automation.webdriver.MyWebDriverManager.props;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

public class SomeOtherApiSteps extends BaseApiRequests {

  private final RequestSpecification defaultRequestSpecification;

  public SomeOtherApiSteps(Header bearerHeader) {
    this.bearerHeader = bearerHeader;
    defaultRequestSpecification = new RequestSpecBuilder()
        .setAccept(ContentType.JSON)
        .setBaseUri(props.envToRunOn().getOtherApiUrl())
        .addHeader(bearerHeader.getName(), bearerHeader.getValue())
        .build();
  }

  // this class is just an example of extension the approach
  // with different classes for different api-services
}
