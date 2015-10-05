package com.epam;

import com.sun.istack.internal.logging.Logger;

import java.io.*;
import java.util.Map;

import static java.util.logging.Level.SEVERE;

/**
 * Created by Dmytro_Ulanovych on 10/5/2015.
 */
public class CachedMessageReader implements MessageReader {
    private static final Logger LOG = Logger.getLogger(CachedMessageReader.class);
    private final Map<String, Message> cachedMessages;

    public CachedMessageReader(Map<String, Message> cachedMessages) {
        this.cachedMessages = cachedMessages;
    }

    @Override
    public Maybe<Message> readMessage(String filePath) {
        Message message;
        if (cachedMessages.containsKey(filePath)) {
            message = cachedMessages.get(filePath);
        } else {
            message = readMessageFromFile(filePath);
            if (message != null) {
                cachedMessages.put(filePath, message);
            }
        }
        return new Maybe<>(message);
    }

    private Message readMessageFromFile(String filePath) {
        Message message = null;
        try (DataInputStream reader = new DataInputStream(new FileInputStream(new File(filePath)))){
            FileInfo info = new FileInfo(filePath);
            message = new Message(info.getId(), reader.readUTF());
        } catch (IOException e) {
            LOG.log(SEVERE, String.format("Error while reading message file %s", filePath), e);
        }
        return message;
    }
}
