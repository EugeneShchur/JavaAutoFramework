package sample.automation.matchers;

import static org.hamcrest.Matchers.equalTo;

import java.time.Duration;
import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import sample.automation.utils.Waiter;


public class HasSizeCollectionMatcher<E> extends TypeSafeMatcher<Collection<E>> {
  private Matcher<Integer> intMatcher;
  private Duration timeout = Duration.ofSeconds(5);

  public HasSizeCollectionMatcher(Matcher<Integer> intMatcher) {
    this.intMatcher = intMatcher;
  }

  public HasSizeCollectionMatcher(Matcher<Integer> intMatcher, int timeout) {
    this.intMatcher = intMatcher;
    this.timeout = Duration.ofSeconds(timeout);
  }

  @Override
  protected boolean matchesSafely(Collection<E> collection) {
    return Waiter.waitFor(collection, timeout, list -> intMatcher.matches(list.size()));
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("collection size is ").appendDescriptionOf(intMatcher);
  }

  @Override
  public void describeMismatchSafely(Collection<E> elements, Description mismatchDescription) {
    mismatchDescription
        .appendText("collection size is ")
        .appendValue(elements.size())
        .appendText(" while waiting ")
        .appendValue(timeout)
        .appendText(" seconds");
  }

  public static <E> Matcher<Collection<E>> hasSize(Matcher<Integer> intMatcher) {
    return new HasSizeCollectionMatcher<>(intMatcher);
  }

  public static <E> Matcher<Collection<E>> hasSize(int size) {
    return new HasSizeCollectionMatcher<>(equalTo(size));
  }

  public static <E> Matcher<Collection<E>> hasSize(Matcher<Integer> intMatcher, int timeout) {
    return new HasSizeCollectionMatcher<>(intMatcher, timeout);
  }

  public static <E> Matcher<Collection<E>> hasSize(int size, int timeout) {
    return new HasSizeCollectionMatcher<>(equalTo(size), timeout);
  }

  public static <E> Matcher<Collection<E>> hasZeroSize() {
    return new HasSizeCollectionMatcher<>(equalTo(0));
  }

  public static <E> Matcher<Collection<E>> hasZeroSize(int timeout) {
    return new HasSizeCollectionMatcher<>(equalTo(0), timeout);
  }
}
