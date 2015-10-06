package com.epam;

import java.util.HashMap;

public interface MessageReader {
	String readMessage(String path, int id, HashMap<String, String> cached_messages);
}
