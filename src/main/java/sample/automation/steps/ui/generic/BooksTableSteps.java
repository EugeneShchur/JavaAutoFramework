package sample.automation.steps.ui.generic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static sample.automation.matchers.HasSizeCollectionMatcher.hasSize;
import static sample.automation.matchers.IsElementDisplayedMatcher.isDisplayed;

import java.util.List;

import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import sample.automation.elements.bookstable.BooksTable;
import sample.automation.pages.BaseBookStorePage;
import sample.automation.steps.ui.CommonPageSteps;

@Log4j2
public class BooksTableSteps<P extends CommonPageSteps> extends CommonPageSteps {

  private final BooksTable booksTable = onPage(BaseBookStorePage.class).booksTable();
  private final P parentUiSteps;

  public BooksTableSteps(WebDriver driver, P parentUiSteps) {
    super(driver);
    this.parentUiSteps = parentUiSteps;
  }

  @Step("Check that book with '{0}' ISBN is displayed in the books table")
  public P bookWithIsbnShouldBeDisplayed(String isbn) {
    booksTable.bookByIsbn(isbn)
              .should(isDisplayed());
    return parentUiSteps;
  }

  @Step("Click on '{0}' book title-link in the books table")
  public P clickOnBookWithIsbn(String isbn) {
    booksTable.bookByIsbn(isbn)
              .should(isDisplayed())
              .bookTitle()
              .scrollToElement(driver)
              .click();
    return parentUiSteps;
  }

  @Step("Check the list of displayed books in the table by their titles")
  public P booksShouldBeDisplayed(List<String> expectedTitlesList) {
    List<String> displayedBooks = booksTable.allBooksList()
                                            .should(hasSize(expectedTitlesList.size()))
                                            .extract(bookRow -> bookRow.bookTitle()
                                                                       .should(isDisplayed())
                                                                       .getText());
    assertThat("Some of the info items in the 'Permissions' tab are missed or have incorrect titles",
               displayedBooks,
               containsInAnyOrder(expectedTitlesList.toArray()));
    return parentUiSteps;
  }
}
