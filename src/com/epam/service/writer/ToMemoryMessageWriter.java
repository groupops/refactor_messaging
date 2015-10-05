package com.epam.service.writer;

import com.epam.model.MessageCache;

public class ToMemoryMessageWriter implements ToMemoryWriter {

  MessageCache messageCache;

  public ToMemoryMessageWriter(MessageCache messageCache) {
    if(messageCache == null){
      messageCache = new MessageCache();
    }
    this.messageCache = messageCache;
  }

  @Override
  public void save(String path, String message) {
    messageCache.add(path, message);
  }
}
