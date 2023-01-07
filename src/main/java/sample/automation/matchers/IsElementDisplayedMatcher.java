package sample.automation.matchers;

import java.time.Duration;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.WebElement;

import sample.automation.utils.Waiter;

public class IsElementDisplayedMatcher extends TypeSafeMatcher<WebElement> {
  private Duration timeout = Duration.ofSeconds(5);

  public IsElementDisplayedMatcher() {
  }

  public IsElementDisplayedMatcher(int timeout) {
    this.timeout = Duration.ofSeconds(timeout);
  }

  @Override
  protected boolean matchesSafely(WebElement element) {
    return Waiter.waitFor(element, timeout, WebElement::isDisplayed);
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("element is displayed on page");
  }

  @Override
  public void describeMismatchSafely(WebElement element, Description mismatchDescription) {
    mismatchDescription
        .appendText("element ")
        .appendValue(element)
        .appendText(" is not displayed on page");
  }

  public static Matcher<WebElement> isDisplayed() {
    return new IsElementDisplayedMatcher();
  }

  public static Matcher<WebElement> isDisplayed(int timeout) {
    return new IsElementDisplayedMatcher(timeout);
  }
}
