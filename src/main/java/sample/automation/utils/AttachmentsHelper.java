package sample.automation.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.openqa.selenium.WebDriver;

import com.assertthat.selenium_shutterbug.core.Shutterbug;

import io.qameta.allure.Attachment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AttachmentsHelper {

  private static final String SCREENSHOTS_DIRECTORY = "target/screenshots";
  private static final String OTHER_ATTACHMENTS_DIRECTORY = "target/attached-files";

  @Attachment(value = "Page screenshot {1}",
              type = "image/png")
  public static byte[] saveScreenshotPNG(WebDriver driver, String fileName) {
    Shutterbug.shootPage(driver, true).withName(fileName).save(SCREENSHOTS_DIRECTORY);
    try {
      byte[] screenshot = Files.readAllBytes(
          new File(String.format("%s/%s.png", SCREENSHOTS_DIRECTORY, fileName))
              .toPath()
      );
      log.info(String.format("Screenshot file '%s' is saved.", fileName));
      return screenshot;
    } catch (IOException e) {
      throw new FileIsNotSavedException("Screenshot is not saved", e);
    }
  }

  @Attachment(value = "{0}",
              type = "text/plain")
  public static String saveTextLog(String message) {
    return message;
  }

  @Attachment(value = "{0}",
              type = "text/html")
  public static String attachHtml(String html) {
    return html;
  }

  @Attachment(value = "Attached File",
              type = "application/octet-stream")
  public static byte[] attachFile(File file) {
    try {
      return Files.readAllBytes(
          new File(String.format("%s/%s", OTHER_ATTACHMENTS_DIRECTORY, file.getName()))
              .toPath()
      );
    } catch (IOException e) {
      throw new FileIsNotSavedException("File is not saved", e);
    }
  }
}
