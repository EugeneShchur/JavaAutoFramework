package sample.automation.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateHelper {

  private static final String PATTERN_DATE_IN_LOG_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
  private static final String PATTERN_DATE_IN_FILENAME_FORMAT = "yyyy-MM-dd_HH-mm-ss";
  private static final String PATTERN_UI_FORMAT_MONTH_DAY_YEAR_TIME = "MMM d',' yyyy '|' HH:mm:ss";
  private static final String PATTERN_UI_FORMAT_MONTH_DAY_LONG_YEAR = "MMM d',' yyyy";
  private static final String PATTERN_API_FORMAT_YEAR_MONTH_DAY_TIME = "yyyy-MM-dd'T'HH:mm:ss";

  public static ZonedDateTime getZonedDateWithShiftOfDays(int days) {
    return ZonedDateTime.now().plusDays(days);
  }

  public static ZonedDateTime getZonedDateTimeFromEpochMillis(long epochMillisValue, String browserTimeZone) {
    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMillisValue),
                                   ZoneId.of(browserTimeZone));
  }

  public static String formatTimestampIntoLogDate(long timeInMillis) {
    return new SimpleDateFormat(PATTERN_DATE_IN_LOG_FORMAT).format(timeInMillis);
  }

  public static String formatTimestampIntoFilenameDate(long timeInMillis) {
    return new SimpleDateFormat(PATTERN_DATE_IN_FILENAME_FORMAT).format(timeInMillis);
  }

  public static String formatTimestampIntoUiMonthDayYearTime(long timeInMillis) {
    return new SimpleDateFormat(PATTERN_UI_FORMAT_MONTH_DAY_YEAR_TIME).format(timeInMillis);
  }

  public static String formatZonedDateTimeToUiMonthDayYearTime(ZonedDateTime dateTime, String browserTimeZone) {
    return dateTime.format(DateTimeFormatter.ofPattern(PATTERN_UI_FORMAT_MONTH_DAY_YEAR_TIME)
                                            .withZone(ZoneId.of(browserTimeZone)));
  }

  public static String formatZonedDateTimeToUiMonthDayYear(ZonedDateTime dateTime, String browserTimeZone) {
    return dateTime.format(DateTimeFormatter.ofPattern(PATTERN_UI_FORMAT_MONTH_DAY_LONG_YEAR)
                                            .withZone(ZoneId.of(browserTimeZone)));
  }

  public static String convertZonedDateTimeToApiDateTime(ZonedDateTime dateTime, String browserTimeZone) {
    return dateTime.format(DateTimeFormatter.ofPattern(PATTERN_API_FORMAT_YEAR_MONTH_DAY_TIME)
                                            .withZone(ZoneId.of(browserTimeZone)));
  }
}
