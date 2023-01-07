package sample.automation.matchers;


import static sample.automation.utils.WebElementHelper.findSelfElement;

import java.time.Duration;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import sample.automation.utils.Waiter;


public class IsElementNotDisplayedMatcher extends TypeSafeMatcher<WebElement> {
  private Duration timeout = Duration.ofSeconds(5);

  public IsElementNotDisplayedMatcher() {
  }

  public IsElementNotDisplayedMatcher(int timeout) {
    this.timeout = Duration.ofSeconds(timeout);
  }

  @Override
  protected boolean matchesSafely(WebElement element) {
    return Waiter.waitFor(element,
                          timeout,
                          item -> {
                            try {
                              findSelfElement(item);
                              return !item.isDisplayed();
                            } catch (NoSuchElementException | StaleElementReferenceException ex) {
                              return true;
                            }
                          });
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("element is not displayed on page");
  }

  @Override
  public void describeMismatchSafely(WebElement element, Description mismatchDescription) {
    mismatchDescription
        .appendText("element ")
        .appendValue(element)
        .appendText(" is displayed on page");
  }

  public static Matcher<WebElement> isNotDisplayed() {
    return new IsElementNotDisplayedMatcher();
  }

  public static Matcher<WebElement> isNotDisplayed(int timeout) {
    return new IsElementNotDisplayedMatcher(timeout);
  }
}
