package sample.automation.elements.bookstable;

import io.qameta.atlas.webdriver.extension.FindBy;
import sample.automation.atlas.AtlasWebElement;
import sample.automation.atlas.HtmlElement;

public interface BookDataRow extends AtlasWebElement<BookDataRow> {

  @FindBy(".//img")
  HtmlElement bookImage();

  @FindBy(".//a")
  HtmlElement bookTitle();

  // and other BookDataRow cells
}
