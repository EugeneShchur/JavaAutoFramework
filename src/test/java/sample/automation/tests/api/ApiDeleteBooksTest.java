package sample.automation.tests.api;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static sample.automation.testdata.TestBooks.TEST_BOOK_TWO;
import static sample.automation.testdata.TestUsers.TEST_USER_TWO;
import static sample.automation.testng.TestRunConstants.API_REGRESSION_GROUP;
import static sample.automation.testng.TestRunConstants.SMOKE_GROUP;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import sample.automation.dataobjects.Book;
import sample.automation.dataobjects.User;
import sample.automation.testng.BaseApiTest;

@Feature("Books Store")
@Story("API - User can manage books in his collection")
@Test(groups = {SMOKE_GROUP, API_REGRESSION_GROUP})
public class ApiDeleteBooksTest extends BaseApiTest {

  private final User testUser = TEST_USER_TWO.getNewCopyOfTestUser();
  private final Book testBook = TEST_BOOK_TWO.getNewCopyOfTestBook();

  @BeforeClass(alwaysRun = true)
  public void login() {
    loginAsUserAndSetAuthData(testUser);
  }

  @BeforeMethod(alwaysRun = true)
  public void addTestBookForUser() {
    List<Book> userCollectionBooks = getApiSteps().onAccountApi()
                                                  .getBooksCollectionForUserId(testUser.getUserId());
    if (isEmpty(userCollectionBooks) || !userCollectionBooks.contains(testBook)) {
      getApiSteps().onBookStoreApi()
                   .addBooksToUserCollection(testBook.getIsbn(), testUser.getUserId());
    }
  }

  @Test(description = "User is able to delete a book from his collection by DELETE /Book api")
  public void demo_api_001() {
    getApiSteps().onBookStoreApi()
                 .deleteBookFromUserCollection(testBook.getIsbn(), testUser.getUserId());
    getApiSteps().onAccountApi()
                 .bookShouldNotBeInUserCollection(testBook.getIsbn(), testUser.getUserId());
  }
}
