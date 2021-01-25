package net.ivodim.file.parser.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.ivodim.file.parser.modules.FileParserModule;

public class FileParserLauncher {

  public static void main(String[] args) {

    final Injector injector = Guice.createInjector(new FileParserModule());

    final FileParserRunner fileParser = injector.getInstance(FileParserRunner.class);
    fileParser.start();
  }
}
