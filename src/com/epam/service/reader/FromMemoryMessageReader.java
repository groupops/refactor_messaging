package com.epam.service.reader;

import com.epam.model.Message;
import com.epam.model.MessageCache;
import com.epam.util.Maybe;

public class FromMemoryMessageReader implements MessageReader {

  MessageCache messageCache;

  public FromMemoryMessageReader(MessageCache messageCache) {
    if(messageCache == null){
      messageCache = new MessageCache();
    }
    this.messageCache = messageCache;
  }

  @Override
  public Maybe<Message> read(String path) {
    return messageCache.getMessageByPath(path);
  }
}
