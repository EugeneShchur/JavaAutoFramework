package sample.automation.webdriver;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.matchesRegex;
import static org.testng.Assert.assertTrue;
import static sample.automation.utils.StringHelper.EMPTY_STRING;
import static sample.automation.utils.StringHelper.NEW_LINE_STRING;
import static sample.automation.utils.StringHelper.SPACE_STRING;
import static sample.automation.utils.Waiter.waitFor;
import static sample.automation.utils.WebElementHelper.findSelfElement;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import io.qameta.allure.Step;
import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.context.RetryerContext;
import io.qameta.atlas.core.internal.DefaultRetryer;
import io.qameta.atlas.webdriver.WebDriverConfiguration;
import io.qameta.atlas.webdriver.WebPage;
import io.restassured.http.Cookies;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import sample.automation.utils.ConsoleErrorException;

@Log4j2
public class WebDriverSteps {

  @Getter
  protected WebDriver driver;

  protected Actions actions;

  public WebDriverSteps(WebDriver driver) {
    this.driver = driver;
    actions = new Actions(driver);
  }

  protected <T extends WebPage> T onPage(Class<T> pageClass) {
    return onPageWithCustomisedRetry(pageClass, 3000L, 200L, singletonList(Throwable.class));
  }

  protected <T extends WebPage> T onPageWithCustomisedRetry(Class<T> pageClass,
                                                            Long timeout,
                                                            Long polling,
                                                            List<Class<? extends Throwable>> exceptionsToIgnore) {
    return new Atlas(new WebDriverConfiguration(driver))
        .context(new RetryerContext(new DefaultRetryer(timeout, polling, exceptionsToIgnore)))
        .create(driver, pageClass);
  }

  protected <T> T openPage(String url, T stepsObject) {
    driver.get(url);
    return stepsObject;
  }

  public WebDriverSteps wait(int millisec) {
    try {
      Thread.sleep(millisec);
    } catch (InterruptedException e) {
      log.info("Thread.sleep() is interrupted!", e);
      Thread.currentThread().interrupt();
    }
    return this;
  }

  protected void focusAndSendKeys(WebElement element, String text) {
    actions.moveToElement(element)
           .click()
           .sendKeys(text).perform();
  }

  protected void hoverOverElement(WebElement element) {
    actions.moveToElement(findSelfElement(element)).perform();
  }

  protected void dragAndDrop(WebElement element1, WebElement element2) {
    actions.dragAndDrop(element1, element2).perform();
  }

  protected void sendKeysWithNewLines(WebElement element, String textWithNewLine) {
    element.sendKeys(textWithNewLine.replace(NEW_LINE_STRING,
                                             Keys.chord(Keys.SHIFT, Keys.ENTER)));
  }

  protected void doubleClick(WebElement element) {
    actions.doubleClick(element).perform();
  }

  @Step
  public void switchToNewOpenedWindow() {
    String currentWindow = driver.getWindowHandle();
    for (String window : driver.getWindowHandles()) {
      if (!currentWindow.equals(window)) {
        switchToWindow(window);
      }
    }
  }

  protected void switchToWindow(String windowHandle) {
    driver.switchTo().window(windowHandle);
  }

  @Step
  public void switchBackToBaseWindow() {
    switchToWindow(EMPTY_STRING);
  }

  @Step
  public void switchToFrame(WebElement frameElement) {
    driver.switchTo()
          .frame(frameElement.getAttribute("id"));
  }

  @Step
  public void switchToParentFrame() {
    driver.switchTo().parentFrame();
  }

  @Step
  public void closeCurrentAndSwitchBackToBaseWindow() {
    if (driver.getWindowHandles().size() > 1) {
      driver.close();
    }
    switchBackToBaseWindow();
  }

  @Step
  public void setCookiesToWebDriver(Cookies cookies) {
    cookies.asList().stream()
           .map(cookie -> new Cookie(
               cookie.getName(),
               cookie.getValue(),
               cookie.getDomain(),
               cookie.getPath(),
               cookie.getExpiryDate(),
               cookie.isSecured(),
               cookie.isHttpOnly()
           ))
           .forEach(cookie -> driver.manage().addCookie(cookie));
  }

  @Step
  public void clearAllBrowsingData() {
    deleteCurrentSiteCookies();
    ((WebStorage) driver).getLocalStorage().clear();
    ((WebStorage) driver).getSessionStorage().clear();
  }

  @Step
  public void deleteCurrentSiteCookies() {
    driver.manage().deleteAllCookies();
  }

  public void waitUntilJqueryIsDone(int seconds) {
    waitFor(driver,
            ofSeconds(seconds),
            ex -> (Boolean) ((JavascriptExecutor) ex).executeScript("return jQuery.active==0"));
  }

  @Step("Click on Back button in browser")
  public void clickBackButtonInBrowser() {
    driver.navigate().back();
  }

  @Step("Reload page")
  public void reloadPage() {
    driver.navigate().refresh();
  }

  @Step("Accept appeared confirmation alert")
  protected void confirmAlert() {
    try {
      Alert alert = driver.switchTo().alert();
      alert.accept();
      log.info("Alert has been accepted: " + alert.getText());
    } catch (NoAlertPresentException e) {
      log.info("There is no alert present " + e);
    }
  }

  @Step
  public void clearTheErrorsLogInConsole() {
    driver.manage()
          .logs()
          .get(LogType.BROWSER);
  }

  @Step
  public void checkUnexpectedErrorsInConsole() {
    checkErrorsInConsole(logEntry -> true);
  }

  @Step
  public void checkUnexpectedErrorsInConsoleExcept(String... ignoredErrors) {
    Predicate<LogEntry> errorsFilter = logEntry -> Arrays.stream(ignoredErrors)
                                                         .noneMatch(error -> logEntry.getMessage().matches(error));
    checkErrorsInConsole(errorsFilter);
  }

  private void checkErrorsInConsole(Predicate<LogEntry> errorsFilter) {
    List<LogEntry> logEntries = driver.manage().logs()
                                      .get(LogType.BROWSER)
                                      .getAll();
    String unexpectedErrors = logEntries.stream()
                                        .filter(errorsFilter)
                                        .map(logEntry -> logEntry.getLevel() + SPACE_STRING + logEntry.getMessage())
                                        .collect(Collectors.joining(NEW_LINE_STRING));
    if (!EMPTY_STRING.equals(unexpectedErrors)) {
      throw new ConsoleErrorException("There are unexpected errors in the browser console!\n" + unexpectedErrors);
    }
  }

  @Step
  public void checkThatErrorPresentInConsole(String expectedConsoleError) {
    List<String> logEntries = driver.manage().logs()
                                    .get(LogType.BROWSER)
                                    .getAll()
                                    .stream()
                                    .map(LogEntry::getMessage)
                                    .collect(Collectors.toList());
    if (isEmpty(logEntries)) {
      throw new AssertionError("There are no errors in the browser console!");
    }
    assertThat("Expected error is not present in browser console!",
               logEntries,
               hasItem(matchesRegex(expectedConsoleError)));
  }

  protected void checkRedirectOnPage(String expectedUrl, String pageName) {
    int waitingTimeout = 5;
    assertTrue(waitFor(driver,
                       expectedUrl,
                       Duration.ofSeconds(waitingTimeout),
                       (WebDriver browser, String expectedCurrentUrl) -> browser.getCurrentUrl()
                                                                                .equals(expectedCurrentUrl)),
               String.format("%s page is not opened during %d seconds, current URL is '%s' instead of expected '%s'",
                             pageName,
                             waitingTimeout,
                             driver.getCurrentUrl(),
                             expectedUrl));
  }

  protected void checkThatPageUrlHasNoParameter(String parameter) {
    assertTrue(waitFor(driver,
                       parameter,
                       Duration.ofSeconds(3),
                       (WebDriver browser, String parameterInUrl) ->
                           !browser.getCurrentUrl().contains(parameterInUrl)),
               String.format("Current page URL still has '%s' parameter which mustn't be there", parameter));
  }

  @Step
  public void checkThatPageUrlDoesNotContain(String partOfUrl, String messagePattern) {
    assertTrue(waitFor(driver,
                       partOfUrl,
                       Duration.ofSeconds(60),
                       (WebDriver browser, String partOfUrlToCheck) ->
                           !browser.getCurrentUrl().contains(partOfUrl)),
               String.format(messagePattern, partOfUrl));
  }

  @Step
  public void checkThatPageUrlDoesNotContain(String partOfUrl) {
    checkThatPageUrlDoesNotContain(partOfUrl, "Current page URL contains '%s' but it shouldn't!");
  }
}
