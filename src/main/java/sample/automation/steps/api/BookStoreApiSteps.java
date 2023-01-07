package sample.automation.steps.api;

import static io.restassured.RestAssured.with;
import static java.util.Collections.singletonList;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static sample.automation.utils.ResponseExtractor.getBooksListFromResponse;
import static sample.automation.webdriver.MyWebDriverManager.props;

import java.util.List;

import com.google.common.collect.ImmutableMap;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import sample.automation.dataobjects.Book;

public class BookStoreApiSteps extends BaseApiRequests {

  private static final String BASE_BOOK_STORE_PATH = "/BookStore/v1";
  private final RequestSpecification withDefaultRequestSpecification;

  public BookStoreApiSteps(Header bearerHeader) {
    this.bearerHeader = bearerHeader;
    withDefaultRequestSpecification = new RequestSpecBuilder()
        .setAccept(ContentType.JSON)
        .setBaseUri(props.envToRunOn().getMainApiUrl())
        .setBasePath(BASE_BOOK_STORE_PATH)
        .build();
  }

  // Requests:

  @Step("GET /Books")
  protected Response sendGetBooks() {
    return with().spec(withDefaultRequestSpecification)
                 .get("/Books");
  }

  @Step("POST /Books")
  protected Response sendPostBook(String bodyContent) {
    return with().spec(withDefaultRequestSpecification)
                 .header(bearerHeader)
                 .contentType(ContentType.JSON)
                 .body(bodyContent)
                 .expect()
                 .statusCode(SC_CREATED)
                 .when()
                 .post("/Books");
  }

  @Step("DELETE /Book")
  protected Response sendDeleteBook(String bodyContent) {
    return with().spec(withDefaultRequestSpecification)
                 .header(bearerHeader)
                 .contentType(ContentType.JSON)
                 .body(bodyContent)
                 .expect()
                 .statusCode(SC_NO_CONTENT)
                 .when()
                 .delete("/Book");
  }


  // Actions:

  @Step("Get all books data")
  public List<Book> getAllBooks() {
    return getBooksListFromResponse(sendGetBooks());
  }

  @Step("Add book to user's collection")
  public void addBooksToUserCollection(String isbn, String userId) {
    String requestBody = gson.toJson(ImmutableMap.of(
        "userId", userId,
        "collectionOfIsbns", singletonList(
            ImmutableMap.of("isbn", isbn)
        )
    ));
    sendPostBook(requestBody);
  }

  @Step("Delete book from user's collection")
  public BookStoreApiSteps deleteBookFromUserCollection(String isbn, String userId) {
    String requestBody = gson.toJson(ImmutableMap.of(
        "isbn", isbn,
        "userId", userId
    ));
    sendDeleteBook(requestBody);
    return this;
  }
}
