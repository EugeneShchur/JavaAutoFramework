package sample.automation.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Environment {
  DEV("https://demoqa.com",
      "https://demoqa.com",
      "https://some.other-api.com",
      "any other env specific value"),
  STAGE("example",
        "example",
        "example",
        "example"),
  PROD("example",
       "example",
       "example",
       "example");

  @Getter
  private final String webSiteUrl;

  @Getter
  private final String mainApiUrl;

  @Getter
  private final String otherApiUrl;

  @Getter
  private final String apiKey;
}
