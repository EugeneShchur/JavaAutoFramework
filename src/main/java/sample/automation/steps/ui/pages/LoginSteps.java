package sample.automation.steps.ui.pages;

import static sample.automation.matchers.IsElementDisplayedMatcher.isDisplayed;

import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import sample.automation.pages.LoginPage;
import sample.automation.steps.ui.NavSteps;

@Log4j2
public class LoginSteps extends NavSteps {

  private static final String LOGIN_PAGE_TITLE = "Login";
  private static final String LOGIN_PAGE_PATH = "/login";

  private final LoginPage loginPage = onPage(LoginPage.class);

  public LoginSteps(WebDriver driver) {
    super(driver);
  }

  @Override
  public String getPageUrl() {
    return baseUrl + LOGIN_PAGE_PATH;
  }

  @Step
  public LoginSteps loginPageShouldBeOpened() {
    checkRedirectOnPage(getPageUrl(), LOGIN_PAGE_TITLE);
    loginPage.pageShouldHaveTitle(LOGIN_PAGE_TITLE);
    signInFormShouldBeDisplayed();
    return this;
  }

  @Step("Do login with credentials")
  public ProfileSteps doLoginWith(String userName, String password) {
    enterUsername(userName)
        .enterPassword(password)
        .clickOnLoginButton();
    return new ProfileSteps(driver);
  }

  @Step
  public LoginSteps signInFormShouldBeDisplayed() {
    loginPage.signInForm()
             .should(isDisplayed());
    return this;
  }

  @Step
  public LoginSteps enterUsername(String username) {
    loginPage.userNameInput()
             .should(isDisplayed())
             .sendKeys(username);
    return this;
  }

  @Step
  public LoginSteps enterPassword(String password) {
    loginPage.passwordInput()
             .should(isDisplayed())
             .sendKeys(password);
    return this;
  }

  @Step
  public LoginSteps clickOnLoginButton() {
    loginPage.loginButton()
             .clickIfDisplayed();
    return this;
  }
}
