package com.epam.repository.file;

import java.util.Optional;

public interface InFileMessageRepositoryReader {

    Optional<String> readFromFile(String path);
}
