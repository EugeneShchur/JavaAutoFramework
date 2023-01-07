package sample.automation.steps.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import sample.automation.steps.ui.BaseSteps;

@Log4j2
@NoArgsConstructor
public class BaseApiSteps extends BaseApiRequests {

  @Getter
  @Setter
  private BaseSteps uiSteps;

  private AccountApiSteps onAccountApi;
  private BookStoreApiSteps onBookStoreApi;
  private SomeOtherApiSteps onSomeOtherApi;

  public AccountApiSteps onAccountApi() {
    if (onAccountApi == null) {
      onAccountApi = new AccountApiSteps(bearerHeader);
    }
    return onAccountApi;
  }

  public BookStoreApiSteps onBookStoreApi() {
    if (onBookStoreApi == null) {
      onBookStoreApi = new BookStoreApiSteps(bearerHeader);
    }
    return onBookStoreApi;
  }

  public SomeOtherApiSteps onSomeOtherApi() {
    if (onSomeOtherApi == null) {
      onSomeOtherApi = new SomeOtherApiSteps(bearerHeader);
    }
    return onSomeOtherApi;
  }
}
