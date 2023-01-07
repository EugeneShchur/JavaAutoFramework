package sample.automation.dataobjects;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

  private String isbn;
  private String title;
  private String subTitle;
  private String author;
  private String publish_date; // date format "2023-01-06T19:40:10.061Z"
  private String publisher;
  private int pages;
  private String description;
  private String website;

  @Override
  public String toString() {
    return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
        .append("isbn", isbn)
        .append("title", title)
        .append("author", author)
        .toString();
  }

  @Override
  public boolean equals(Object bookToCompare) {
    if (!(bookToCompare instanceof Book)) {
      return false;
    }
    return isbn.equals(((Book) bookToCompare).isbn);
  }

  public Book getNewCopyOfTestBook() {
    return Book.builder()
               .isbn(isbn)
               .title(title)
               .subTitle(subTitle)
               .author(author)
               .publish_date(publish_date)
               .publisher(publisher)
               .pages(pages)
               .description(description)
               .website(website)
               .build();
  }
}
