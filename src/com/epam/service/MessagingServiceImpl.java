package com.epam.service;

import com.epam.model.FileInfo;
import com.epam.model.Message;
import com.epam.model.MessageCache;
import com.epam.service.reader.FromFileMessageReader;
import com.epam.service.reader.FromMemoryMessageReader;
import com.epam.service.reader.MessageReader;
import com.epam.service.writer.MessageWriter;
import com.epam.service.writer.ToFileMessageWriter;
import com.epam.service.writer.ToMemoryMessageWriter;
import com.epam.util.Maybe;
import com.sun.istack.internal.logging.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by magdy on 23.09.15.
 */
public class MessagingServiceImpl implements MessagingService {

  private Logger logger = Logger.getLogger(this.getClass());

  private String path;
  private String workingDirectory;
  private MessageCache messageCache;
  private MessageWriter fileWriter;
  private MessageWriter memoryWriter;
  private MessageReader fileReader;
  private MessageReader memoryReader;

  public MessagingServiceImpl(String workingDirectory) {
    this.fileWriter = new ToFileMessageWriter(workingDirectory);
    this.memoryWriter = new ToMemoryMessageWriter(messageCache);
    this.fileReader = new FromFileMessageReader();
    this.memoryReader = new FromMemoryMessageReader(messageCache);
  }

  @Override
  public Maybe<Message> readMessage(String path, int id) {
    Maybe<Message> message = null;
    if (messageCache.isMessageInPath(path)) {
      message = messageCache.getMessageByPath(path);
    } else {
      if (!message.iterator().hasNext()) {
        message = new Maybe<>(readMessageFromFile(path, id));
      }
      addMessage(path, message);
    }
    return message;
  }

  @Override
  public void saveMessage(Message message) {
    logger.info(String.format("Saving message with id: %d", message.getId()));

    FileInfo file_info = getMessageFileInfoById(message.getId());
    String path = file_info.getFilePath();
    System.out.println("---------------> " + path);
    File file = new File(path);

    ToFileMessageWriter toFileWriter =
        new ToFileMessageWriter(workingDirectory);
    toFileWriter.save(message);
    toFileWriter.close(file_info, toFileWriter.getDataOutputStream(
        message.getId(), file));

    ToMemoryMessageWriter toMemoryWriter =
        new ToMemoryMessageWriter(messageCache);
    toMemoryWriter.save(message);

    getPath();
    logger.info(String.format("Saved message with id: %d", message.getId()));
  }

  private void addMessage(String path, Maybe<Message> messages) {
    for (Message message : messages) {
      messageCache.add(path, message);
    }
  }

  private Message readMessageFromFile(String filePath, int id) {
    Message message = null;
    try (DataInputStream reader = new DataInputStream(
        new FileInputStream(new File(filePath)))) {
      FileInfo info = new FileInfo(filePath);
      message = new Message(id, reader.readUTF());
    } catch (IOException e) {
      logger.severe(
          String.format("Error while reading message file %s", filePath), e);
    }
    return message;
  }

  private Maybe<Message> readNotCachedMessage(String path) {
    Maybe<Message> message;
    FromFileMessageReader fileReader = new FromFileMessageReader();
    message = fileReader.read(path);

    return message;
  }

  public FileInfo getMessageFileInfoById(int id) {
    return new FileInfo(workingDirectory, id);
  }

  public String getPath() {
    return path;
  }

}
