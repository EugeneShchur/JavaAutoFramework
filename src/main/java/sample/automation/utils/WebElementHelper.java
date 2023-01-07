package sample.automation.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebElementHelper {

  private static final String SELF_PATH = "self::*";

  public static boolean isElementPresent(WebElement element) {
    try {
      return !findSelfElements(element).isEmpty();
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  public static List<WebElement> findSelfElements(WebElement element) {
    return element.findElements(By.xpath(SELF_PATH));
  }

  public static void scrollElementIntoViewUsingJs(WebElement element, WebDriver driver) {
    try {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].scrollIntoView(true);", findSelfElement(element));
    } catch (Exception e) {
      log.info("Executing of Javascript scrollIntoView() failed!", e);
      Assert.fail("Failed to scroll to web element using JS!");
    }
  }

  public static WebElement findSelfElement(WebElement element) {
    return element.findElement(By.xpath(SELF_PATH));
  }

  public static void clickOnElementUsingJS(WebElement element, WebDriver driver) {
    try {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].click();", findSelfElement(element));
    } catch (Exception e) {
      log.info("Executing of Javascript click() failed!", e);
      Assert.fail("Failed to click on web element using JS!");
    }
  }

  public static void removeAttributeForElementUsingJS(WebDriver driver, WebElement element, String attribute) {
    try {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].removeAttribute('" + attribute + "');", findSelfElement(element));
    } catch (Exception e) {
      log.info("Removing of attribute via Javascript removeAttribute() is failed!", e);
      Assert.fail("Failed to remove attribute from web element using JS!");
    }
  }

  public static void setAttributeForElementUsingJS(WebDriver driver, WebElement element, String attribute, String attributeValue) {
    try {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript(String.format("arguments[0].setAttribute('%s', '%s');", attribute, attributeValue),
                       findSelfElement(element));
    } catch (Exception e) {
      log.info("Setting of attribute via Javascript setAttribute() is failed!", e);
      Assert.fail("Failed to set attribute for web element using JS!");
    }
  }

  public static void sendKeysUsingJs(WebElement element, String text, WebDriver driver) {
    try {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      String constructScript = String.format("arguments[0].value='%s';", text);
      js.executeScript(constructScript, findSelfElement(element));
    } catch (Exception e) {
      log.info("Executing of 'sendKeys' using Javascript failed!", e);
    }
  }

  public static WebElement getParentNodeFor(WebElement element) {
    return element.findElement(By.xpath("./.."));
  }
}
