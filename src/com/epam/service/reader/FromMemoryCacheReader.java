package com.epam.service.reader;

import com.epam.model.Message;
import com.epam.model.MessageCache;
import com.epam.util.Maybe;

public class FromMemoryCacheReader implements CacheReader {

  MessageCache messageCache;
  String workingDirectory;

  public FromMemoryCacheReader(MessageCache messageCache,
                               String workingDirectory) {
    if (messageCache == null) {
      messageCache = new MessageCache();
    }
    this.messageCache = messageCache;
    this.workingDirectory = workingDirectory;
  }

  @Override
  public Maybe<Message> read(int id) {
    return messageCache.getMessageById(id);
  }

  public boolean isMessagePresent(String path) {
    return messageCache.isMessageInPath(path);
  }

}
