package com.epam;

import java.util.Map;

/**
 * Created by Dmytro_Ulanovych on 10/5/2015.
 */
public class CachingMessageReader implements MessageReader {
    private final Map<Integer, Message> cache;
    private MessageReader internalReader;

    public CachingMessageReader(MessageReader internalReader, Map<Integer, Message> cache) {
        if (internalReader == null) {
            throw new IllegalArgumentException("Message reader can't be null!");
        }
        if (cache == null) {
            throw new IllegalArgumentException("Cache can't be null!");
        }
        this.internalReader = internalReader;
        this.cache = cache;
    }

    @Override
    public Maybe<Message> readMessage(Integer id) {
        Maybe<Message> message;
        if (cache.containsKey(id)) {
            message = new Maybe<>(cache.get(id));
        } else {
            message = internalReader.readMessage(id);
            updateCache(id, message);
        }
        return message;
    }

    private void updateCache(Integer id, Maybe<Message> message) {
        if (message.iterator().hasNext()) {
            cache.put(id, message.iterator().next());
        }
    }
}
