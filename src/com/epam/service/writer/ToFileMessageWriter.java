package com.epam.service.writer;

import com.epam.model.FileInfo;
import com.sun.istack.internal.logging.Logger;

import java.io.*;
import java.util.logging.Level;

public class ToFileMessageWriter {

  private Logger logger = Logger.getLogger(this.getClass());

  public void save(int id, String message, File file, FileInfo file_info) {
    DataOutputStream file_writer = getDataOutputStream(id, file);
    writeUTFMessage(id, message, file_writer);
    close(file_info, file_writer);
  }

  private DataOutputStream getDataOutputStream(int id, File file) {
    DataOutputStream file_writer = null;
    try {
      file_writer = new DataOutputStream(new FileOutputStream(file));
    } catch (FileNotFoundException e) {
      logger.log(Level.SEVERE,
          String.format("Error while Saving message with id: %d", id), e);
    }
    return file_writer;
  }

  private void writeUTFMessage(int id, String message,
                               DataOutputStream file_writer) {
    try {
      file_writer.writeUTF(message);
    } catch (IOException e) {
      logger.log(Level.SEVERE,
          String.format("Error while Saving message with id: %d", id), e);
    }
  }

  private void close(FileInfo file_info, DataOutputStream file_writer) {
    if (file_writer != null) {
      try {
        file_writer.close();
      } catch (IOException e) {
        logger.log(Level.WARNING,
            String.format("Could not close '%s'.", file_info.getFilePath()), e);
      }
    }
  }

}
