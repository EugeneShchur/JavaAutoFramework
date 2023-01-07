package sample.automation.elements.navbar;

import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Param;
import sample.automation.atlas.AtlasWebElement;

public interface NavigationBar extends AtlasWebElement<NavigationBar> {

  @FindBy("//div[@class='element-group' and .//div[@class='header-text' and text()='{{ sectionName }}']]")
  NavBarSection navBarSection(@Param("sectionName") String sectionName);
}
