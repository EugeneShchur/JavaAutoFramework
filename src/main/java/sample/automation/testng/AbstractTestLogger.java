package sample.automation.testng;

import static sample.automation.utils.DateHelper.formatTimestampIntoLogDate;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class AbstractTestLogger {

  private static final AtomicInteger doneTestsNumber = new AtomicInteger(0);
  private int currentTestNumber;
  private long testMethodTimeStart;
  private long testClassTimeStart;

  @BeforeTest(alwaysRun = true)
  public void logBeforeTestsSet(ITestContext context) {
    log.info(String.format(">>> [TEST SET] '%s' (total %d tests) STARTED at %s ====",
                           context.getName(),
                           context.getAllTestMethods().length,
                           formatTimestampIntoLogDate(context.getStartDate().getTime())));
  }

  @AfterTest(alwaysRun = true)
  public void logAfterTestsSet(ITestContext context) {
    long testSetTimeEnd = System.currentTimeMillis();
    float takenTimeInSeconds = ((testSetTimeEnd - context.getStartDate().getTime()) / 1000f);
    log.info(String.format("<<< [TEST SET] '%s' ENDED at %s, total time taken - %.3f seconds. ====%n",
                           context.getName(),
                           formatTimestampIntoLogDate(testSetTimeEnd),
                           takenTimeInSeconds));
  }

  @BeforeClass(alwaysRun = true)
  public void logBeforeClass(ITestContext context) {
    testClassTimeStart = System.currentTimeMillis();
    log.info(String.format("  [TEST] '%s' started at %s",
                           this.getClass().getSimpleName(),
                           formatTimestampIntoLogDate(testClassTimeStart)));
  }

  @AfterClass(alwaysRun = true)
  public void logAfterClass(ITestContext context) {
    long testClassTimeEnd = System.currentTimeMillis();
    log.info(String.format("  [TEST] '%s' ended at %s, total time taken - %.3f seconds",
                           this.getClass().getSimpleName(),
                           formatTimestampIntoLogDate(testClassTimeEnd),
                           ((testClassTimeEnd - testClassTimeStart) / 1000.0f)));
  }

  @BeforeMethod(alwaysRun = true)
  public void logBeforeMethod(Method testMethod) {
    testMethodTimeStart = System.currentTimeMillis();
    currentTestNumber = doneTestsNumber.incrementAndGet();
    log.info(String.format("    Test case %d. '%s' started at %s",
                           currentTestNumber,
                           testMethod.getName(),
                           formatTimestampIntoLogDate(testMethodTimeStart)));
  }

  @AfterMethod(alwaysRun = true)
  public void logAfterMethod(Method testmethod) {
    long testMethodTimeEnd = System.currentTimeMillis();
    log.info(String.format("    Test case %d. '%s' ended at %s, time taken - %.3f seconds",
                           currentTestNumber,
                           testmethod.getName(),
                           formatTimestampIntoLogDate(testMethodTimeEnd),
                           ((testMethodTimeEnd - testMethodTimeStart) / 1000.0f)));
  }
}
