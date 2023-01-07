package sample.automation.steps.api;

import static io.restassured.RestAssured.with;
import static org.testng.Assert.assertTrue;
import static sample.automation.dataobjects.DataCalcHelper.isBookWithIsbnPresentInList;
import static sample.automation.utils.ResponseExtractor.getBooksListFromResponse;
import static sample.automation.utils.ResponseExtractor.getTokenFromResponse;
import static sample.automation.utils.ResponseExtractor.getUserIdFromResponse;
import static sample.automation.utils.Waiter.waitFor;
import static sample.automation.webdriver.MyWebDriverManager.props;

import java.time.Duration;
import java.util.List;

import com.google.common.collect.ImmutableMap;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import sample.automation.dataobjects.Book;
import sample.automation.dataobjects.User;

public class AccountApiSteps extends BaseApiRequests {

  private static final String BASE_ACCOUNT_PATH = "/Account/v1";
  private final RequestSpecification withDefaultRequestSpecification;

  public AccountApiSteps(Header bearerHeader) {
    this.bearerHeader = bearerHeader;
    withDefaultRequestSpecification = new RequestSpecBuilder()
        .setAccept(ContentType.JSON)
        .setBaseUri(props.envToRunOn().getMainApiUrl())
        .setBasePath(BASE_ACCOUNT_PATH)
        .build();
  }

  // Requests:

  @Step("POST /GenerateToken")
  protected Response sendPostGenerateToken(String bodyContent) {
    return with().spec(withDefaultRequestSpecification)
                 .contentType(ContentType.JSON)
                 .body(bodyContent)
                 .post("/GenerateToken");
  }

  @Step("POST /Login")
  protected Response sendPostLogin(String bodyContent) {
    return with().spec(withDefaultRequestSpecification)
                 .contentType(ContentType.JSON)
                 .body(bodyContent)
                 .post("/Login");
  }

  @Step("GET /User/..")
  protected Response sendGetUser(String userId) {
    return with().spec(withDefaultRequestSpecification)
                 .header(bearerHeader)
                 .pathParam("userId", userId)
                 .get("/User/{userId}");
  }


  // Actions:

  @Step("Login as '{0}' user and set auth token")
  public void loginAs(User user) {
    String requestBody = gson.toJson(ImmutableMap.of(
        "userName", user.getUsername(),
        "password", user.getPassword()
    ));
    sendPostGenerateToken(requestBody);
    Response loginResponse = sendPostLogin(requestBody);
    user.setUserId(getUserIdFromResponse(loginResponse));
    setAuthToken(getTokenFromResponse(loginResponse));
  }

  @Step("Get books collection for '{0}' user ID")
  public List<Book> getBooksCollectionForUserId(String userId) {
    return getBooksListFromResponse(sendGetUser(userId));
  }

  @Step("Check that book is present in the user's collection")
  public void bookShouldBePresentInUserCollection(String isbn, String userId) {
    assertTrue(waitForBookPresenceInUserCollection(isbn, userId, true),
               "Book is still absent in the user's collection");
  }

  @Step("Check that book is not available in the user's collection")
  public void bookShouldNotBeInUserCollection(String isbn, String userId) {
    assertTrue(waitForBookPresenceInUserCollection(isbn, userId, false),
               "Book is still present in the user's collection!");
  }

  private boolean waitForBookPresenceInUserCollection(String isbn, String userId, boolean isShouldBePresent) {
    return waitFor(this,
                   Duration.ofSeconds(3),
                   250,
                   (AccountApiSteps apiSteps) -> {
                     boolean isBookPresent = isBookWithIsbnPresentInList(apiSteps.getBooksCollectionForUserId(userId),
                                                                         isbn);
                     return isShouldBePresent == isBookPresent;
                   });
  }
}
