package com.epam.service.reader;

import com.epam.model.Message;
import com.epam.util.Maybe;

public interface CacheReader {

  Maybe<Message> read(String path);

}
