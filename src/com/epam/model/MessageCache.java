package com.epam.model;

import com.epam.util.Maybe;

import java.util.HashMap;
import java.util.Map;

public class MessageCache {

  private Map<String, String> cached_messages = new HashMap<>();

  public boolean isMessageInPath(String path) {
    return cached_messages.containsKey(path);
  }

  public void add(String path, String message) {
      cached_messages.put(path, message);
  }

  public Maybe<String> getMessageByPath(String path) {
    return new Maybe<>(cached_messages.get(path));
  }

}
