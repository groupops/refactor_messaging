package com.epam;

import com.sun.istack.internal.logging.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import static java.util.logging.Level.SEVERE;

/**
 * Created by Dmytro_Ulanovych on 10/6/2015.
 */
public class FileMessageReader extends CachedMessageReader {
    private final static Logger LOG = Logger.getLogger(FileMessageReader.class);

    public FileMessageReader(Map<String, Message> cachedMessages) {
        super(cachedMessages);
    }

    @Override
    public Maybe<Message> readMessage(String filePath) {
        Maybe<Message> message = super.readMessage(filePath);
        if (!message.iterator().hasNext()){
            message = new Maybe<>(readMessageFromFile(filePath));
        }
        return message;
    }

    private Message readMessageFromFile(String filePath) {
        Message message = null;
        try (DataInputStream reader = new DataInputStream(new FileInputStream(new File(filePath)))) {
            FileInfo info = new FileInfo(filePath);
            message = new Message(info.getId(), reader.readUTF());
        } catch (IOException e) {
            LOG.log(SEVERE, String.format("Error while reading message file %s", filePath), e);
        }
        return message;
    }
}
