package net.ivodim.file.parser.appenders;

import java.util.List;

public interface AppenderFactory {
  Appender create(String appenderName, List<String> columnNames);
}
