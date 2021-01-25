package net.ivodim.file.parser.appenders;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface Appender extends Closeable {

  void writeLine(List<String> values) throws IOException;
}
