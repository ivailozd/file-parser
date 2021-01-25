package net.ivodim.file.parser.csv.mocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ivodim.file.parser.appenders.Appender;
import net.ivodim.file.parser.appenders.AppenderFactory;

public class CsvAppenderFactoryMock implements AppenderFactory {

  private final Map<String, CsvAppenderMock> csvAppenderMockMap = new HashMap<>();

  @Override
  public Appender create(String appenderName, List<String> columnNames) {
    CsvAppenderMock appender = csvAppenderMockMap.get(appenderName);
    if (appender == null) {
      appender = new CsvAppenderMock(appenderName);
      csvAppenderMockMap.put(appenderName, appender);
    }

    return appender;
  }

  public Map<String, CsvAppenderMock> getCsvAppenderMockMap() {
    return csvAppenderMockMap;
  }
}
