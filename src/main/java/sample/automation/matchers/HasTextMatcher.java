package sample.automation.matchers;

import static org.hamcrest.Matchers.is;
import static sample.automation.utils.StringHelper.EMPTY_STRING;

import java.time.Duration;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import sample.automation.utils.Waiter;

public class HasTextMatcher extends TypeSafeMatcher<WebElement> {
  private final Matcher<String> textMatcher;
  private Duration timeout = Duration.ofSeconds(5);

  private HasTextMatcher(Matcher<String> textMatcher) {
    this.textMatcher = textMatcher;
  }

  private HasTextMatcher(Matcher<String> textMatcher, int timeout) {
    this.textMatcher = textMatcher;
    this.timeout = Duration.ofSeconds(timeout);
  }

  @Override
  public boolean matchesSafely(WebElement item) {
    return Waiter.waitFor(item, timeout, element -> textMatcher.matches(element.getText()));
  }

  public void describeTo(Description description) {
    description.appendText("element text ").appendValue(textMatcher);
  }

  @Override
  protected void describeMismatchSafely(WebElement item, Description mismatchDescription) {
    try {
      String text = item.getText();
      mismatchDescription
          .appendText("text of element ")
          .appendValue(item)
          .appendText(" was ")
          .appendValue(text)
          .appendText(" while waiting ")
          .appendValue(timeout)
          .appendText(" seconds");
    } catch (NoSuchElementException e) {
      mismatchDescription
          .appendText("element ")
          .appendValue(item)
          .appendText(" is not displayed while waiting ")
          .appendValue(timeout)
          .appendText(" seconds");
    }
  }

  public static Matcher<WebElement> hasText(final Matcher<String> textMatcher) {
    return new HasTextMatcher(textMatcher);
  }

  public static Matcher<WebElement> hasText(final String expectedString) {
    return new HasTextMatcher(is(expectedString));
  }

  public static Matcher<WebElement> hasText(final Matcher<String> textMatcher, int timeout) {
    return new HasTextMatcher(textMatcher, timeout);
  }

  public static Matcher<WebElement> hasText(final String expectedString, int timeout) {
    return new HasTextMatcher(is(expectedString), timeout);
  }

  public static Matcher<WebElement> hasEmptyText() {
    return new HasTextMatcher(is(EMPTY_STRING));
  }

  public static Matcher<WebElement> hasEmptyText(int timeout) {
    return new HasTextMatcher(is(EMPTY_STRING), timeout);
  }
}
