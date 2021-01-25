package net.ivodim.file.parser.appenders;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import net.ivodim.file.parser.writers.FileWriterFactory;

public abstract class AbstractFileAppender implements Appender {

  private final List<String> columnNames;
  private final BufferedWriter writer;

  public AbstractFileAppender(
      String name, String fileExtension, List<String> columnNames, FileWriterFactory writerFactory)
      throws IOException {
    this.columnNames = columnNames;
    final File file = new File(name + fileExtension);
    if (file.createNewFile()) {
      writer = writerFactory.create(file);
      startNewFile(writer);
    } else {
      writer = writerFactory.create(file);
    }
  }

  protected abstract void startNewFile(BufferedWriter writer) throws IOException;

  @Override
  public void close() throws IOException {
    writer.flush();
    writer.close();
  }

  protected List<String> getColumnNames() {
    return columnNames;
  }

  protected BufferedWriter getWriter() {
    return writer;
  }
}
