package com.epam;

import java.util.Arrays;
import java.util.HashMap;

import com.sun.istack.internal.logging.Logger;

public class MessagingServiceImpl implements MessagingService {

	static Logger logger = Logger.getLogger(MessagingServiceImpl.class);
	
    private HashMap<String, String> cached_messages = new HashMap<String, String>();
    private String working_dir;
    private String file_path;

    public MessagingServiceImpl(String working_dir){
    	this.working_dir = working_dir;
    }

    public String getFilePath() {
		return file_path;
    }
    
    public void saveMessage(int id, String message){
    	logger.info(String.format("Saving message with id: %d", id));
    	MessageWriter writer = new CustomWriter(Arrays.asList(new FileWriter(working_dir), new CacheWriter(working_dir)));
    	writer.saveMessage(id, message, cached_messages);
        logger.info(String.format("Saved message with id: %d", id));
    }
    
	public Maybe<String> readMessage(String path, int id){
        String message = null;
        // if message is in cache then read it from cache
        if (cached_messages.containsKey(path)) {
            message = cached_messages.get(path);
        } else {
        	MessageReader messageReader = new FileReader();
        	message = messageReader.readMessage(path, id);
        	// write it to cache after you read it from the file
            if (message != null) {
        		MessageWriter writer = new CustomWriter(Arrays.asList(new CacheWriter(working_dir)));
        		writer.saveMessage(id, message, cached_messages);
        	}
        }
        return new Maybe<>(message);
    }
}