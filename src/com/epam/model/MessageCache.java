package com.epam.model;

import com.epam.util.Maybe;

import java.util.HashMap;
import java.util.Map;

public class MessageCache {

  private Map<String, Message> cachedMessages = new HashMap<>();

  public boolean isMessageInPath(String path) {
    return cachedMessages.containsKey(path);
  }

  public void add(String path, Message message) {
      cachedMessages.put(path, message);
  }

  public Maybe<Message> readFromCache(String path){
    return new Maybe<>(cachedMessages.get(path));
  }

  public Maybe<Message> getMessageById(int id) {
    return new Maybe<>(cachedMessages.get(id));
  }

}
