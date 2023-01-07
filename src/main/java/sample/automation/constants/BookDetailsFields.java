package sample.automation.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookDetailsFields {

  ISBN("ISBN", "ISBN :"),
  TITLE("title", "Title :"),
  SUB_TITLE("subtitle", "Sub Title :"),
  AUTHOR("author", "Author :");
  // add constants for other fields if you need to check them

  @Getter
  private final String fieldLocator;

  @Getter
  private final String fieldLabel;
}
