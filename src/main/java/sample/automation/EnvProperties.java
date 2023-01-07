package sample.automation;

import ru.qatools.properties.Property;
import ru.qatools.properties.Resource;
import sample.automation.constants.Environment;

@Resource.Classpath("env.properties")
public interface EnvProperties {

  @Property("envToRunOn")
  Environment envToRunOn();

  @Property("browserName")
  String browserName();

  @Property("browserVersion")
  String browserVersion();

  @Property("isHeadless")
  Boolean isHeadless();

  @Property("windowDimension")
  String windowDimension();

  @Property("seleniumEnvironment")
  String seleniumEnvironment();

  @Property("remoteWebDriverUrl")
  String remoteWebDriverUrl();

  @Property("remoteWebDriverPlatform")
  String remoteWebDriverPlatform();

  @Property("adminPassword")
  String adminPassword();
}
