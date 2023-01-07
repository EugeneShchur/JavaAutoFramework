package sample.automation.matchers;

import java.time.Duration;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.WebElement;

import sample.automation.utils.Waiter;
import sample.automation.utils.WebElementHelper;


public class IsElementPresentMatcher extends TypeSafeMatcher<WebElement> {
  private Duration timeout = Duration.ofSeconds(5);

  public IsElementPresentMatcher() {
  }

  public IsElementPresentMatcher(int timeout) {
    this.timeout = Duration.ofSeconds(timeout);
  }

  @Override
  protected boolean matchesSafely(WebElement element) {
    return Waiter.waitFor(element, timeout, WebElementHelper::isElementPresent);
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("element is present on the page");
  }

  @Override
  public void describeMismatchSafely(WebElement element, Description mismatchDescription) {
    mismatchDescription
        .appendText("element ")
        .appendValue(element)
        .appendText(" is not present on the page");
  }

  public static Matcher<WebElement> isPresent() {
    return new IsElementPresentMatcher();
  }

  public static Matcher<WebElement> isPresent(int timeout) {
    return new IsElementPresentMatcher(timeout);
  }
}
