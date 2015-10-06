package com.epam.service.writer;

import com.epam.model.Message;
import com.epam.model.MessageCache;

public class ToMemoryMessageWriter implements MessageWriter {

  private MessageCache messageCache;
  private String workingDir;

  public ToMemoryMessageWriter(String workingDir) {
    this.workingDir = workingDir;
  }

  public ToMemoryMessageWriter(MessageCache messageCache) {
    if(messageCache == null){
      messageCache = new MessageCache();
    }
    this.messageCache = messageCache;
  }

  @Override
  public void save(Message message) {
    messageCache.add(workingDir, message);
  }
}
