package sample.automation.utils;

import static sample.automation.utils.AttachmentsHelper.saveTextLog;

import org.testng.SkipException;

public class ConsoleErrorException extends SkipException {

  public ConsoleErrorException(String message) {
    super(message);
    saveTextLog(message);
  }
}
