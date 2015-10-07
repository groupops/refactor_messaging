package com.epam.service.writer;

import com.epam.model.Message;
import com.epam.model.MessageCache;

public class ToMemoryCacheWriter implements CacheWriter {

  private MessageCache messageCache;
  private String workingDir;

  public ToMemoryCacheWriter(String workingDir) {
    this.workingDir = workingDir;
  }

  public ToMemoryCacheWriter(MessageCache messageCache) {
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
