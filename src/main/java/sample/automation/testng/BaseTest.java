package sample.automation.testng;

import static sample.automation.constants.AppConstants.TOKEN_COOKIE_NAME;
import static sample.automation.constants.AppConstants.USER_ID_COOKIE_NAME;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.xml.XmlTest;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import sample.automation.dataobjects.User;
import sample.automation.steps.api.BaseApiSteps;
import sample.automation.steps.ui.BaseSteps;
import sample.automation.steps.ui.pages.LoginSteps;
import sample.automation.steps.ui.pages.ProfileSteps;
import sample.automation.webdriver.MyWebDriverManager;

@Log4j2
@Getter
public class BaseTest extends AbstractTestLogger {

  protected MyWebDriverManager driverManager;
  protected BaseSteps uiSteps;
  protected BaseApiSteps apiSteps;

  @BeforeClass(alwaysRun = true)
  public void initBaseSteps(XmlTest testSuite) {
    driverManager = new MyWebDriverManager();
    driverManager.startWebDriver();
    uiSteps = new BaseSteps(driverManager.getWebDriver());
    apiSteps = new BaseApiSteps();
    uiSteps.setApiSteps(apiSteps);
    apiSteps.setUiSteps(uiSteps);
  }

  @Step("Login as '{0} user")
  public ProfileSteps loginAsUserAndSetAuthData(User user) {
    // this is just a combo-step example to use in the regular test cases
    // also you could use navigation and pages steps directly if you want
    // we could also do api authorisation here to set same token for further usage of api-steps
    ProfileSteps profilesteps = uiSteps.navigateToPage(LoginSteps.class)
                                       .loginPageShouldBeOpened()
                                       .doLoginWith(user.getUsername(), user.getPassword())
                                       .profilePageShouldBeOpened();
    String tokenFromBrowser = uiSteps.getDriver().manage()
                                     .getCookieNamed(TOKEN_COOKIE_NAME)
                                     .getValue();
    String userIdFromBrowser = uiSteps.getDriver().manage()
                                      .getCookieNamed(USER_ID_COOKIE_NAME)
                                      .getValue();
    apiSteps.setAuthToken(tokenFromBrowser);
    user.setUserId(userIdFromBrowser);
    return profilesteps;
  }

  /**
   * Additional check of the errors in browser console even after SUCCESS passing of the test. If some errors are
   * expected in some particular test — override this AfterClass method in test case class
   * with call of checkUnexpectedErrorsInConsoleExcept(String... ignoredErrors) method
   * and mention there all the errors which should be ignored for that test case
   */

  @AfterClass(alwaysRun = true)
  public void checkConsoleErrors() {
    getUiSteps().checkUnexpectedErrorsInConsole();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    driverManager.stopWebDriver();
  }
}
