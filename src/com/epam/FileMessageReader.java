package com.epam;

import com.sun.istack.internal.logging.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static java.util.logging.Level.SEVERE;

/**
 * Created by Dmytro_Ulanovych on 10/6/2015.
 */
public class FileMessageReader implements MessageReader {
    private final static Logger LOG = Logger.getLogger(FileMessageReader.class);
    private final String workingDirectory;

    public FileMessageReader(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    @Override
    public Maybe<Message> readMessage(Integer id) {
        return new Maybe<>(readMessageFromFile(id));
    }

    private Message readMessageFromFile(Integer id) {
        Message message = null;
        String filePath = new FileInfo(workingDirectory, id).getFilePath();
        try (DataInputStream reader = new DataInputStream(new FileInputStream(new File(filePath)))) {
            message = new Message(id, reader.readUTF());
        } catch (IOException e) {
            LOG.log(SEVERE, String.format("Error while reading message file %s", filePath), e);
        }
        return message;
    }
}
