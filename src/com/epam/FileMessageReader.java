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
public class FileMessageReader implements MessageReader {
    private final static Logger LOG = Logger.getLogger(FileMessageReader.class);
    private MessageReader internalReader;


    @Override
    public Maybe<Message> readMessage(String filePath) {
        Maybe<Message> message = new Maybe<>(readMessageFromFile(filePath));
        if (!message.iterator().hasNext() && internalReader != null) {
            message = internalReader.readMessage(filePath);
        }
        return message;
    }

    @Override
    public void setInternalReader(MessageReader reader) {
        internalReader = reader;
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
