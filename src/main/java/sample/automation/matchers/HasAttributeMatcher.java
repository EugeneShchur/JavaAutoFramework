package sample.automation.matchers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.Duration;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.WebElement;

import sample.automation.utils.Waiter;


public class HasAttributeMatcher extends TypeSafeMatcher<WebElement> {

  public static final String CLASS_ATTRIBUTE = "class";
  public static final String VALUE_ATTRIBUTE = "value";
  private final String attributeName;
  private final Matcher<String> attributeValueMatcher;
  private Duration timeout = Duration.ofSeconds(5);

  public HasAttributeMatcher(String attributeName, Matcher<String> attributeValueMatcher) {
    this.attributeName = attributeName;
    this.attributeValueMatcher = attributeValueMatcher;
  }

  public HasAttributeMatcher(String attributeName, Matcher<String> attributeValueMatcher, int timeout) {
    this.attributeName = attributeName;
    this.attributeValueMatcher = attributeValueMatcher;
    this.timeout = Duration.ofSeconds(timeout);
  }

  @Override
  public boolean matchesSafely(WebElement item) {
    return Waiter.waitFor(item,
                          timeout,
                          element -> attributeValueMatcher.matches(element.getAttribute(attributeName)));
  }

  public void describeTo(Description description) {
    description
        .appendText("attribute ")
        .appendValue(attributeName)
        .appendText(" ")
        .appendDescriptionOf(attributeValueMatcher);
  }

  @Override
  protected void describeMismatchSafely(WebElement item, Description mismatchDescription) {
    mismatchDescription
        .appendText("attribute ")
        .appendValue(attributeName)
        .appendText(" of element ")
        .appendValue(item)
        .appendText(" was ")
        .appendValue(item.getAttribute(attributeName));
  }

  public static Matcher<WebElement> hasAttribute(final String attributeName,
                                                 final Matcher<String> attributeValueMatcher) {
    return new HasAttributeMatcher(attributeName, attributeValueMatcher);
  }

  public static Matcher<WebElement> hasAttribute(final String attributeName, final String attributeValue) {
    return new HasAttributeMatcher(attributeName, is(attributeValue));
  }

  public static Matcher<WebElement> hasAttribute(final String attributeName,
                                                 final Matcher<String> attributeValueMatcher,
                                                 int timeout) {
    return new HasAttributeMatcher(attributeName, attributeValueMatcher, timeout);
  }

  public static Matcher<WebElement> hasAttribute(final String attributeName,
                                                 final String attributeValue,
                                                 int timeout) {
    return new HasAttributeMatcher(attributeName, is(attributeValue), timeout);
  }

  public static Matcher<WebElement> hasClassAttribute(final Matcher<String> attributeValueMatcher) {
    return new HasAttributeMatcher(CLASS_ATTRIBUTE, attributeValueMatcher);
  }

  public static Matcher<WebElement> hasClassAttribute(final String attributeValue) {
    return new HasAttributeMatcher(CLASS_ATTRIBUTE, is(attributeValue));
  }

  public static Matcher<WebElement> hasClassAttribute(final Matcher<String> attributeValueMatcher, int timeout) {
    return new HasAttributeMatcher(CLASS_ATTRIBUTE, attributeValueMatcher, timeout);
  }

  public static Matcher<WebElement> hasClassAttribute(final String attributeValue, int timeout) {
    return new HasAttributeMatcher(CLASS_ATTRIBUTE, is(attributeValue), timeout);
  }

  public static Matcher<WebElement> hasValueAttribute(final Matcher<String> attributeValueMatcher) {
    return new HasAttributeMatcher(VALUE_ATTRIBUTE, attributeValueMatcher);
  }

  public static Matcher<WebElement> hasValueAttribute(final String attributeValue) {
    return new HasAttributeMatcher(VALUE_ATTRIBUTE, is(attributeValue));
  }

  public static Matcher<WebElement> hasValueAttribute(final Matcher<String> attributeValueMatcher, int timeout) {
    return new HasAttributeMatcher(VALUE_ATTRIBUTE, attributeValueMatcher, timeout);
  }

  public static Matcher<WebElement> hasValueAttribute(final String attributeValue, int timeout) {
    return new HasAttributeMatcher(VALUE_ATTRIBUTE, is(attributeValue), timeout);
  }

  public static Matcher<WebElement> hasNullValueForAttribute(final String attributeName) {
    return new HasAttributeMatcher(attributeName, equalTo(null));
  }

  public static Matcher<WebElement> hasNullValueForAttribute(final String attributeName, int timeout) {
    return new HasAttributeMatcher(attributeName, equalTo(null), timeout);
  }
}
