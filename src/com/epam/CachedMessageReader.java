package com.epam;

import java.util.Map;

/**
 * Created by Dmytro_Ulanovych on 10/5/2015.
 */
public class CachedMessageReader implements MessageReader {
    private final Map<String, Message> cachedMessages;

    public CachedMessageReader(Map<String, Message> cachedMessages) {
        this.cachedMessages = cachedMessages;
    }

    @Override
    public Maybe<Message> readMessage(String filePath) {
        Message message = null;
        if (cachedMessages.containsKey(filePath)) {
            message = cachedMessages.get(filePath);
        }
        return new Maybe<>(message);
    }
}
