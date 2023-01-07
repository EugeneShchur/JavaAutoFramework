package sample.automation.matchers;


import static sample.automation.utils.WebElementHelper.isElementPresent;

import java.time.Duration;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.WebElement;

import sample.automation.utils.Waiter;


public class IsElementNotPresentMatcher extends TypeSafeMatcher<WebElement> {
  private Duration timeout = Duration.ofSeconds(5);

  public IsElementNotPresentMatcher() {
  }

  public IsElementNotPresentMatcher(int timeout) {
    this.timeout = Duration.ofSeconds(timeout);
  }

  @Override
  protected boolean matchesSafely(WebElement element) {
    return Waiter.waitFor(element, timeout, testedElement -> !isElementPresent(testedElement));
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("element is NOT present on the page");
  }

  @Override
  public void describeMismatchSafely(WebElement element, Description mismatchDescription) {
    mismatchDescription
        .appendText("element ")
        .appendValue(element)
        .appendText(" is still present on the page");
  }

  public static Matcher<WebElement> isNotPresent() {
    return new IsElementNotPresentMatcher();
  }

  public static Matcher<WebElement> isNotPresent(int timeout) {
    return new IsElementNotPresentMatcher(timeout);
  }
}
