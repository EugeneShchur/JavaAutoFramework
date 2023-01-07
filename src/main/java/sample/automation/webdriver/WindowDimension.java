package sample.automation.webdriver;

import java.util.Arrays;

import org.openqa.selenium.Dimension;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WindowDimension {
  FULL_HD(new Dimension(1920, 1080)),
  HD(new Dimension(1280, 1024)),
  MACBOOK_1(new Dimension(1280, 800)),
  MACBOOK_2(new Dimension(1440, 900)),
  MACBOOK_3(new Dimension(2880, 1800)),
  LAPTOP(new Dimension(1366, 768));

  @Getter
  private final Dimension dimension;

  public static WindowDimension getWindowDimensionByName(String windowDimensions) {
    return Arrays.stream(WindowDimension.values())
                 .filter(windowDimension -> windowDimension.name().equals(windowDimensions))
                 .findFirst()
                 .orElseThrow(() -> new java.util.NoSuchElementException(
                     String.format("No constants found in the WindowDimension enum with '%s' name", windowDimensions)
                 ));
  }
}
