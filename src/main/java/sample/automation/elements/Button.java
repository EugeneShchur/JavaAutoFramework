package sample.automation.elements;

import static sample.automation.matchers.HasCssValueMatcher.hasCssValue;
import static sample.automation.matchers.HasTextMatcher.hasText;

import sample.automation.atlas.AtlasWebElement;

public interface Button extends AtlasWebElement<Button> {

  default Button shouldHaveTitle(String expectedTitle) {
    should(hasText(expectedTitle));
    return this;
  }

  default Button shouldHaveColor(String expectedColor) {
    should(hasCssValue("background-color", expectedColor));
    return this;
  }
}
