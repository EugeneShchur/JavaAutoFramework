package sample.automation.steps.ui;

import static sample.automation.utils.DateHelper.formatTimestampIntoFilenameDate;
import static sample.automation.utils.StringHelper.EMPTY_STRING;

import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import sample.automation.utils.AttachmentsHelper;

@Log4j2
public abstract class NavSteps extends CommonPageSteps {

  public NavSteps(WebDriver driver) {
    super(driver);
  }

  abstract protected String getPageUrl();

  @Step
  public <T extends NavSteps> T navigateToPage(Class<T> targetStepsClass) {
    return navigate(targetStepsClass, EMPTY_STRING);
  }

  @Step
  public <T extends NavSteps> T navigateToPageWithAdditionalPath(Class<T> targetStepsClass, String additionalPath) {
    return navigate(targetStepsClass, additionalPath);
  }

  private <T extends NavSteps> T navigate(Class<T> targetStepsClass, String additionalPath) {
    try {
      T steps = targetStepsClass.getConstructor(WebDriver.class)
                                .newInstance(driver);
      driver.get(steps.getPageUrl() + additionalPath);
      return steps;
    } catch (Exception e) {
      log.error(e);
      AttachmentsHelper.saveScreenshotPNG(driver, "navigate_failed_"
          + formatTimestampIntoFilenameDate(System.currentTimeMillis()));
      throw new AssertionError("Failed to navigate on the target page!");
    }
  }
}
