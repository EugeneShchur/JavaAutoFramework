package sample.automation.testng;


import static sample.automation.testng.TestRunConstants.DO_NOT_RETRY_GROUP;
import static sample.automation.testng.TestRunConstants.KNOWN_ISSUE_GROUP;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

  /**
   * @see IRetryAnalyzer#retry(ITestResult) The retryLimit indicates how many times a failed @Test
   * method should be attempted to rerun. TestNg will call retry() method every time a test fails. So we can put some code
   * in here to define criteria when to rerun the test.
   */
  private static final int retryLimit = 1;

  private int counter = 0;

  @Override
  public boolean retry(ITestResult result) {
    List<String> allGroupsForMethod = Arrays.asList(result.getMethod().getGroups());
    List<String> groupsToNotRetry = Arrays.asList(KNOWN_ISSUE_GROUP,
                                                  DO_NOT_RETRY_GROUP);
    if (counter < retryLimit
        && Collections.disjoint(allGroupsForMethod, groupsToNotRetry)) {
      counter++;
      return true;
    }
    return false;
  }
}
