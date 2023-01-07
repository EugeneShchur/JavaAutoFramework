package sample.automation.testng;

import org.testng.annotations.BeforeClass;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import sample.automation.dataobjects.User;
import sample.automation.steps.api.BaseApiSteps;

@Log4j2
@Getter
public class BaseApiTest extends AbstractTestLogger {

  protected BaseApiSteps apiSteps;

  @BeforeClass(alwaysRun = true)
  public void initBaseSteps() {
    apiSteps = new BaseApiSteps();
  }

  public void loginAsUserAndSetAuthData(User user) {
    apiSteps.onAccountApi()
            .loginAs(user);
    apiSteps.setAuthToken(apiSteps.onAccountApi().getToken());
  }
}
