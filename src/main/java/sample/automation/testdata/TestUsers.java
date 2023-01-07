package sample.automation.testdata;

import static sample.automation.webdriver.MyWebDriverManager.props;

import sample.automation.dataobjects.User;

public class TestUsers {

  public static final User TEST_USER_ONE = User.builder()
                                               .username("user1")
                                               .password(props.adminPassword())
                                               .build();

  public static final User TEST_USER_TWO = User.builder()
                                               .username("user2")
                                               .password(props.adminPassword())
                                               .build();
}
