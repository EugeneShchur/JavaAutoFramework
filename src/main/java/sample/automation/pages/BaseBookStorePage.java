package sample.automation.pages;

import static sample.automation.matchers.HasTextMatcher.hasText;

import io.qameta.atlas.webdriver.extension.FindBy;
import sample.automation.atlas.HtmlElement;
import sample.automation.elements.Button;
import sample.automation.elements.SearchBox;
import sample.automation.elements.bookstable.BooksTable;
import sample.automation.matchers.IsElementDisplayedMatcher;

public interface BaseBookStorePage extends BaseSitePage {

  @FindBy("//div[./input[@id='searchBox']]")
  SearchBox searchBox();

  @FindBy("//label[@id='userName-value']")
  HtmlElement userNameLabel();

  @FindBy("//label/following-sibling::button[@id='submit']")
  Button logoutButton();

  @FindBy("//div[contains(@class, 'ReactTable')]")
  BooksTable booksTable();

  default void userNameShouldBeDisplayed(String expectedUserName) {
    userNameLabel()
        .should(IsElementDisplayedMatcher.isDisplayed())
        .should(hasText(expectedUserName));
  }
}
