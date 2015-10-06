package com.epam.messages;

import java.util.Optional;

public interface MessageReader {

    Optional<String> readMessage(int id);
    
}
