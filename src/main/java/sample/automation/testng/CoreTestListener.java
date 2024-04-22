package sample.automation.testng;

import static io.qameta.allure.util.ResultsUtils.TAG_LABEL_NAME;
import static sample.automation.utils.AllureEnvXmlWriter.saveValuesIntoAllureEnvironmentXml;
import static sample.automation.utils.DateHelper.formatTimestampIntoFilenameDate;
import static sample.automation.webdriver.MyWebDriverManager.props;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.qameta.allure.Allure;
import lombok.extern.log4j.Log4j2;
import sample.automation.utils.AttachmentsHelper;

@Log4j2
public class CoreTestListener implements IAnnotationTransformer,
                                         IInvokedMethodListener,
                                         ITestListener {

  @Override
  public void transform(ITestAnnotation annotation,
                        Class testClass,
                        Constructor testConstructor,
                        Method testMethod) {
    // applies the RetryAnalyzer on all methods which has @Test annotation
    annotation.setRetryAnalyzer(RetryAnalyzer.class);
  }

  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    Method testMethod = method.getTestMethod()
                              .getConstructorOrMethod()
                              .getMethod();
    if (testMethod.isAnnotationPresent(Test.class)) {
      // setting tags may come in handy for export of reports into Allure TestOps or other test management system:
      List<String> allGroupsForTest = Arrays.asList(testResult.getMethod().getGroups());
      allGroupsForTest.forEach(groupName -> Allure.label(TAG_LABEL_NAME, groupName));
    }
  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    if (method.getTestMethod().getMethodName().equals("checkConsoleErrors")) {
      return;
    }
    if (testResult.getStatus() == ITestResult.FAILURE) {
      String testFullName = String.format("%s_%s",
                                          testResult.getTestClass().getName(),
                                          testResult.getMethod().getMethodName());
      if (method.isConfigurationMethod()) {
        log.info(String.format("Before/After configuration method is failed for test '%s', taking screenshot...",
                               testFullName));
      } else {
        log.info(String.format("Test '%s' failed, taking screenshot...", testFullName));
      }
      String fileName = String.format("%s_%s",
                                      testFullName,
                                      formatTimestampIntoFilenameDate(System.currentTimeMillis()));
      saveScreenshotFromBrowserSession(testResult, fileName);
    }
  }

  private void saveScreenshotFromBrowserSession(ITestResult testResult, String fileName) {
    WebDriver driver = ((BaseTest) testResult.getInstance()).getDriverManager()
                                                            .getWebDriver();
    AttachmentsHelper.saveScreenshotPNG(driver, fileName);
  }

  @Override
  public void onTestStart(ITestResult result) {
  }

  @Override
  public void onTestSuccess(ITestResult result) {
  }

  @Override
  public void onTestFailure(ITestResult result) {
  }

  @Override
  public void onTestSkipped(ITestResult result) {
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
  }

  @Override
  public void onStart(ITestContext context) {
  }

  @Override
  public void onFinish(ITestContext context) {
    // this will add properties data used during test run into Allure report
    saveValuesIntoAllureEnvironmentXml(ImmutableMap.<String, String>builder()
                                                   .put("Groups", Arrays.toString(context.getIncludedGroups()))
                                                   .put("Environment", props.envToRunOn().toString())
                                                   .put("Browser name", props.browserName())
                                                   .put("Browser version", props.browserVersion())
                                                   .put("Is in headless mode", String.valueOf(props.isHeadless()))
                                                   .put("Browser window dimensions", props.windowDimension())
                                                   .build(),
                                       "target/allure-results/");
  }
}
