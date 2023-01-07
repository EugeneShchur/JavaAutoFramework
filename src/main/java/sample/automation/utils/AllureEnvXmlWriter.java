package sample.automation.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.ImmutableMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AllureEnvXmlWriter {

  public static void saveValuesIntoAllureEnvironmentXml(ImmutableMap<String, String> environmentValuesSet, String pathToCreatedFile) {
    try {
      // generating xml content:
      Document document = DocumentBuilderFactory.newInstance()
                                                .newDocumentBuilder()
                                                .newDocument();
      Element environment = document.createElement("environment");
      document.appendChild(environment);
      environmentValuesSet.forEach((k, v) -> {
        Element parameter = document.createElement("parameter");
        Element key = document.createElement("key");
        Element value = document.createElement("value");
        key.appendChild(document.createTextNode(k));
        value.appendChild(document.createTextNode(v));
        parameter.appendChild(key);
        parameter.appendChild(value);
        environment.appendChild(parameter);
      });
      // Write the content into xml file:
      StreamResult result = new StreamResult(new File(pathToCreatedFile + "environment.xml"));
      TransformerFactory.newInstance()
                        .newTransformer()
                        .transform(new DOMSource(document), result);
      log.info("Allure environment.xml is saved into " + pathToCreatedFile);
    } catch (ParserConfigurationException | TransformerException exception) {
      log.error(exception.getMessage());
    }
  }
}
