package sample.automation.tests.ui;


import static sample.automation.constants.AppConstants.BookStoreAppNavBarOptions.PROFILE_NAV_BAR_OPTION;
import static sample.automation.constants.AppConstants.NavBarSections.BOOK_STORE_APP_NAV_BAR_SECTION;
import static sample.automation.testdata.TestBooks.TEST_BOOK_ONE;
import static sample.automation.testdata.TestUsers.TEST_USER_ONE;
import static sample.automation.testng.TestRunConstants.BOOK_STORE_UI_GROUP;
import static sample.automation.testng.TestRunConstants.SMOKE_GROUP;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import sample.automation.dataobjects.Book;
import sample.automation.dataobjects.User;
import sample.automation.steps.ui.pages.BookStoreSteps;
import sample.automation.steps.ui.pages.ProfileSteps;
import sample.automation.testng.BaseTest;

@Feature("Books Store")
@Story("User can manage books in his collection and see them on Profile")
@Test(groups = {SMOKE_GROUP, BOOK_STORE_UI_GROUP})
public class AddBookIntoCollectionTest extends BaseTest {

  private final User testUser = TEST_USER_ONE.getNewCopyOfTestUser();
  private final Book testBook = TEST_BOOK_ONE.getNewCopyOfTestBook();
  private BookStoreSteps onBookStore;

  @BeforeClass(alwaysRun = true)
  public void loginAndGetDataForTests() {
    loginAsUserAndSetAuthData(testUser);
    // any other general actions
  }

  @BeforeMethod(alwaysRun = true)
  public void deleteTestBookFromCollectionAndReopenBookStore() {
    List<Book> userCollectionBooks = getApiSteps().onAccountApi()
                                                  .getBooksCollectionForUserId(testUser.getUserId());
    if (userCollectionBooks.contains(testBook)) {
      getApiSteps().onBookStoreApi()
                   .deleteBookFromUserCollection(testBook.getIsbn(), testUser.getUserId());
    }
    onBookStore = getUiSteps().navigateToPage(BookStoreSteps.class)
                              .bookStorePageShouldBeOpened();
  }

  @Test(description = "User is able to add a book into his collection from Book Store")
  public void demo_003() {
    onBookStore
        .inBooksTable().clickOnBookWithIsbn(testBook.getIsbn())
        .bookStoreDetailsPageShouldBeOpenedFor(testBook.getIsbn())
        .clickOnAddToCollectionButton()
        .inNavigationBar().sectionShouldHaveExpandedState(BOOK_STORE_APP_NAV_BAR_SECTION, true)
        .inNavigationBar().clickOnOptionFromSection(BOOK_STORE_APP_NAV_BAR_SECTION,
                                                    PROFILE_NAV_BAR_OPTION,
                                                    ProfileSteps.class)
        .profilePageShouldBeOpened()
        .inBooksTable().bookWithIsbnShouldBeDisplayed(testBook.getIsbn());
  }
}
