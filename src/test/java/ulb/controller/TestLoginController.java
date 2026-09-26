package controller;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import controllers.LoginController;
import database.repository.UserRepository;
import exceptions.RepositoryException;
import models.User;
import utils.Utils;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;


import java.lang.reflect.Method;

@ExtendWith(MockitoExtension.class)
public class TestLoginController extends TestCase{

    private UserRepository repMock;
    private LoginController.ControllerListener controllerListenerMock;
    private Stage stageMock;
    private LoginController loginController;

    @BeforeEach
    public void setUp() {
        repMock = mock(UserRepository.class);
        controllerListenerMock = mock(LoginController.ControllerListener.class);
        stageMock = mock(Stage.class);
        loginController = new LoginController(stageMock, controllerListenerMock, repMock);
        loginController.setBundle("fr");
    }

    private User setupUser(String email, String password) {
        User user = new User();
        user.setMailAddress(email);
        user.setPassword(User.hashPassword(password));
        return user;
    }


    @Test
    public void testVerifyConnexion_ValidCredentials() throws RepositoryException {
        User user = setupUser("test@example.com", "Password");
        when(repMock.exist(user.getMailAddress(), user.getPassword())).thenReturn(user);
        loginController.verifyConnexion("test@example.com", "Password");
        verify(controllerListenerMock).setCurrentUser(user);
        verify(stageMock).hide();
        verify(controllerListenerMock).showHome();
    }

    @Test
    public void testVerifyConnexion_UserNotFound(){
        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {
            // Configure the static method to do nothing
            utilities.when(() -> Utils.displayPopup(anyString())).thenAnswer(invocation -> null);
            Utils.displayPopup("Le compte n'existe pas.\n Pensez à créer un compte.");
            loginController.verifyConnexion("wrongMail@example.com", "wrongPassword");
            verify(controllerListenerMock, never()).setCurrentUser(any(User.class));
            verify(stageMock, never()).hide();
            verify(controllerListenerMock, never()).showHome();
        }
    }

    @Test
    public void testFindUser_ValidCredentials() throws RepositoryException{
        User user = setupUser("test@example.com", "Password");
        when(repMock.exist(user.getMailAddress(), user.getPassword())).thenReturn(user);
        try {
            Method method = LoginController.class.getDeclaredMethod("findUser", String.class, String.class);
            method.setAccessible(true);
            User actualUser = (User) method.invoke(loginController, user.getMailAddress(), user.getPassword());
            verify(repMock, times(1)).exist(user.getMailAddress(), user.getPassword());
            assertEquals(user, actualUser);
        } catch (Exception e){
            fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testFindUser_UserNotFound(){
        try {
            Method method = LoginController.class.getDeclaredMethod("findUser", String.class, String.class);
            method.setAccessible(true);
            User actualUser = (User) method.invoke(loginController, "wrongMail@example.com", "wrongPassword");
            verify(repMock, times(1)).exist("wrongMail@example.com", "wrongPassword");
            assertNull(actualUser);
        } catch (Exception e){
            fail("Caught unexpected exception: " + e.getMessage());
        }
    }

}
