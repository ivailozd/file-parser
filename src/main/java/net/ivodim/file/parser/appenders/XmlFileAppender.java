package net.ivodim.file.parser.appenders;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import net.ivodim.file.parser.writers.FileWriterFactory;

public class XmlFileAppender extends AbstractFileAppender {

  public XmlFileAppender(String name, List<String> columnNames, FileWriterFactory writerFactory)
      throws IOException {
    super(name, ".xml", columnNames, writerFactory);
  }

  @Override
  protected void startNewFile(BufferedWriter writer) throws IOException {
    getWriter().write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    getWriter().write(System.lineSeparator());
    getWriter().write("<invoices>");
    getWriter().write(System.lineSeparator());
  }

  @Override
  public void writeLine(List<String> values) throws IOException {
    final List<String> columnNames = getColumnNames();
    if (columnNames.size() != values.size()) {
      throw new RuntimeException(
          "Columns and values must have same size. Columns => "
              + columnNames
              + ", values => "
              + values);
    }

    final BufferedWriter writer = getWriter();
    writer.write("  <invoice>");
    writer.write(System.lineSeparator());
    for (int i = 0; i < columnNames.size(); i++) {
      writer.write("    <" + columnNames.get(i) + ">");
      writer.write(values.get(i));
      writer.write("</" + columnNames.get(i) + ">");
      writer.write(System.lineSeparator());
    }

    writer.write("  </invoice>");
    writer.write(System.lineSeparator());
  }

  @Override
  public void close() throws IOException {
    getWriter().write("</invoices>");
    super.close();
  }
}
