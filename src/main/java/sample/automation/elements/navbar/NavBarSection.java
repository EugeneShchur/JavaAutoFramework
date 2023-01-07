package sample.automation.elements.navbar;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static sample.automation.matchers.HasAttributeMatcher.hasClassAttribute;

import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Param;
import sample.automation.atlas.AtlasWebElement;
import sample.automation.atlas.HtmlElement;

public interface NavBarSection extends AtlasWebElement<NavBarSection> {

  @FindBy(".//li[.//span[text()='{{ optionName }}']]")
  HtmlElement option(@Param("optionName") String option);

  @FindBy(".//div[contains(@class, 'element-list')]")
  HtmlElement optionsBlock();

  default NavBarSection shouldBeExpanded(boolean isExpanded) {
    String expandedClassValue = "show";
    optionsBlock()
        .should(hasClassAttribute(
            isExpanded
            ? containsString(expandedClassValue)
            : not(containsString(expandedClassValue))
        ));
    return this;
  }
}
