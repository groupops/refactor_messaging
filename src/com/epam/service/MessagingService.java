package com.epam.service;

import com.epam.util.Maybe;
import com.epam.model.FileInfo;
import com.epam.model.MessageCache;
import com.epam.service.reader.FromFileMessageReader;
import com.epam.service.writer.ToFileMessageWriter;
import com.epam.service.writer.ToMemoryMessageWriter;
import com.sun.istack.internal.logging.Logger;

import java.io.*;
import java.util.logging.Level;

/**
 * Created by magdy on 23.09.15.
 */
public class MessagingService {

  private Logger logger = Logger.getLogger(this.getClass());

  private String path;
  private String working_dir;
  private MessageCache messageCache;

  public MessagingService(String working_dir, MessageCache messageCache) {
    this.working_dir = working_dir;
    this.messageCache = messageCache;
  }

  public void saveMessage(int id, String message) {
    logger.log(Level.INFO, String.format("Saving message with id: %d", id));

    FileInfo file_info = getMessageFileInfoById(id);
    String path = file_info.getFilePath();
    System.out.println("---------------> " + path);
    File file = new File(path);

    ToFileMessageWriter toFileMessageWriter = new ToFileMessageWriter();
    toFileMessageWriter.save(id, message, file, file_info);

    ToMemoryMessageWriter toMememoryWriter = new ToMemoryMessageWriter(messageCache);
    toMememoryWriter.save(path, message);

    getPath();
    logger.log(Level.INFO, String.format("Saved message with id: %d", id));
  }

  public Maybe<String> readMessage(String path) {
    Maybe<String> message;
    if (messageCache.isMessageInPath(path)) {
      message = messageCache.getMessageByPath(path);
    } else {
      message = readNotCachedMessage(path);
      addMessage(path, message);
    }
    return message;
  }

  private void addMessage(String path, Maybe<String> messages) {
    for (String message : messages) {
      messageCache.add(path, message);
    }
  }

  private Maybe<String> readNotCachedMessage(String path) {
    Maybe<String> message;
    FromFileMessageReader fileReader = new FromFileMessageReader();
    message = fileReader.read(path);

    return message;
  }

  public FileInfo getMessageFileInfoById(int id) {
    return new FileInfo(working_dir, id);
  }

  public String getPath(){
    return path;
  }

}
