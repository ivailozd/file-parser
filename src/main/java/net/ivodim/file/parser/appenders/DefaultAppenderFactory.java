package net.ivodim.file.parser.appenders;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.io.IOException;
import java.util.List;
import net.ivodim.file.parser.writers.FileWriterFactory;

public class DefaultAppenderFactory implements AppenderFactory {

  private final String outputFormat;
  private final FileWriterFactory fileWriterFactory;

  @Inject
  public DefaultAppenderFactory(
      @Named("output.format") String outputFormat, FileWriterFactory fileWriterFactory) {
    this.outputFormat = outputFormat;
    this.fileWriterFactory = fileWriterFactory;
  }

  @Override
  public Appender create(String appenderName, List<String> columnNames) {
    try {
      if ("csv".equalsIgnoreCase(outputFormat)) {
        return new CsvFileAppender(appenderName, columnNames, fileWriterFactory);
      } else if ("xml".equalsIgnoreCase(outputFormat)) {
        return new XmlFileAppender(appenderName, columnNames, fileWriterFactory);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    throw new RuntimeException("Unknown output format => " + outputFormat);
  }
}
