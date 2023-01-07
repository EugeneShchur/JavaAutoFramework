package sample.automation.pages;

import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Param;
import sample.automation.elements.BookDetailsField;
import sample.automation.elements.Button;

public interface BookStorePage extends BaseBookStorePage {

  @FindBy("//div[@id='{{ fieldName }}-wrapper']")
  BookDetailsField bookDetailsFor(@Param("fieldName") String fieldName);

  @FindBy("//div[contains(@class, 'text-left')]//button[@id='addNewRecordButton']")
  Button backToBookStoreButton();

  @FindBy("//div[contains(@class, 'text-right')]//button[@id='addNewRecordButton']")
  Button addToCollectionButton();
}
