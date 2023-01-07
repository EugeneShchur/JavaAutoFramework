package sample.automation.steps.ui;

import static sample.automation.webdriver.MyWebDriverManager.props;

import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import sample.automation.pages.BaseSitePage;
import sample.automation.steps.api.BaseApiSteps;
import sample.automation.webdriver.WebDriverSteps;

@Log4j2
public abstract class CommonPageSteps extends WebDriverSteps {

  protected static final String baseUrl = props.envToRunOn().getWebSiteUrl();
  protected BaseSitePage baseSitePage = onPage(BaseSitePage.class);

  @Getter
  @Setter
  private BaseApiSteps apiSteps;

  public CommonPageSteps(WebDriver driver) {
    super(driver);
  }

  @Step
  public void loadMainPage() {
    driver.get(baseUrl);
  }
}
