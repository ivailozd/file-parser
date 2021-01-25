package net.ivodim.file.parser.modules;

import com.google.inject.Module;
import org.testng.IModuleFactory;
import org.testng.ITestContext;

public class CsvParserTestModuleFactory implements IModuleFactory {
  @Override
  public Module createModule(ITestContext iTestContext, Class<?> aClass) {
    return new TestCsvFileParserModule();
  }
}
