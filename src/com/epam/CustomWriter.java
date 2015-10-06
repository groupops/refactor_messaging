package com.epam;

import java.util.HashMap;
import java.util.List;

public class CustomWriter implements MessageWriter {

	List<MessageWriter> writers;
	
	public CustomWriter(List<MessageWriter> writers) {
		super();
		this.writers = writers;
	}

	@Override
	public void saveMessage(int id, String message, HashMap<String, String> cached_messages) {
		for (MessageWriter messageWriter : writers) {
			messageWriter.saveMessage(id, message, cached_messages);
		}
	}

}
