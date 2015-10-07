package com.epam.service;

import com.epam.model.FileInfo;
import com.epam.model.Message;
import com.epam.model.MessageCache;
import com.epam.service.reader.CacheReader;
import com.epam.service.reader.FromMemoryCacheReader;
import com.epam.service.writer.CacheWriter;
import com.epam.service.writer.ToMemoryCacheWriter;
import com.epam.util.Maybe;
import com.sun.istack.internal.logging.Logger;

/**
 * Created by magdy on 23.09.15.
 */
public class MessagingServiceImpl implements MessagingService {

  private Logger logger = Logger.getLogger(this.getClass());

  private String path;
  private String workingDirectory;
  private int idCounter = 1;
  private MessageCache messageCache;
  private CacheWriter memoryWriter;
  private CacheReader memoryReader;

  public MessagingServiceImpl(String workingDirectory) {
    this.memoryWriter = new ToMemoryCacheWriter(workingDirectory);
    this.memoryReader =
        new FromMemoryCacheReader(messageCache, workingDirectory);
  }

  @Override
  public Maybe<Message> readMessage(int id) {
    return memoryReader.read(id);
  }

  @Override
  public void saveMessage(String messageContent) {
    Message message = new Message(idCounter, messageContent);
    memoryWriter.save(message);
    memoryWriter.save(message);
    idCounter++;
  }

  public FileInfo getMessageFileInfoById(int id) {
    return new FileInfo(workingDirectory, id);
  }

  public String getPath() {
    return path;
  }

}
