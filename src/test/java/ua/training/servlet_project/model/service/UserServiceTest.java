package ua.training.servlet_project.model.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.training.servlet_project.controller.dto.UserDTO;
import ua.training.servlet_project.controller.dto.UserLoginDTO;
import ua.training.servlet_project.controller.dto.UserProfileDTO;
import ua.training.servlet_project.model.dao.DaoFactory;
import ua.training.servlet_project.model.dao.UserDao;
import ua.training.servlet_project.model.entity.*;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private DaoFactory daoFactory;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService testedInstance;

    @Test
    void shouldGetAllUsers() {
        //given
        int expectedNumber = 2;
        when(userDao.findAll(any()))
                .thenReturn(givenUsersPage());
        when(daoFactory.createUserDao())
                .thenReturn(userDao);
        //when
        Page<UserDTO> usersPage = testedInstance.getAllUsers(null);
        //then
        assertThat(usersPage.getContent().size()).isEqualTo(expectedNumber);

        UserDTO userDTO = usersPage.getContent().get(0);
        assertThat(userDTO)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("firstName", "Tom")
                .hasFieldOrPropertyWithValue("lastName", "Hank")
                .hasFieldOrPropertyWithValue("role", RoleType.ROLE_USER)
                .hasFieldOrPropertyWithValue("email", "r@r.com");
    }

    Page<User> givenUsersPage() {
        return new Page<>(
                Arrays.asList(
                        User.builder()
                                .id(1L)
                                .role(RoleType.ROLE_USER)
                                .firstName("Tom")
                                .lastName("Hank")
                                .email("r@r.com")
                                .build(),
                        User.builder()
                                .id(2L)
                                .role(RoleType.ROLE_MANAGER)
                                .firstName("Bob")
                                .lastName("Rust")
                                .email("g@g.com")
                                .build()
                ),
                new Pageable(0, 2, SortType.ID),
                1);
    }

    @Test
    void shouldReturnUserWithId1() {
        //given 
        when(userDao.findById(any()))
                .thenReturn(givenUser());
        when(daoFactory.createUserDao())
                .thenReturn(userDao);
        //when
        UserProfileDTO user = testedInstance.getUserById(1L);
        //then
        assertThat(user)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("firstName", "Alex")
                .hasFieldOrPropertyWithValue("lastName", "Kere")
                .hasFieldOrPropertyWithValue("role", RoleType.ROLE_USER)
                .hasFieldOrPropertyWithValue("email", "a@a.com");
    }

    private Optional<User> givenUser() {
        return Optional.of(User.builder()
                .id(1L)
                .email("a@a.com")
                .role(RoleType.ROLE_USER)
                .firstName("Alex")
                .lastName("Kere")
                .password("5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8")
                .build());
    }

    @Test
    void shouldReturnUserWithCorrectEmailAndPassword() {
        //given
        when(userDao.findByEmailAndPasswordHash("a@a.com", "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8"))
                .thenReturn(givenUser());
        when(daoFactory.createUserDao())
                .thenReturn(userDao);
        //when
        Optional<User> user = testedInstance.getUserByEmailAndPassword(
                UserLoginDTO.builder()
                        .email("a@a.com")
                        .password("password")
                        .build()
        );
        //then
        assertThat(user.get())
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("email", "a@a.com");
    }
}