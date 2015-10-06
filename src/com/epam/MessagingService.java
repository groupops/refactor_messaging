package com.epam;

public interface MessagingService {

	void saveMessage(int id, String message);
	
	Maybe<String> readMessage(String path, int id);
}
