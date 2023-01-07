package sample.automation.testdata;

import sample.automation.dataobjects.Book;

public class TestBooks {

  public static final Book TEST_BOOK_ONE = Book.builder()
                                               .isbn("9781593275846")
                                               .build();

  public static final Book TEST_BOOK_TWO = Book.builder()
                                               .isbn("9781449337711")
                                               .build();

}
