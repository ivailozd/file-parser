package net.ivodim.file.parser.modules;

import com.google.inject.AbstractModule;
import net.ivodim.file.parser.appenders.AppenderFactory;
import net.ivodim.file.parser.csv.mocks.CsvAppenderFactoryMock;

public class TestCsvFileParserModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(AppenderFactory.class).to(CsvAppenderFactoryMock.class).asEagerSingleton();
  }
}
