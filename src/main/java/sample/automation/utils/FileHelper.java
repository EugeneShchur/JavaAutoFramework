package sample.automation.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileHelper {

  public static String getFileNameWithoutExtension(File file) {
    return FilenameUtils.removeExtension(file.getName());
  }

  public static void deleteFileIfExists(File file) {
    if (file.exists()) {
      try {
        Files.delete(file.toPath());
      } catch (IOException e) {
        log.error(e.getMessage());
      }
    }
  }

  public static void saveListIntoCsvFile(List<String[]> dataList, String fileName) {
    try (CsvListWriter listWriter = new CsvListWriter(new FileWriter(fileName),
                                                      new CsvPreference.Builder('"', '\n', "").build())) {
      listWriter.write(dataList.stream()
                               .map(array -> String.join(",", array))
                               .collect(Collectors.toList()));
    } catch (IOException e) {
      log.info(e.getMessage());
    }
  }

  public static void saveListIntoFile(List<String> dataList, String fileName) {
    try (FileWriter fileWriter = new FileWriter(fileName)) {
      fileWriter.write(String.join("\n", dataList));
      log.info(fileName + " file is saved");
    } catch (IOException e) {
      log.info(e.getMessage());
    }
  }

  public static boolean isFilesEqual(File firstFile, File secondFile) {
    try {
      return FileUtils.contentEquals(firstFile, secondFile);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static void cleanDirectoryIfExist(String folderName) throws IOException {
    File directory = new File(folderName);
    if (directory.exists() && directory.isDirectory()) {
      FileUtils.cleanDirectory(directory);
    }
  }
}
