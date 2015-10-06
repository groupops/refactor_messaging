package com.epam;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

public class FileReader implements MessageReader {

	static Logger logger = Logger.getLogger(FileReader.class);
	
	@Override
	public String readMessage(String path, int id, HashMap<String, String> cached_messages) {
    	String message = null;
    	DataInputStream file_reader = null;
        try {
            // open file
            file_reader = new DataInputStream(new FileInputStream(new File(path)));
            message = file_reader.readUTF();
        } catch (FileNotFoundException e) {
        	logger.log(Level.SEVERE, String.format("Error while opening message file for reading '%s'", path), e);
        } catch (IOException e) {
        	logger.log(Level.SEVERE, String.format("Error while reading message file %s", path), e);
        } finally {
        	closeReader(file_reader, path);
        }
        return message;
	}

	private void closeReader(DataInputStream file_reader, String path) {
		if (file_reader != null) {
            try {
                file_reader.close();
            } catch (IOException e) {
            	logger.log(Level.WARNING, String.format("Error while closing message file %s", path), e);
            }
        }
	}
}
