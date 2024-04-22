package sample.automation.pages;

import static sample.automation.matchers.HasTextMatcher.hasText;

import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;
import sample.automation.atlas.HtmlElement;
import sample.automation.elements.navbar.NavigationBar;
import sample.automation.matchers.IsElementDisplayedMatcher;

public interface BaseSitePage extends WebPage {

  @FindBy("//h1")
  HtmlElement pageTitle();

  @FindBy("//div[@class='left-pannel']")
  NavigationBar navigationBar();

  default void pageShouldHaveTitle(String expectedPageTitle) {
    pageTitle()
        .should(IsElementDisplayedMatcher.isDisplayed())
        .should(hasText(expectedPageTitle));
  }
}
