package sample.automation.dataobjects;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sample.automation.constants.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

  private String userId;
  private String username;
  private String password;
  private UserRole role;
  private List<Book> books;

  @Override
  public String toString() {
    return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
        .append("username", username)
        .append("role", role)
        .toString();
  }

  public User getNewCopyOfTestUser() {
    return User.builder()
               .userId(userId)
               .username(username)
               .password(password)
               .role(role)
               .books(books)
               .build();
  }
}
