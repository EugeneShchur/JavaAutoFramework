package sample.automation.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// © Viktoriya Yelenskaya

public class SoftAssertion {

  private List<AssertionError> failedAssertions = new ArrayList<>();

  public static void softAssert(Runnable... actionsWithAsserts) {
    new SoftAssertion().bundleAsserts(actionsWithAsserts);
  }

  private void bundleAsserts(Runnable... actionsWithAsserts) {
    runAllAsserts(actionsWithAsserts);
    throwCollectedAssertionErrors(failedAssertions);
  }

  public static void throwCollectedAssertionErrors(List<AssertionError> failedAssertions) {
    if (!failedAssertions.isEmpty()) {
      throw new AssertionError(String.format("%d failed assertions found:%n %s",
                                             failedAssertions.size(),
                                             getJoinedAssertionMessage(failedAssertions)));
    }
  }

  private void runAllAsserts(Runnable... actionWithMultipleAsserts) {
    Stream.of(actionWithMultipleAsserts)
          .forEach(this::runAssert);
  }

  private void runAssert(Runnable actionWithAssert) {
    try {
      actionWithAssert.run();
    } catch (AssertionError error) {
      failedAssertions.add(error);
    }
  }

  private static String getJoinedAssertionMessage(List<AssertionError> failedAssertions) {
    return failedAssertions.stream()
                           .map(AssertionError::getMessage)
                           .collect(Collectors.joining("\n"));
  }
}
