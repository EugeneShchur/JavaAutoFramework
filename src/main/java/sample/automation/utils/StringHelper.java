package sample.automation.utils;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.http.Consts.UTF_8;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebElement;
import org.testng.ITestNGMethod;
import org.testng.SkipException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringHelper {

  public static final String NON_BREAKABLE_SPACES_MATCH_REGEX = "(^\\h*)|(\\h*$)";
  public static final String EMPTY_STRING = "";
  public static final String SPACE_STRING = " ";
  public static final String HTML_ENCODED_SPACE = "\u00a0";
  public static final String NEW_LINE_STRING = "\n";
  private static final String DECIMAL_FORMAT = "#.##";

  public static String getClearTextFromHtmlFragment(WebElement elementWithHtmlFragment) {
    Document htmlFragment =
        Jsoup.parseBodyFragment(elementWithHtmlFragment.getAttribute("innerHTML"));
    htmlFragment.select("div").remove();
    htmlFragment.select("span").remove();
    htmlFragment.select("input").remove();
    return htmlFragment.text();
  }

  public static String replaceSpacesWithT(String targetText) {
    return targetText.replace(SPACE_STRING, "T");
  }

  public static String obtainIdFromUrl(String url) {
    return url.substring(url.lastIndexOf('/') + 1);
  }

  public static String getStringDecimalFormattedValue(String number) {
    return new DecimalFormat(DECIMAL_FORMAT).format(Double.parseDouble(number)).replace(",", ".");
  }

  public static String getStringDecimalFormattedValueWithMathRound(String number) {
    return new DecimalFormat(DECIMAL_FORMAT).format(Math.round(Double.parseDouble(number)));
  }

  public static List<String> addMeIntoMatchedListElement(List<String> list, String matchedElement) {
    List<String> updatedList = new ArrayList<>();
    list.forEach(option -> {
      if (option.equals(matchedElement)) {
        option = option.concat(" (me)");
      }
      updatedList.add(option);
    });
    return updatedList;
  }

  public static String extractFirstCharFromEveryWordInString(String stringToConvert) {
    String firstChars = EMPTY_STRING;
    for (String firstChar : stringToConvert.split(" ")) {
      firstChars = firstChars.concat(String.valueOf(firstChar.charAt(0)));
    }
    return firstChars;
  }

  public static String getRegexGroupForPatternInString(String textToParse, int groupNumber, Pattern regexPattern) {
    Matcher matcher = regexPattern.matcher(textToParse);
    if (matcher.find()) {
      return matcher.group(groupNumber);
    }
    throw new UnsupportedOperationException("There is no matches for this regex");
  }

  public static String removeSpaces(String text) {
    return text.replace(SPACE_STRING, EMPTY_STRING);
  }

  public static String replaceSingleQuotesWithDouble(String text) {
    return text.replace("'", "\"");
  }

  public static String normalizeSpacesAndRemoveLineBreaks(String text) {
    return replaceWhitespacesWithSingleAndTrim(
        replaceHtmlBreaksWithSpace(
            replaceNewLineWithSpace(
                replaceAnyTypeOfWhitespacesWithWhitespace(removeNonBreakableSpaces(text))
            )
        )
    );
  }

  /**
   * this removes leading and trailing whitespace from the input string and also replaces any number of whitespaces
   * inside with a single whitespace
   */
  public static String replaceWhitespacesWithSingleAndTrim(String text) {
    return text.replaceAll("^ +| +$|( )+", "$1");
  }

  public static String replaceHtmlBreaksWithSpace(String text) {
    return text.replace("<br>", SPACE_STRING);
  }

  public static String replaceNewLineWithSpace(String text) {
    return text.replaceAll(NEW_LINE_STRING, SPACE_STRING);
  }

  public static String replaceAnyTypeOfWhitespacesWithWhitespace(String text) {
    return text.replaceAll("\\p{Z}", SPACE_STRING);
  }

  public static String removeNonBreakableSpaces(String text) {
    return text.replaceAll(NON_BREAKABLE_SPACES_MATCH_REGEX, EMPTY_STRING)
               .replace(HTML_ENCODED_SPACE, EMPTY_STRING);
  }

  public static List<String> splitCommasStringToListWithTrim(String text) {
    return Arrays.stream(text.split(","))
                 .map(String::trim)
                 .collect(Collectors.toList());
  }

  public static String convertListToCommasSeparatedText(List<String> listToConvert) {
    return String.join(", ", listToConvert);
  }

  public static List<String> removeNonNumericNonAlphabetic(List<String> stringsList) {
    return stringsList.stream()
                      .map(StringHelper::removeNonNumericNonAlphabetic)
                      .collect(Collectors.toList());
  }

  public static String removeNonNumericNonAlphabetic(String text) {
    return text.replaceAll("[^a-zA-Z0-9]", EMPTY_STRING);
  }

  public static List<String> removeSubstrings(List<String> targetStringList, String... removedSubStrings) {
    return targetStringList.stream()
                           .map(str -> removeSubstrings(str, removedSubStrings))
                           .collect(Collectors.toList());
  }

  public static String removeSubstrings(String targetString, String... removedSubStrings) {
    return Arrays.stream(removedSubStrings)
                 .reduce(targetString, (subStringToRemove, accumulatedString) ->
                     accumulatedString.replace(subStringToRemove, EMPTY_STRING));
  }

  public static List<String> listToLowerCase(List<String> stringsList) {
    return stringsList.stream()
                      .map(String::toLowerCase)
                      .collect(Collectors.toList());
  }

  public static String constructPathWithSpecificSeparator(String separator, String first, String... more) {
    String path = Paths.get(first, more).toString();
    return path.replace("\\", separator);
  }

  public static String getLastPathParameter(String path) {
    String[] pathParameters = path.split("/");
    return pathParameters[pathParameters.length - 1];
  }

  public static String[] splitStringOnPartsWithLength(String textToSplit, int lengthOfParts) {
    return textToSplit.split("(?<=\\G.{" + lengthOfParts + "})");
  }

  public static String getMainPartOfMethodDescription(ITestNGMethod method) {
    String fullDescription = method.getDescription();
    String splitMark = " >> ";
    int indexOfSplitMark = fullDescription.indexOf(splitMark);
    return indexOfSplitMark > 0
           ? fullDescription.substring(0, indexOfSplitMark + 4)
           : fullDescription + splitMark;
  }

  public static String constructFilePath(String directory, String fileName) {
    return String.format("%s%s%s", directory, File.separator, fileName);
  }

  public static String concatFileName(String downloadedFileName, String downloadedFileExtension) {
    return String.format("%s.%s", downloadedFileName, downloadedFileExtension);
  }

  public static String addPercentToValue(Number number) {
    return number + "%";
  }

  public static String formatTwoValuesAsFraction(Number numerator, Number denominator) {
    return String.format("%s / %s", numerator, denominator);
  }

  public static List<String> filterToContain(List<String> stringsList, String expectedStringToContain) {
    return stringsList.stream()
                      .filter(item -> containsIgnoreCase(item, expectedStringToContain))
                      .collect(Collectors.toList());
  }

  public static int getNumberOfMatchesInStringsList(List<String> itemsList, String expectedStringToContain) {
    return itemsList.stream()
                    .mapToInt(string -> countMatches(string.toLowerCase(), expectedStringToContain.toLowerCase()))
                    .sum();
  }

  public static double getFirstNumberFromString(String str) {
    return Arrays.stream(str.replaceAll("[^0-9.]+", SPACE_STRING)
                            .trim()
                            .split(SPACE_STRING))
                 .mapToDouble(Double::parseDouble)
                 .toArray()[0];
  }

  public static List<String> capitaliseStringsList(List<String> stringsList) {
    return stringsList.stream()
                      .map(name -> WordUtils.capitalize(name, ' ', '(', '.'))
                      .collect(Collectors.toList());
  }

  public static List<String> takeOnlyFirstWordFromStringList(List<String> stringsList) {
    return stringsList.stream()
                      .map(name -> name.split(SPACE_STRING)[0])
                      .collect(Collectors.toList());
  }

  public static List<String> replaceNullsInStringsList(List<String> stringsList) {
    return stringsList.stream()
                      .map(text -> text.replace("null", SPACE_STRING))
                      .collect(Collectors.toList());
  }

  public static String trimLastCharacterIfSuchPresent(String initialString, char lastCharacterToTrim) {
    if (initialString.charAt(initialString.length() - 1) == lastCharacterToTrim) {
      return initialString.substring(0, initialString.length() - 1);
    }
    return initialString;
  }

  public static String replaceUrlEncodedChars(String textWithUrlEncodedChars) {
    if (isNotEmpty(textWithUrlEncodedChars)) {
      return textWithUrlEncodedChars.replace("&amp;", "&");
    }
    return textWithUrlEncodedChars;
  }

  public static String encodeStringToUrl(String text) {
    try {
      return URLEncoder.encode(text, UTF_8.toString())
                       .replace("+", "%20");
    } catch (UnsupportedEncodingException exception) {
      exception.printStackTrace();
      throw new SkipException("Test is skipped because URL encoding is failed for text: " + text);
    }
  }

  public static String getDomainPartFromUrl(String fullUrl) {
    return getRegexGroupForPatternInString(fullUrl, 1, Pattern.compile("(https:\\/\\/[^:^\\/]*)"));
  }

  public static String getSubDomainPartFromUrl(String fullUrl) {
    return getRegexGroupForPatternInString(fullUrl, 1, Pattern.compile("https:\\/\\/([^:^.]*)\\."));
  }

  public static String replaceUrlEncodedSpacesAndBraces(String textWithUrlEncodedSpace) {
    return textWithUrlEncodedSpace.replace(SPACE_STRING, "%20")
                                  .replace("(", "%28")
                                  .replace(")", "%29");
  }

  public static String appendRandomAlphanumericForString(String initialString) {
    return initialString + RandomStringUtils.randomAlphanumeric(10);
  }

  public static String addPluralSuffixIfMany(Number numberOfItems) {
    return numberOfItems.intValue() > 1
           ? "s"
           : EMPTY_STRING;
  }
}
