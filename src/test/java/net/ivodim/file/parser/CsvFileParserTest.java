package net.ivodim.file.parser;

import com.google.inject.Inject;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import net.ivodim.file.parser.appenders.AppenderFactory;
import net.ivodim.file.parser.csv.mocks.CsvAppenderFactoryMock;
import net.ivodim.file.parser.csv.mocks.CsvAppenderMock;
import net.ivodim.file.parser.modules.CsvParserTestModuleFactory;
import net.ivodim.file.parser.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(moduleFactory = CsvParserTestModuleFactory.class)
public class CsvFileParserTest {

  @Inject private AppenderFactory appenderFactory;

  @DataProvider(name = "data")
  public Object[][] getData() {
    List<String> columnNames =
        List.of(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString());
    List<List<String>> valueRows = new ArrayList<>();
    for (int i = 0; i < ThreadLocalRandom.current().nextInt(10, 100); i++) {
      final String groupByValue = UUID.randomUUID().toString();
      for (int j = 0; j < ThreadLocalRandom.current().nextInt(100, 1000); j++) {
        valueRows.add(
            List.of(groupByValue, UUID.randomUUID().toString(), UUID.randomUUID().toString()));
      }
    }

    Collections.shuffle(valueRows);
    return new Object[][] {{columnNames, valueRows}};
  }

  @Test(dataProvider = "data")
  public void test(List<String> columnNames, List<List<String>> valueLines) throws Exception {

    final Set<String> selectColumns = new HashSet<>(columnNames);

    final String fileName = UUID.randomUUID().toString();
    final String destinationFolder = UUID.randomUUID().toString();
    final CsvFileParser csvFileParser =
        new CsvFileParser(
            fileName,
            destinationFolder,
            selectColumns,
            columnNames.iterator().next(),
            appenderFactory);

    final StringBuilder builder = new StringBuilder();
    builder.append(String.join(",", columnNames)).append(System.lineSeparator());
    for (List<String> values : valueLines) {
      builder.append(String.join(",", values)).append(System.lineSeparator());
    }

    final BufferedReader reader = new BufferedReader(new StringReader(builder.toString()));
    csvFileParser.parse(reader);

    final Map<String, CsvAppenderMock> appenderMap =
        ((CsvAppenderFactoryMock) appenderFactory).getCsvAppenderMockMap();

    for (List<String> line : valueLines) {
      final String appenderName =
          Utils.getAppenderName(destinationFolder, line.iterator().next(), fileName);
      final CsvAppenderMock appenderMock = appenderMap.get(appenderName);
      Assert.assertNotNull(appenderMock);
      Assert.assertTrue(appenderMock.getLines().remove(line));
    }

    for (CsvAppenderMock appenderMock : appenderMap.values()) {
      Assert.assertTrue(appenderMock.getLines().isEmpty());
    }
  }
}
