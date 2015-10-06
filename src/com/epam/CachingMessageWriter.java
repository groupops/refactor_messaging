package com.epam;

import java.util.Map;

/**
 * Created by Dmytro_Ulanovych on 10/6/2015.
 */
public class CachingMessageWriter implements MessageWriter {
    private final Map<Integer, Message> cache;
    private final MessageWriter internalWriter;

    public CachingMessageWriter(Map<Integer, Message> cache, MessageWriter internalWriter) {
        if (cache == null) {
            throw new IllegalArgumentException("Cache can't be null!");
        }
        if (internalWriter == null) {
            throw new IllegalArgumentException("Writer can't be null!");
        }
        this.cache = cache;
        this.internalWriter = internalWriter;
    }

    @Override
    public void saveMessage(Message message) {
        cache.put(message.getId(), message);
        internalWriter.saveMessage(message);
    }
}
