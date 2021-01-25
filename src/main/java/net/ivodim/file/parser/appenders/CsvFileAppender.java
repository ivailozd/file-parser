package net.ivodim.file.parser.appenders;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import net.ivodim.file.parser.writers.FileWriterFactory;

public class CsvFileAppender extends AbstractFileAppender {

  public CsvFileAppender(String name, List<String> columnNames, FileWriterFactory writerFactory)
      throws IOException {
    super(name, ".scv", columnNames, writerFactory);
  }

  @Override
  protected void startNewFile(BufferedWriter writer) throws IOException {
    getWriter().write(String.join(",", getColumnNames()));
    getWriter().write(System.lineSeparator());
  }

  @Override
  public void writeLine(List<String> values) throws IOException {
    getWriter().write(String.join(",", values));
    getWriter().write(System.lineSeparator());
  }
}
