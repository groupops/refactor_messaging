package com.epam.service.reader;

import com.epam.model.MessageCache;

public class FromMemoryMessageReader implements FromMemoryReader {

  MessageCache messageCache;

  public FromMemoryMessageReader(MessageCache messageCache) {
    if(messageCache == null){
      messageCache = new MessageCache();
    }
    this.messageCache = messageCache;
  }

  @Override
  public String read(String path) {
    return messageCache.getMessageByPath(path);
  }
}
