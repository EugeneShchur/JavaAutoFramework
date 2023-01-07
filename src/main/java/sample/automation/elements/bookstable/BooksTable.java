package sample.automation.elements.bookstable;

import io.qameta.atlas.webdriver.ElementsCollection;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Param;
import sample.automation.atlas.AtlasWebElement;

public interface BooksTable extends AtlasWebElement<BooksTable> {

  @FindBy(".//div[@role='row' and .//a]")
  ElementsCollection<BookDataRow> allBooksList();

  @FindBy(".//div[@role='row' and .//a[contains(@href, 'book={{ isbn }}')]]")
  BookDataRow bookByIsbn(@Param("isbn") String isbn);

  @FindBy(".//div[@role='row' and .//a[text()='{{ title }}']]")
  BookDataRow bookByTitle(@Param("title") String title);
}
