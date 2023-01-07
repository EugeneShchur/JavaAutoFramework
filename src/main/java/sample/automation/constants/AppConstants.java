package sample.automation.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

  public static final String TOKEN_COOKIE_NAME = "token";
  public static final String USER_ID_COOKIE_NAME = "userID";

  public class NavBarSections {
    public static final String ELEMENTS_NAV_BAR_SECTION = "Elements";
    public static final String FORMS_NAV_BAR_SECTION = "Forms";
    public static final String BOOK_STORE_APP_NAV_BAR_SECTION = "Book Store Application";
  }

  public class BookStoreAppNavBarOptions {
    public static final String LOGIN_NAV_BAR_OPTION = "Login";
    public static final String BOOK_STORE_NAV_BAR_OPTION = "Book Store";
    public static final String PROFILE_NAV_BAR_OPTION = "Profile";
  }
}
