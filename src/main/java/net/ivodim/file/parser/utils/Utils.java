package net.ivodim.file.parser.utils;

public class Utils {

  public static String getAppenderName(
      String destinationFolder, String groupByColumnValue, String fileName) {
    return destinationFolder + "/" + groupByColumnValue + "_" + fileName.replace(".csv", "");
  }
}
