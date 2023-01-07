package sample.automation.tests.ui;


import static sample.automation.constants.BookDetailsFields.AUTHOR;
import static sample.automation.constants.BookDetailsFields.ISBN;
import static sample.automation.constants.BookDetailsFields.SUB_TITLE;
import static sample.automation.constants.BookDetailsFields.TITLE;
import static sample.automation.dataobjects.DataCalcHelper.getBooksTitles;
import static sample.automation.testng.TestRunConstants.BOOK_STORE_UI_GROUP;
import static sample.automation.testng.TestRunConstants.SMOKE_GROUP;
import static sample.automation.utils.SoftAssertion.softAssert;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import sample.automation.dataobjects.Book;
import sample.automation.steps.ui.pages.BookStoreSteps;
import sample.automation.testng.BaseTest;

@Feature("Books Store")
@Story("Anyone can view available books in the Book Store")
@Test(groups = {SMOKE_GROUP, BOOK_STORE_UI_GROUP})
public class ViewBooksTest extends BaseTest {
  // example of tests which don't need authorisation

  private List<Book> allAvailableBooks;
  private BookStoreSteps onBookStore;

  @BeforeClass(alwaysRun = true)
  public void getDataForTests() {
    allAvailableBooks = getApiSteps().onBookStoreApi()
                                     .getAllBooks();
    // any other general actions
  }

  @BeforeMethod(alwaysRun = true)
  public void reopenBookStorePage() {
    onBookStore = getUiSteps().navigateToPage(BookStoreSteps.class)
                              .bookStorePageShouldBeOpened();
  }

  @Test(description = "User can see all available books in the Book Store")
  public void demo_001() {
    List<String> expectedBooksTitles = getBooksTitles(allAvailableBooks);
    onBookStore
        .inBooksTable().booksShouldBeDisplayed(expectedBooksTitles);
  }

  @Test(description = "User can open and view book's details in the Book Store")
  public void demo_002() {
    Book anyBook = allAvailableBooks.get(0);
    onBookStore
        .inBooksTable().clickOnBookWithIsbn(anyBook.getIsbn())
        .bookStoreDetailsPageShouldBeOpenedFor(anyBook.getIsbn());
    softAssert(
        () -> onBookStore.bookDetailsFieldShouldBeDisplayedWithValue(ISBN, anyBook.getIsbn()),
        () -> onBookStore.bookDetailsFieldShouldBeDisplayedWithValue(TITLE, anyBook.getTitle()),
        () -> onBookStore.bookDetailsFieldShouldBeDisplayedWithValue(SUB_TITLE, anyBook.getSubTitle()),
        () -> onBookStore.bookDetailsFieldShouldBeDisplayedWithValue(AUTHOR, anyBook.getAuthor())
    );
  }
}
