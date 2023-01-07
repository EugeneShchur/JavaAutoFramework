package sample.automation.testng;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestRunConstants {

  public static final String KNOWN_ISSUE_GROUP = "KNOWN_ISSUE";
  public static final String DO_NOT_RETRY_GROUP = "DO_NOT_RETRY";

  public static final String SMOKE_GROUP = "Smoke";
  public static final String BOOK_STORE_UI_GROUP = "BookStoreUi";
  public static final String API_REGRESSION_GROUP = "ApiRegression";
  // don't forget to add new group names into README.md file also
}
