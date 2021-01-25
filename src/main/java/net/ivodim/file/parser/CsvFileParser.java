package net.ivodim.file.parser;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.ivodim.file.parser.appenders.Appender;
import net.ivodim.file.parser.appenders.AppenderFactory;
import net.ivodim.file.parser.utils.FileInfo;
import net.ivodim.file.parser.utils.Utils;

public class CsvFileParser implements FileParser {

  private final String fileName;
  private final String destinationFolder;
  private final Set<String> selectColumns;
  private final String groupByColumn;
  private final AppenderFactory appenderFactory;

  @Inject
  public CsvFileParser(
      @Named("source.file.location") String fileName,
      @Named("destination.folder") String destinationFolder,
      @Named("select.columns.set") Set<String> selectColumns,
      @Named("group.by.column") String groupByColumn,
      AppenderFactory appenderFactory) {
    this.fileName = fileName;
    this.destinationFolder = destinationFolder;
    this.selectColumns = selectColumns;
    this.groupByColumn = groupByColumn;
    this.appenderFactory = appenderFactory;
  }

  @Override
  public void parse(BufferedReader reader) throws IOException {
    final Map<String, Appender> appenderMap = new HashMap<>();
    try {
      final FileInfo fileInfo = getFileInfo(reader);
      String line = reader.readLine();
      while (line != null) {
        final String[] lineSplit = line.split(",");
        final List<String> values = getLineValues(fileInfo.getColumnsByIndex(), lineSplit);

        final String groupByColumnValue = lineSplit[fileInfo.getGroupByColumnIndex()];
        final Appender appender = getOrCreateAppender(appenderMap, fileInfo, groupByColumnValue);

        appender.writeLine(values);

        line = reader.readLine();
      }
    } finally {
      for (Appender appender : appenderMap.values()) {
        try {
          appender.close();
        } catch (Exception exception) {
          // nothing to do
        }
      }
    }
  }

  private Appender getOrCreateAppender(
      Map<String, Appender> appenderMap, FileInfo fileInfo, String groupByColumnValue) {
    final String appenderName =
        Utils.getAppenderName(destinationFolder, groupByColumnValue, fileName);
    Appender appender = appenderMap.get(appenderName);
    if (appender == null) {
      appender = appenderFactory.create(appenderName, fileInfo.getColumnNames());
      appenderMap.put(appenderName, appender);
    }
    return appender;
  }

  private List<String> getLineValues(Map<Integer, String> columnsByIndex, String[] columnValues) {
    final List<String> values = new ArrayList<>();
    for (int i = 0; i < columnValues.length; i++) {
      if (columnsByIndex.containsKey(i)) {
        values.add(columnValues[i]);
      }
    }
    return values;
  }

  private FileInfo getFileInfo(BufferedReader reader) throws IOException {
    final Map<Integer, String> columnsByIndex = new HashMap<>();
    Integer groupByColumnIndex = null;
    final List<String> columnNames = new ArrayList<>();
    final String[] columnNamesArr = reader.readLine().replaceAll("\\uFEFF", "").split(",");
    for (int i = 0; i < columnNamesArr.length; i++) {
      if (selectColumns.contains(columnNamesArr[i].trim())) {
        columnsByIndex.put(i, columnNamesArr[i]);
        columnNames.add(columnNamesArr[i]);
      }

      if (columnNamesArr[i].equals(groupByColumn)) {
        groupByColumnIndex = i;
      }
    }

    if (groupByColumnIndex == null) {
      throw new RuntimeException("Group by column => " + groupByColumn + " not found");
    }

    return new FileInfo(columnsByIndex, columnNames, groupByColumnIndex);
  }
}
