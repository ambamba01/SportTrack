package database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import database.dao.UserDao;
import exceptions.RepositoryException;
import models.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestUserRepository {

    @Mock
    private UserDao userDaoMock;
    private UserRepository rep;
    private User validUserTest;
    private User validUserTest1;
    private List<User> all;
    private User invalidUserTest;

    @BeforeEach
    void setUp() throws RepositoryException {
        validUserTest = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f, false, "fr",true,true);
        validUserTest1 = new User(2, "Jo", "Doe", "jo.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f, false, "fr",true,true);
        invalidUserTest = new User();
        userDaoMock = mock(UserDao.class);
        rep = new UserRepository(userDaoMock);
        all = new ArrayList<>();
        all.add(validUserTest);
        all.add(validUserTest1);
        Mockito.lenient().when(userDaoMock.select(validUserTest.getKey())).thenReturn(validUserTest);
        Mockito.lenient().when(userDaoMock.select(-1)).thenReturn(null);
        Mockito.lenient().when(userDaoMock.selectAll()).thenReturn(all);
        Mockito.lenient().when(userDaoMock.select(validUserTest.getMailAddress(), validUserTest.getPassword())).thenReturn(validUserTest);
        Mockito.lenient().when(userDaoMock.select("invalid@mail.com", "invalidpwd")).thenReturn(null);
    }

    @Test
    public void testAddWhenValid() throws Exception {
        rep.add(validUserTest);
        Mockito.verify(userDaoMock, times(1)).insert(any(User.class));
    }

    @Test
    public void testAddWhenNotValid() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            rep.add(invalidUserTest);
        });
        Mockito.verify(userDaoMock, times(0)).insert(any(User.class));
    }

    @Test
    public void testGetExists()throws Exception{
        User result = rep.get(validUserTest.getKey());
        assertEquals(validUserTest, result);
        Mockito.verify(userDaoMock, times(1)).select(validUserTest.getKey());
    }

    @Test
    public void testGetNotExists()throws Exception{
        User result = rep.get(invalidUserTest.getKey());
        Assertions.assertNull(result);
        Mockito.verify(userDaoMock, times(1)).select(invalidUserTest.getKey());
    }

    @Test
    public void testGetAll()throws Exception{
        List<User> result = rep.getAll();
        assertEquals(all, result);
        Mockito.verify(userDaoMock, times(1)).selectAll();
    }

    @Test
    public void testExistTrue() throws Exception {
        User result = rep.exist(validUserTest.getMailAddress(), validUserTest.getPassword());
        assertEquals(validUserTest, result);
        Mockito.verify(userDaoMock, times(1)).select(validUserTest.getMailAddress(), validUserTest.getPassword());
    }

    @Test
    public void testExistFalse() throws Exception {
        User result = rep.exist("invalid@mail.com", "invalidpwd");
        Assertions.assertNull(result);
        Mockito.verify(userDaoMock, times(1)).select("invalid@mail.com", "invalidpwd");
    }

}
