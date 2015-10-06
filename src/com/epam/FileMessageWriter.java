package com.epam;

import com.sun.istack.internal.logging.Logger;

import java.io.*;
import java.util.Map;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

/**
 * Created by Dmytro_Ulanovych on 10/5/2015.
 */
public class FileMessageWriter implements MessageWriter{
    private static final Logger LOG = Logger.getLogger(FileMessageWriter.class);
    private String workingDirectory;


    public FileMessageWriter(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    @Override
    public void saveMessage(Message message) {
        LOG.info(String.format("Saving message with id: %d", message.getId()));

        String filePath = new FileInfo(workingDirectory, message.getId()).getFilePath();
        DataOutputStream writer = getFileWriter(filePath, message);
        writeMessageToFile(writer, message);
        closeWriter(writer);

        LOG.info(String.format("Saved message with id: %d", message.getId()));
    }

    private DataOutputStream getFileWriter(String filePath, Message message) {
        File file = new File(filePath);
        DataOutputStream writer = null;
        try {
            writer = new DataOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, String.format("Error while Saving message with id: %d", message.getId()), e);
        }

        return writer;
    }

    private void writeMessageToFile(DataOutputStream writer, Message message) {
        try {
            writer.writeUTF(message.getBody());
        } catch (IOException e) {
            LOG.log(SEVERE, String.format("Error while Saving message with id: %d", message.getId()), e);
        }
    }

    private void closeWriter(DataOutputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                LOG.log(WARNING, "Could not close File Writer.", e);
            }
        }
    }
}
