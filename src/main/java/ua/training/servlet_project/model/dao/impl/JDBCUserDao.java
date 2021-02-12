package ua.training.servlet_project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.dao.UserDao;
import ua.training.servlet_project.model.dao.mapper.UserMapper;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.entity.User;
import ua.training.servlet_project.model.exceptions.EntityCreationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCUserDao implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(JDBCUserDao.class);
    private static final String COUNT_COLUMN_NAME = "COUNT(*)";
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) {
        try (PreparedStatement ps = connection.prepareCall("INSERT INTO user" +
                "(email, first_name, last_name, password, role)" +
                " VALUES (?, ?, ?, ?, ?)")) {
            ps.setString(1, entity.getEmail());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());
            ps.setString(4, entity.getPassword());
            ps.setString(5, entity.getRole().toString());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new EntityCreationException(ex);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> result = Optional.empty();

        try (PreparedStatement ps = connection.prepareCall("SELECT * FROM user WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            UserMapper mapper = new UserMapper();
            if (rs.next()) {
                result = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> result = Optional.empty();

        try (PreparedStatement ps = connection.prepareCall("SELECT * FROM user WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            UserMapper mapper = new UserMapper();
            if (rs.next()) {
                result = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public Optional<User> findByEmailAndPasswordHash(String email, String passwordHash) {
        Optional<User> result = Optional.empty();

        LOGGER.error("here");

        try (PreparedStatement ps = connection.prepareCall("SELECT * FROM user WHERE email = ? AND password = ?")) {
            ps.setString(1, email);
            ps.setString(2, passwordHash);
            ResultSet rs = ps.executeQuery();
            UserMapper mapper = new UserMapper();
            if (rs.next()) {
                result = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        Map<Long, User> users = new LinkedHashMap<>();
        int totalPages = 0;

        try (PreparedStatement ps = connection.prepareCall(
                "SELECT * FROM user " +
                        "ORDER BY id " +
                        "LIMIT ? " +
                        "OFFSET ? ");
             PreparedStatement countQuery = connection.prepareCall("SELECT COUNT(*) FROM user ")
        ) {
            ps.setInt(1, pageable.getPageSize());
            ps.setInt(2, pageable.getPageSize() * pageable.getPageNumber());

            ResultSet rs = ps.executeQuery();
            ResultSet totalElementsResultSet = countQuery.executeQuery();

            UserMapper userMapper = new UserMapper();
            while (rs.next()) {
                User user = userMapper
                        .extractFromResultSet(rs);
                userMapper
                        .makeUnique(users, user);
            }

            if (totalElementsResultSet.next()) {
                totalPages = userMapper.getTotalPages(totalElementsResultSet,
                        COUNT_COLUMN_NAME, pageable.getPageSize());
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }
        return new Page<>(new ArrayList<>(users.values()), pageable, totalPages);
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwable) {
            LOGGER.error(throwable);
        }
    }
}
