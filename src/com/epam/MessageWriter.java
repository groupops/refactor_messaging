package com.epam;

import java.util.HashMap;

public interface MessageWriter {
	void saveMessage(int id, String message, HashMap<String, String> cached_messages);
}
