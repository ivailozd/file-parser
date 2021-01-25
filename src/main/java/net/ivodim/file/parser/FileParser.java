package net.ivodim.file.parser;

import java.io.BufferedReader;
import java.io.IOException;

public interface FileParser {

  void parse(BufferedReader reader) throws IOException;
}
