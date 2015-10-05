package com.epam;

/**
 * Created by Dmytro_Ulanovych on 10/5/2015.
 */
public interface MessageReader {
    Maybe<Message> readMessage(String filePath);
}
