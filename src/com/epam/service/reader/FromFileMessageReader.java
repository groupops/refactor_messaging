package com.epam.service.reader;

import com.epam.model.Message;
import com.epam.util.Maybe;
import com.sun.istack.internal.logging.Logger;

import java.io.*;

public class FromFileMessageReader implements MessageReader {

  private Logger logger = Logger.getLogger(this.getClass());

  @Override
  public Maybe<Message> read(String path) {
    DataInputStream file_reader = null;
    Message message = null;
    try {
      file_reader = new DataInputStream(new FileInputStream(new File(path)));
      message.setContent(file_reader.readUTF());
    } catch (FileNotFoundException e) {
      logger.severe(String.format(
          "Error while opening message file for reading '%s'", path), e);
    } catch (IOException e) {
      logger.severe(String.format("Error while reading message file %s", path),
          e);
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
        logger
            .warning(String.format("Error while closing message file %s", path),
                e);
      }
    }
  }

}
