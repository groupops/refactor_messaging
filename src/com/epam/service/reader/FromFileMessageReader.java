package com.epam.service.reader;

import com.epam.util.Maybe;
import com.sun.istack.internal.logging.Logger;

import java.io.*;
import java.util.logging.Level;

public class FromFileMessageReader implements FromFileReader {

  private Logger logger = Logger.getLogger(this.getClass());

  @Override
  public Maybe<String> read(String path) {
    DataInputStream file_reader = null;
    String message = null;
    try {
      file_reader = new DataInputStream(new FileInputStream(new File(path)));
      message = file_reader.readUTF();
    } catch (FileNotFoundException e) {
      logger.log(Level.SEVERE, String.format(
          "Error while opening message file for reading '%s'", path), e);
    } catch (IOException e) {
      logger.log(Level.SEVERE,
          String.format("Error while reading message file %s", path), e);
    } finally {
      close(path, file_reader);
    }
    return new Maybe<>(message);
  }

  private void close(String path, DataInputStream file_reader) {
    if (file_reader != null) {
      try {
        file_reader.close();
      } catch (IOException e) {
        logger.log(Level.WARNING,
            String.format("Error while closing message file %s", path), e);
      }
    }
  }

}
