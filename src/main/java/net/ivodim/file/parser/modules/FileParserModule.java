package net.ivodim.file.parser.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import net.ivodim.file.parser.CsvFileParser;
import net.ivodim.file.parser.FileParser;
import net.ivodim.file.parser.appenders.AppenderFactory;
import net.ivodim.file.parser.appenders.DefaultAppenderFactory;
import net.ivodim.file.parser.launcher.FileParserRunner;
import net.ivodim.file.parser.launcher.FileParserRunnerImpl;
import net.ivodim.file.parser.writers.BufferedWriterFactory;
import net.ivodim.file.parser.writers.FileWriterFactory;

public class FileParserModule extends AbstractModule {

  @Override
  protected void configure() {
    loadProperties();
    bind(AppenderFactory.class).to(DefaultAppenderFactory.class).asEagerSingleton();
    bind(FileWriterFactory.class).to(BufferedWriterFactory.class).asEagerSingleton();
    bind(FileParser.class).to(CsvFileParser.class).asEagerSingleton();
    bind(FileParserRunner.class).to(FileParserRunnerImpl.class).asEagerSingleton();
  }

  private void loadProperties() {
    final Properties properties = new Properties();
    try (final InputStream is = this.getClass().getResourceAsStream("/file_parser.properties")) {
      properties.load(is);
      Names.bindProperties(binder(), properties);
      System.out.println("[Loaded properties =>] " + properties);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Provides
  @Named("select.columns.set")
  @Inject
  public Set<String> getSelectColumnsSet(@Named("select.columns") String selectColumnsRaw) {
    return Arrays.stream(selectColumnsRaw.split(",")).collect(Collectors.toSet());
  }
}
