package ua.training.servlet_project.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.UserLoginDTO;
import ua.training.servlet_project.controller.dto.UserProfileDTO;
import ua.training.servlet_project.controller.dto.UserRegistrationDTO;
import ua.training.servlet_project.model.dao.DaoFactory;
import ua.training.servlet_project.model.dao.UserDao;
import ua.training.servlet_project.model.entity.RoleType;
import ua.training.servlet_project.model.entity.User;
import ua.training.servlet_project.model.exceptions.IllegalEmailException;
import ua.training.servlet_project.model.exceptions.UserNotFoundException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private static final String USER_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE = "Cannot find user by email";
    private final DaoFactory daoFactory;

    public UserService() {
        daoFactory = DaoFactory.getInstance();
    }

    public Optional<User> getUserByEmailAndPassword(UserLoginDTO newUser) {
        Optional<User> user;

        try (UserDao userDao = daoFactory.createUserDao()) {
            user = userDao.findByEmailAndPasswordHash(newUser.getEmail(),
                    getPasswordHash(newUser.getPassword()));
        }

        LOGGER.info(getPasswordHash(newUser.getPassword()));
        return user;
    }

    public UserProfileDTO getUserById(Long id) {
        User user;
        try (UserDao userDao = daoFactory.createUserDao()) {
            user = userDao.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE));
        }

        return UserProfileDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public UserProfileDTO getUserByEmail(String email) {
        User user;
        try (UserDao userDao = daoFactory.createUserDao()) {
            user = userDao.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE));
        }

        return UserProfileDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public void saveNewUser(UserRegistrationDTO userRegDTO) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            User user = User.builder()
                    .firstName(userRegDTO.getFirstName())
                    .lastName(userRegDTO.getLastName())
                    .email(userRegDTO.getEmail())
                    .password(getPasswordHash(userRegDTO.getPassword()))
                    .role(RoleType.ROLE_USER)
                    .build();
            userDao.create(user);
        } catch (Exception ex) {
            LOGGER.error("{This Email already exists}");
            throw new IllegalEmailException(ex);
        }
    }

    private static String getPasswordHash(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Cannot find a digest method in 'getPasswordHash()' " + e);
        }
        byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
