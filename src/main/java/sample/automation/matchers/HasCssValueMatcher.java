package sample.automation.matchers;

import static org.hamcrest.Matchers.is;

import java.time.Duration;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.WebElement;

import sample.automation.utils.Waiter;

public class HasCssValueMatcher extends TypeSafeMatcher<WebElement> {
  private final String cssValueName;
  private final Matcher<String> attributeValueMatcher;
  private Duration timeout = Duration.ofSeconds(5);

  public HasCssValueMatcher(String cssValueName, Matcher<String> attributeValueMatcher) {
    this.cssValueName = cssValueName;
    this.attributeValueMatcher = attributeValueMatcher;
  }

  public HasCssValueMatcher(String cssValueName, Matcher<String> attributeValueMatcher, int timeout) {
    this.cssValueName = cssValueName;
    this.attributeValueMatcher = attributeValueMatcher;
    this.timeout = Duration.ofSeconds(timeout);
  }

  public static Matcher<WebElement> hasCssValue(final String cssValueName, final Matcher<String> cssValueMatcher) {
    return new HasCssValueMatcher(cssValueName, cssValueMatcher);
  }

  public static Matcher<WebElement> hasCssValue(final String cssValueName, final String cssValue) {
    return new HasCssValueMatcher(cssValueName, is(cssValue));
  }

  public static Matcher<WebElement> hasCssValue(final String cssValueName,
                                                final Matcher<String> cssValueMatcher,
                                                int timeout) {
    return new HasCssValueMatcher(cssValueName, cssValueMatcher, timeout);
  }

  public static Matcher<WebElement> hasCssValue(final String cssValueName,
                                                final String cssValue,
                                                int timeout) {
    return new HasCssValueMatcher(cssValueName, is(cssValue), timeout);
  }

  @Override
  public boolean matchesSafely(WebElement item) {
    return Waiter.waitFor(item, timeout, element -> attributeValueMatcher.matches(element.getCssValue(cssValueName)));
  }

  public void describeTo(Description description) {
    description
        .appendText("css value ")
        .appendValue(cssValueName)
        .appendText(" ")
        .appendDescriptionOf(attributeValueMatcher);
  }

  @Override
  protected void describeMismatchSafely(WebElement item, Description mismatchDescription) {
    mismatchDescription
        .appendText("css value ")
        .appendValue(cssValueName)
        .appendText(" of element ")
        .appendValue(item)
        .appendText(" was ")
        .appendValue(item.getCssValue(cssValueName));
  }
}
