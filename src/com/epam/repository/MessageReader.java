package com.epam.repository;

import java.util.Optional;

public interface MessageReader {

    Optional<String> readMessage(int id);
}
