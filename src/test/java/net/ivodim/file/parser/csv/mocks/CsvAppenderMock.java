package net.ivodim.file.parser.csv.mocks;

import java.util.ArrayList;
import java.util.List;
import net.ivodim.file.parser.appenders.Appender;

public class CsvAppenderMock implements Appender {

  private final String name;
  private final List<List<String>> lines = new ArrayList<>();

  public CsvAppenderMock(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void writeLine(List<String> values) {
    lines.add(values);
  }

  @Override
  public void close() {}

  public List<List<String>> getLines() {
    return lines;
  }
}
