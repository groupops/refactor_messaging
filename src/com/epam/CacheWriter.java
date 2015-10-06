package com.epam;

import java.util.HashMap;

public class CacheWriter implements MessageWriter {

	private String working_dir;
	private String file_path;
	
	public CacheWriter(String working_dir) {
		super();
		this.working_dir = working_dir;
	}

	@Override
	public void saveMessage(int id, String message, HashMap<String, String> cached_messages) {
		FileInfo file_info = new FileInfo(working_dir, id);
        file_path = file_info.getFilePath();
        
        // cache saved message
        cached_messages.put(file_path, message);
	}
}
