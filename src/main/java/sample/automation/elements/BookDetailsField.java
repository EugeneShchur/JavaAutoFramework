package sample.automation.elements;

import static sample.automation.matchers.HasTextMatcher.hasText;

import io.qameta.atlas.webdriver.extension.FindBy;
import sample.automation.atlas.AtlasWebElement;
import sample.automation.atlas.HtmlElement;
import sample.automation.matchers.IsElementDisplayedMatcher;

public interface BookDetailsField extends AtlasWebElement<BookDetailsField> {

  @FindBy(".//label[contains(@id, '-label')]")
  HtmlElement fieldLabel();

  @FindBy(".//label[contains(@id, '-value')]")
  HtmlElement fieldValue();

  default BookDetailsField shouldHaveLabel(String expectedLabel) {
    fieldLabel()
        .should(IsElementDisplayedMatcher.isDisplayed())
        .should(hasText(expectedLabel));
    return this;
  }

  default BookDetailsField shouldHaveValue(String expectedValue) {
    fieldValue()
        .should(IsElementDisplayedMatcher.isDisplayed())
        .should(hasText(expectedValue));
    return this;
  }
}
