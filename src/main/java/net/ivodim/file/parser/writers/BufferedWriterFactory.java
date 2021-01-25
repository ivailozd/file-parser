package net.ivodim.file.parser.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

public class BufferedWriterFactory implements FileWriterFactory {
  @Override
  public BufferedWriter create(File file) throws IOException {
    return new BufferedWriter(new FileWriter(file, Charset.forName("UTF-8"), true));
  }
}
