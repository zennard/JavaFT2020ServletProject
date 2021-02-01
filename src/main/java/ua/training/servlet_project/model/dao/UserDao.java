package ua.training.servlet_project.model.dao;

import ua.training.servlet_project.model.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPasswordHash(String email, String password);
}
