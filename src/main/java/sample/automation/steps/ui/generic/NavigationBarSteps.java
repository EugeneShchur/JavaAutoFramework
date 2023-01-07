package sample.automation.steps.ui.generic;

import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import sample.automation.steps.ui.CommonPageSteps;
import sample.automation.steps.ui.NavSteps;

@Log4j2
public class NavigationBarSteps<P extends NavSteps> extends CommonPageSteps {

  private final P parentUiSteps;

  public NavigationBarSteps(WebDriver driver, P parentUiSteps) {
    super(driver);
    this.parentUiSteps = parentUiSteps;
  }

  @Step("Click on '{0}' section title in navigation bar")
  public P clickOnSectionTitleToExpandCollapse(String sectionTitle) {
    baseSitePage.navigationBar()
                .navBarSection(sectionTitle)
                .clickIfDisplayed();
    return parentUiSteps;
  }

  @Step("Click on '{0}' section title in navigation bar")
  public P sectionShouldHaveExpandedState(String sectionTitle, boolean isExpanded) {
    baseSitePage.navigationBar()
                .navBarSection(sectionTitle)
                .shouldBeExpanded(isExpanded);
    return parentUiSteps;
  }

  @Step("Click on '{1}' option from '{0}' in navigation bar")
  public <T extends NavSteps> T clickOnOptionFromSection(String sectionTitle,
                                                         String optionName,
                                                         Class<T> expectedTargetPageStepsClass) {
    baseSitePage.navigationBar()
                .navBarSection(sectionTitle)
                .option(optionName)
                .scrollToElement(driver)
                .click();
    try {
      return expectedTargetPageStepsClass.getConstructor(WebDriver.class)
                                         .newInstance(driver);
    } catch (Exception e) {
      log.error(e);
      throw new AssertionError("Failed to navigate on the target page!");
    }
  }
}
