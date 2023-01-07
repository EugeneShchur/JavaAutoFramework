package sample.automation.utils;

import java.util.List;

import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import sample.automation.dataobjects.Book;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseExtractor {

  public static List<Book> getBooksListFromResponse(Response response) {
    return response.jsonPath()
                   .getList("books", Book.class);
  }

  public static String getUserIdFromResponse(Response loginResponse) {
    return loginResponse.jsonPath()
                        .getString("userId");
  }

  public static String getTokenFromResponse(Response loginResponse) {
    return loginResponse.jsonPath()
                        .getString("token");
  }
}
