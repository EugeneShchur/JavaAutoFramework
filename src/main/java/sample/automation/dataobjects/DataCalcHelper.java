package sample.automation.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataCalcHelper {

  public static List<String> getBooksTitles(List<Book> booksList) {
    return booksList.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());
  }

  public static boolean isBookWithIsbnPresentInList(List<Book> booksList, String isbnToCheck) {
    return booksList.stream()
                    .anyMatch(book -> book.getIsbn()
                                          .equals(isbnToCheck));
  }
}
