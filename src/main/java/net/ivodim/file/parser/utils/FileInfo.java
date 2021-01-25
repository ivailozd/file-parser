package net.ivodim.file.parser.utils;

import java.util.List;
import java.util.Map;

public class FileInfo {

  private final Map<Integer, String> columnsByIndex;
  private final List<String> columnNames;
  private final int groupByColumnIndex;

  public FileInfo(
      Map<Integer, String> columnsByIndex, List<String> columnNames, int groupByColumnIndex) {
    this.columnsByIndex = columnsByIndex;
    this.columnNames = columnNames;
    this.groupByColumnIndex = groupByColumnIndex;
  }

  public Map<Integer, String> getColumnsByIndex() {
    return columnsByIndex;
  }

  public List<String> getColumnNames() {
    return columnNames;
  }

  public int getGroupByColumnIndex() {
    return groupByColumnIndex;
  }

  @Override
  public String toString() {
    return "FileInfo{"
        + "columnsByIndex="
        + columnsByIndex
        + ", columnNames="
        + columnNames
        + ", groupByColumnIndex="
        + groupByColumnIndex
        + '}';
  }
}
