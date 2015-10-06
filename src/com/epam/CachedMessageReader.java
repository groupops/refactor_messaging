package com.epam;

import java.util.Map;

/**
 * Created by Dmytro_Ulanovych on 10/5/2015.
 */
public class CachedMessageReader implements MessageReader {
    private final Map<String, Message> cachedMessages;
    private MessageReader internalReader;

    public CachedMessageReader(Map<String, Message> cachedMessages) {
        this.cachedMessages = cachedMessages;
    }

    @Override
    public Maybe<Message> readMessage(String filePath) {
        Maybe<Message> message = null;
        if (cachedMessages.containsKey(filePath)) {
            message = new Maybe<>(cachedMessages.get(filePath));
        } else if (internalReader != null) {
            message = internalReader.readMessage(filePath);
        }
        return message;
    }

    @Override
    public void setInternalReader(MessageReader reader) {
        internalReader = reader;
    }
}
