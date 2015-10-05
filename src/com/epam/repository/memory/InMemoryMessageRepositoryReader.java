package com.epam.repository.memory;

import java.util.Optional;

public interface InMemoryMessageRepositoryReader {

    Optional<String> readFromMemory(String path);
}
