package sample.automation.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrowserCommandsHelper {

  public static String getBrowserTimeZoneUsingJS(WebDriver driver) {
    try {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      return (String) js.executeScript("return Intl.DateTimeFormat().resolvedOptions().timeZone;");
    } catch (Exception e) {
      log.info("Getting of browser timeZone with Javascript failed!", e);
      Assert.fail("Failed to get WebDriver timezone using JS!");
    }
    return null;
  }

  public static String getItemFromLocalStorage(WebDriver driver, String key) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    return (String) js.executeScript(String.format("return window.localStorage.getItem('%s');", key));
  }

  public static boolean isItemPresentInLocalStorage(WebDriver driver, String key) {
    return getItemFromLocalStorage(driver, key) != null;
  }

  public static void setItemInLocalStorage(WebDriver driver, String key, String value) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript(String.format("window.localStorage.setItem('%s','%s');", key, value));
  }

  public static void removeItemFromLocalStorage(WebDriver driver, String key) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript(String.format("window.localStorage.removeItem('%s');", key));
  }

  public static void clearLocalStorage(WebDriver driver) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.localStorage.clear();");
  }
}
