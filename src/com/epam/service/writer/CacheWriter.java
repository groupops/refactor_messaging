package com.epam.service.writer;

import com.epam.model.Message;

public interface CacheWriter {

  void save(Message message);

}
