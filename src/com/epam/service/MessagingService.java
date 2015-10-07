package com.epam.service;

import com.epam.model.Message;
import com.epam.util.Maybe;

public interface MessagingService {

  void saveMessage(String message);

  Maybe<Message> readMessage(int id);

}