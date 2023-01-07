package sample.automation.utils;

public class FileIsNotSavedException extends RuntimeException {
  public FileIsNotSavedException(String errorMessage, Throwable initialError) {
    super(errorMessage, initialError);
  }
}
