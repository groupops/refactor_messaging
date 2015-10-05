package com.epam;

import com.sun.istack.internal.logging.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Created by magdy on 23.09.15.
 */
public class MessagingService {

    private HashMap<String, String> cached_messages = new HashMap<String, String>();
    private String working_dir;

    public MessagingService(String working_dir){
        this.working_dir = working_dir;
    }

    public String saveMessage(int id, String message){
        // log info saving
        Logger.getLogger(this.getClass()).info(String.format("Saving message with id: %d", id));

        // save message to file
        FileInfo file_info = getMessageFileInfoById(id);
        String file_path = file_info.getFilePath();
        System.out.println("---------------> " + file_path);
        File file = new File(file_path);
        DataOutputStream file_writer = null;
        try {
            file_writer = new DataOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            Logger.getLogger(this.getClass()).log(Level.SEVERE, String.format("Error while Saving message with id: %d", id), e);
        }
        try {
            file_writer.writeUTF(message);
        } catch (IOException e) {
            Logger.getLogger(this.getClass()).log(Level.SEVERE, String.format("Error while Saving message with id: %d", id), e);
        }
        if (file_writer != null) {
            try {
                file_writer.close();
            } catch (IOException e) {
                Logger.getLogger(this.getClass()).log(Level.WARNING, String.format("Could not close '%s'.", file_info.getFilePath()), e);
            }
        }
        // cache saved message
        cached_messages.put(file_path, message);

        // log info saved message
        Logger.getLogger(this.getClass()).info(String.format("Saved message with id: %d", id));
        return file_path;
    }


    public String readMessage(String path){
        String message = null;
        // if message is in cache then read it from cache
        if (cached_messages.containsKey(path)) {
            message = cached_messages.get(path);
        } else {
            // if message is not in cache then read it from file
            DataInputStream file_reader = null;
            try {
                // open file
                file_reader = new DataInputStream(new FileInputStream(new File(path)));
                // read message from file
                message = file_reader.readUTF();
            } catch (FileNotFoundException e) {
                Logger.getLogger(this.getClass()).log(Level.SEVERE, String.format("Error while opening message file for reading '%s'", path), e);
            } catch (IOException e) {
                Logger.getLogger(this.getClass()).log(Level.SEVERE, String.format("Error while reading message file %s", path), e);
            } finally {
                if (file_reader != null) {
                    try {
                        file_reader.close();
                    } catch (IOException e) {
                        Logger.getLogger(this.getClass()).log(Level.WARNING, String.format("Error while closing message file %s", path), e);
                    }
                }
            }

            // write it to cache after you read it from the file
            if (message != null) cached_messages.put(path, message);
        }
        return message;
    }

    public FileInfo getMessageFileInfoById(int id){
        return new FileInfo(working_dir, id);
    }
}
