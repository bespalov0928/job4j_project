package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Optional<User> save(User user);

    boolean deleteById(int id);

    boolean update(User user);

    Optional<User> findById(int id);

    Collection<User> findAll();

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByEmailAndPassword(String email, String pass);
}
