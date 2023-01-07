package sample.automation.webdriver;

import static java.util.Collections.singletonList;
import static sample.automation.webdriver.SeleniumEnvironment.LOCAL;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.google.common.collect.ImmutableMap;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.extern.log4j.Log4j2;
import ru.qatools.properties.PropertyLoader;
import sample.automation.EnvProperties;

@Log4j2
public class MyWebDriverManager {

  public static final EnvProperties props =
      PropertyLoader.newInstance().populate(EnvProperties.class);
  public static final DriverManagerType browserType =
      DriverManagerType.valueOf(props.browserName());
  public static final String DOWNLOADED_FILES_DIR =
      System.getProperty("user.dir") + File.separator + "downloadedFiles";
  public static final boolean isRunOnGitHub = Boolean.parseBoolean(System.getenv("GITHUB_ACTIONS"));
  private WebDriver driver;

  public WebDriver getWebDriver() {
    return driver;
  }

  public void startWebDriver() {
    if (LOCAL.name().equals(props.seleniumEnvironment())) {
      setupLocalDriver();
    } else {
      try {
        setupRemoteDriver();
      } catch (MalformedURLException e) {
        log.error(e.getMessage());
      }
    }
  }

  /**
   * By default WebDriverManager downloads the binary for the same operating systems than the machine running the test,
   * so for local driver there is no need to do setPlatform explicitly
   */
  private void setupLocalDriver() {
    // Safari does not need driver set up
    if (!DriverManagerType.SAFARI.equals(browserType)) {
      WebDriverManager.getInstance(browserType)
                      .driverVersion(props.browserVersion())
                      .setup();
    }
    driver = createWebDriver();
    if (isRunOnGitHub || props.isHeadless()) {
      setWindowSize();
    } else {
      driver.manage().window().maximize();
    }
  }

  private void setupRemoteDriver() throws MalformedURLException {
    DesiredCapabilities capabilities =
        new DesiredCapabilities() {{
          setCapability("version", props.browserVersion());
          setPlatform(Platform.fromString(props.remoteWebDriverPlatform()));
          merge(getDriverOptions());
        }};
    driver = new RemoteWebDriver(new URL(props.remoteWebDriverUrl()), capabilities) {{
      setFileDetector(new LocalFileDetector());
    }};
    setWindowSize();
  }

  private WebDriver createWebDriver() {
    switch (browserType) {
      case FIREFOX:
        return new FirefoxDriver(getFirefoxOptions());
      case EDGE:
        return new EdgeDriver(getEdgeOptions());
      case SAFARI:
        return new SafariDriver(getSafariOptions());
      default:
        return new ChromeDriver(getChromeOptions());
    }
  }

  private void setWindowSize() {
    driver.manage()
          .window()
          .setSize(WindowDimension.getWindowDimensionByName(props.windowDimension())
                                  .getDimension());
  }

  private Capabilities getDriverOptions() {
    switch (browserType) {
      case FIREFOX:
        return getFirefoxOptions();
      case EDGE:
        return getEdgeOptions();
      case SAFARI:
        return getSafariOptions();
      default:
        return getChromeOptions();
    }
  }

  private ChromeOptions getChromeOptions() {
    ChromeOptions chromeOptions = new ChromeOptions();
    setChromiumOptions(chromeOptions);
    return chromeOptions;
  }

  private EdgeOptions getEdgeOptions() {
    EdgeOptions edgeOptions = new EdgeOptions();
    setChromiumOptions(edgeOptions);
    return edgeOptions;
  }

  private void setChromiumOptions(ChromiumOptions<?> options) {
    options.setExperimentalOption("prefs", ImmutableMap.of(
        "download.default_directory", DOWNLOADED_FILES_DIR,
        "credentials_enable_service", false,
        "profile.password_manager_enabled", false
    ));
    options.setExperimentalOption("excludeSwitches", singletonList("enable-automation"));
    options.setHeadless(isRunOnGitHub || props.isHeadless());
    // also may need addArguments("--disable-extensions");
  }

  private FirefoxOptions getFirefoxOptions() {
    FirefoxProfile profile =
        new FirefoxProfile() {{
          setPreference("browser.download.folderList", 2);
          setPreference("browser.download.dir", DOWNLOADED_FILES_DIR);
          setPreference(
              "browser.helperApps.neverAsk.saveToDisk",
              "text/csv,application/java-archive, application/x-msexcel,application/excel,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/vnd.microsoft.portable-executable"
          );
        }};
    return new FirefoxOptions() {{
      setProfile(profile);
      setLogLevel(FirefoxDriverLogLevel.ERROR);
      setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
      setHeadless(isRunOnGitHub || props.isHeadless());
    }};
  }

  private SafariOptions getSafariOptions() {
    return new SafariOptions() {{
      setCapability("safari.options.dataDir", DOWNLOADED_FILES_DIR);
    }};
  }

  public void stopWebDriver() {
    if (driver != null) {
      driver.quit();
    }
  }
}
