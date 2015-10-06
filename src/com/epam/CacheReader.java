package com.epam;

import java.util.HashMap;

public class CacheReader implements MessageReader {

	@Override
	public String readMessage(String path, int id, HashMap<String, String> cached_messages) {
		return cached_messages.get(path);
	}

}
