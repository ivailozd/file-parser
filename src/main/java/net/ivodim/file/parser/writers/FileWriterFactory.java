package net.ivodim.file.parser.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public interface FileWriterFactory {
    BufferedWriter create(File file) throws IOException;
}
