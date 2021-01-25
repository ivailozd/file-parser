package net.ivodim.file.parser.launcher;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import net.ivodim.file.parser.FileParser;

public class FileParserRunnerImpl implements FileParserRunner {

  private final String fileName;
  private final String sourceFolder;
  private final FileParser fileParser;

  @Inject
  public FileParserRunnerImpl(
      @Named("source.file.location") String fileName,
      @Named("source.folder") String sourceFolder,
      FileParser fileParser) {
    this.fileName = fileName;
    this.sourceFolder = sourceFolder;
    this.fileParser = fileParser;
  }

  @Override
  public void start() {
    try (final BufferedReader reader =
        new BufferedReader(
            new FileReader(sourceFolder + "/" + fileName, Charset.forName("UTF-8")))) {
      fileParser.parse(reader);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
