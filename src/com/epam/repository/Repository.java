package com.epam.repository;

import java.util.Optional;

public interface Repository {

    public Optional<String> read(int id);

    public void save(String content);
}
