package com.epam.service;

import com.epam.model.Message;
import com.epam.util.Maybe;

public interface MessagingService {

  void saveMessage(Message message);

  Maybe<Message> readMessage(String path, int id);

}