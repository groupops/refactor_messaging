package com.epam;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

public class FileWriter implements MessageWriter {

	static Logger logger = Logger.getLogger(FileWriter.class);
	
	private String working_dir;
	private String file_path;

	public FileWriter(String working_dir) {
		super();
		this.working_dir = working_dir;
	}
	
	@Override
	public void saveMessage(int id, String message) {
		// save message to file
        FileInfo file_info = new FileInfo(working_dir, id);
        file_path = file_info.getFilePath();
        System.out.println("---------------> " + file_path);
        File file = new File(file_path);
        DataOutputStream file_writer = getFileWriter(file, id);
        writeToFile(file_writer, message, id);
        closeWriter(file_writer);
	}

	private DataOutputStream getFileWriter(File file, int id) {
    	DataOutputStream file_writer = null;
        try {
            file_writer = new DataOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
        	logger.log(Level.SEVERE, String.format("Error while Saving message with id: %d", id), e);
        }
		return file_writer;
    }

	private void writeToFile(DataOutputStream file_writer, String message, int id) {
		try {
			file_writer.writeUTF(message);
		} catch (IOException e) {
			logger.log(Level.SEVERE, String.format("Error while Saving message with id: %d", id), e);
		}
	}

	private void closeWriter(DataOutputStream file_writer) {
		if (file_writer != null) {
			try {
				file_writer.close();
			} catch (IOException e) {
				logger.log(Level.WARNING, String.format("Could not close '%s'.", file_path), e);
			}
		}
	}
}
