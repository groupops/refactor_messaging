package com.epam.service.reader;

import com.epam.util.Maybe;

public interface FromFileReader {

  Maybe<String> read(String path);

}
