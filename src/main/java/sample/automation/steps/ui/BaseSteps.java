package sample.automation.steps.ui;

import org.openqa.selenium.WebDriver;

import lombok.extern.log4j.Log4j2;
import sample.automation.steps.ui.generic.NavigationBarSteps;

@Log4j2
public class BaseSteps extends NavSteps {

  private final NavigationBarSteps<BaseSteps> navigationBar = new NavigationBarSteps<>(driver, this);

  public BaseSteps(WebDriver driver) {
    super(driver);
  }

  @Override
  public String getPageUrl() {
    return baseUrl;
  }

  public NavigationBarSteps<BaseSteps> inNavigationBar() {
    return navigationBar;
  }
}
