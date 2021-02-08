package ua.training.servlet_project.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.UserDTO;
import ua.training.servlet_project.controller.dto.UserLoginDTO;
import ua.training.servlet_project.controller.dto.UserProfileDTO;
import ua.training.servlet_project.controller.dto.UserRegistrationDTO;
import ua.training.servlet_project.model.dao.DaoFactory;
import ua.training.servlet_project.model.dao.UserDao;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.entity.RoleType;
import ua.training.servlet_project.model.entity.User;
import ua.training.servlet_project.model.exceptions.IllegalEmailException;
import ua.training.servlet_project.model.exceptions.UserNotFoundException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

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

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> usersPage;
        try (UserDao userDao = daoFactory.createUserDao()) {
            usersPage = userDao.findAll(pageable);
        }

        List<UserDTO> usersDTO = usersPage.getContent().stream()
                .map(this::getUserDTO)
                .collect(Collectors.toList());

        return new Page<>(usersDTO, usersPage.getPageable(),
                usersPage.getTotalPages());
    }

    private UserDTO getUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public void saveNewUser(UserRegistrationDTO userRegDTO) {
        User user = User.builder()
                .firstName(userRegDTO.getFirstName())
                .lastName(userRegDTO.getLastName())
                .email(userRegDTO.getEmail())
                .password(getPasswordHash(userRegDTO.getPassword()))
                .role(RoleType.ROLE_USER)
                .build();
        try (UserDao userDao = daoFactory.createUserDao()) {
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
        return printHexBinary(hash);
    }
}
