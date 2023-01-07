package sample.automation.utils;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Waiter {

  private static final String WAITING_ERROR_MESSAGE = "Waiting for condition is interrupted!";

  public static <T> boolean waitFor(T item, Duration timeout, Predicate<T> condition) {
    int pollingIntervalInMs = 250;
    return waitFor(item, timeout, pollingIntervalInMs, condition);
  }

  public static <T> boolean waitFor(T item,
                                    Duration timeout,
                                    int pollingIntervalInMs,
                                    Predicate<T> condition) {
    Clock clock = Clock.systemDefaultZone();

    Instant end = clock.instant().plus(timeout);
    boolean isReady = false;
    do {
      try {
        isReady = condition.test(item);
        if (isReady) {
          return isReady;
        }
        Thread.sleep(pollingIntervalInMs);
      } catch (Exception ex) {
        try {
          Thread.sleep(pollingIntervalInMs);
        } catch (InterruptedException e) {
          log.info(WAITING_ERROR_MESSAGE, e);
          Thread.currentThread().interrupt();
        }
      }
    }
    while (clock.instant().compareTo(end) <= 0);

    return isReady;
  }

  public static <W, T> boolean waitFor(W driver,
                                       T element,
                                       Duration timeout,
                                       BiPredicate<W, T> condition) {
    Clock clock = Clock.systemDefaultZone();
    int pollingIntervalInMs = 250;

    Instant end = clock.instant().plus(timeout);
    boolean isReady = false;
    do {
      try {
        isReady = condition.test(driver, element);
        if (isReady) {
          return isReady;
        }
        Thread.sleep(pollingIntervalInMs);
      } catch (Exception ex) {
        try {
          Thread.sleep(pollingIntervalInMs);
        } catch (InterruptedException e) {
          log.info(WAITING_ERROR_MESSAGE, e);
          Thread.currentThread().interrupt();
        }
      }
    }
    while (clock.instant().compareTo(end) <= 0);
    return isReady;
  }
}
