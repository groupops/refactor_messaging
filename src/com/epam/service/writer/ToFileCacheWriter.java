package com.epam.service.writer;

import com.epam.model.FileInfo;
import com.epam.model.Message;
import com.sun.istack.internal.logging.Logger;

import java.io.*;

public class ToFileCacheWriter implements CacheWriter {

  private Logger logger = Logger.getLogger(this.getClass());

  private String workingDirectory;

  public ToFileCacheWriter(String workingDirectory) {
    this.workingDirectory = workingDirectory;
  }

  public void save(Message message) {
    String filePath =
        workingDirectory + File.separator + message.getId() + ".txt";
    DataOutputStream file_writer =
        getDataOutputStream(message.getId(), new File(filePath));
    writeUTFMessage(message, file_writer);
  }

  public DataOutputStream getDataOutputStream(int id, File file) {
    DataOutputStream file_writer = null;
    try {
      file_writer = new DataOutputStream(new FileOutputStream(file));
    } catch (FileNotFoundException e) {
      logger.severe(String.format("Error while Saving message with id: %d", id),
          e);
    }
    return file_writer;
  }

  private void writeUTFMessage(Message message,
                               DataOutputStream file_writer) {
    try {
      file_writer.writeUTF(message.getContent());
    } catch (IOException e) {
      logger.severe(String.format("Error while Saving message with id: %d",
          message.getId()), e);
    }
  }

  public void close(FileInfo file_info, DataOutputStream file_writer) {
    if (file_writer != null) {
      try {
        file_writer.close();
      } catch (IOException e) {
        logger.warning(
            String.format("Could not close '%s'.", file_info.getFilePath()), e);
      }
    }
  }

}
