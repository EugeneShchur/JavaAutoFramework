package sample.automation.pages;

import io.qameta.atlas.webdriver.extension.FindBy;
import sample.automation.elements.Button;

public interface ProfilePage extends BaseBookStorePage {

  @FindBy("//button[@id='gotoStore']")
  Button goToBookStoreButton();
}
