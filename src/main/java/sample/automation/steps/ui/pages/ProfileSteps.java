package sample.automation.steps.ui.pages;

import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import sample.automation.pages.ProfilePage;
import sample.automation.steps.ui.NavSteps;
import sample.automation.steps.ui.generic.BooksTableSteps;
import sample.automation.steps.ui.generic.NavigationBarSteps;

@Log4j2
public class ProfileSteps extends NavSteps {

  private static final String PROFILE_PAGE_TITLE = "Profile";
  private static final String PROFILE_PAGE_PATH = "/profile";

  private final NavigationBarSteps<ProfileSteps> navigationBar = new NavigationBarSteps<>(driver, this);
  private final BooksTableSteps<ProfileSteps> inBooksTable = new BooksTableSteps<>(driver, this);

  private final ProfilePage profilePage = onPage(ProfilePage.class);

  public ProfileSteps(WebDriver driver) {
    super(driver);
  }

  @Override
  public String getPageUrl() {
    return baseUrl + PROFILE_PAGE_PATH;
  }

  public NavigationBarSteps<ProfileSteps> inNavigationBar() {
    return navigationBar;
  }

  public BooksTableSteps<ProfileSteps> inBooksTable() {
    return inBooksTable;
  }

  @Step
  public ProfileSteps profilePageShouldBeOpened() {
    checkRedirectOnPage(getPageUrl(), PROFILE_PAGE_TITLE);
    profilePage.pageShouldHaveTitle(PROFILE_PAGE_TITLE);
    return this;
  }

  @Step
  public ProfileSteps clickOnGoToBookStoreButton() {
    profilePage.goToBookStoreButton()
               .clickIfDisplayed();
    return this;
  }
}
