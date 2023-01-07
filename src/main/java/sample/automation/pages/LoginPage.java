package sample.automation.pages;

import io.qameta.atlas.webdriver.extension.FindBy;
import sample.automation.atlas.HtmlElement;
import sample.automation.elements.Button;

public interface LoginPage extends BaseBookStorePage {

  @FindBy("//form[@id='userForm']")
  HtmlElement signInForm();

  @FindBy("//input[@id='userName']")
  HtmlElement userNameInput();

  @FindBy("//input[@id='password']")
  HtmlElement passwordInput();

  @FindBy("//button[@id='login']")
  Button loginButton();
}
