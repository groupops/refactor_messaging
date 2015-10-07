package com.epam.service.reader;

import com.epam.model.Message;
import com.epam.model.MessageCache;
import com.epam.util.Maybe;
import com.sun.istack.internal.logging.Logger;

import java.io.*;

public class FromFileCacheReader implements CacheReader {

  private Logger logger = Logger.getLogger(this.getClass());

  private String workingDirectory;
  private MessageCache messageCache;

  public FromFileCacheReader(MessageCache messageCache,
                             String workingDirectory) {
    this.messageCache = messageCache;
    this.workingDirectory = workingDirectory;
  }

  @Override
  public Maybe<Message> read(String path) {
    Maybe<Message> message = null;
    if (messageCache.isMessageInPath(path)) {
      message = messageCache.readFromCache(path);
    } else {
      message = readMessageFromFile(path);
      addMessage(path, message);
    }
    return message;
  }

  private Maybe<Message> readMessageFromFile(String path) {
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

  private void addMessage(String path, Maybe<Message> messages) {
    for (Message message : messages) {
      messageCache.add(path, message);
    }
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
