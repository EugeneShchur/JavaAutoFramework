package sample.automation.steps.ui.pages;

import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import sample.automation.constants.BookDetailsFields;
import sample.automation.pages.BookStorePage;
import sample.automation.steps.ui.NavSteps;
import sample.automation.steps.ui.generic.BooksTableSteps;
import sample.automation.steps.ui.generic.NavigationBarSteps;

@Log4j2
public class BookStoreSteps extends NavSteps {

  private static final String BOOK_STORE_PAGE_TITLE = "Book Store";
  private static final String BOOK_STORE_PAGE_PATH = "/books";
  private static final String BOOK_DETAILS_PATH_PARAM_PATTERN = "%s?book=%s";

  private final BookStorePage bookStorePage = onPage(BookStorePage.class);
  private final NavigationBarSteps<BookStoreSteps> navigationBar = new NavigationBarSteps<>(driver, this);
  private final BooksTableSteps<BookStoreSteps> inBooksTable = new BooksTableSteps<>(driver, this);

  public BookStoreSteps(WebDriver driver) {
    super(driver);
  }

  @Override
  public String getPageUrl() {
    return baseUrl + BOOK_STORE_PAGE_PATH;
  }

  public NavigationBarSteps<BookStoreSteps> inNavigationBar() {
    return navigationBar;
  }

  public BooksTableSteps<BookStoreSteps> inBooksTable() {
    return inBooksTable;
  }

  @Step("Book Store page is opened")
  public BookStoreSteps bookStorePageShouldBeOpened() {
    checkRedirectOnPage(getPageUrl(), BOOK_STORE_PAGE_TITLE);
    bookStorePage.pageShouldHaveTitle(BOOK_STORE_PAGE_TITLE);
    return this;
  }

  @Step("Book Store page is opened")
  public BookStoreSteps bookStoreDetailsPageShouldBeOpenedFor(String isbn) {
    checkRedirectOnPage(String.format(BOOK_DETAILS_PATH_PARAM_PATTERN,
                                      getPageUrl(),
                                      isbn),
                        BOOK_STORE_PAGE_TITLE);
    bookStorePage.pageShouldHaveTitle(BOOK_STORE_PAGE_TITLE);
    return this;
  }

  @Step("Click on Add To Your Collection button on book details")
  public BookStoreSteps clickOnAddToCollectionButton() {
    bookStorePage.addToCollectionButton()
                 .clickIfDisplayed();
    return this;
  }

  @Step("Check value of the '{0}' field on book details")
  public BookStoreSteps bookDetailsFieldShouldBeDisplayedWithValue(BookDetailsFields field,
                                                                   String expectedValue) {
    bookStorePage.bookDetailsFor(field.getFieldLocator())
                 .shouldHaveValue(expectedValue)
                 .shouldHaveLabel(field.getFieldLabel());
    return this;
  }
}
