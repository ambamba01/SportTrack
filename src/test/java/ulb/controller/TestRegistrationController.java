package ulb.controller;

import javafx.stage.Stage;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ulb.controllers.RegistrationController;
import ulb.database.repository.UserRepository;
import ulb.exceptions.AlreadyExistException;
import ulb.exceptions.RepositoryException;
import ulb.models.User;
import ulb.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestRegistrationController extends TestCase {
    private UserRepository repMock;
    private RegistrationController.ControllerListener controllerListenerMock;
    private Stage stageMock;
    private RegistrationController registerController;
    private User user;

    @BeforeEach
    public void setUp() {
        repMock = mock(UserRepository.class);
        controllerListenerMock = mock(RegistrationController.ControllerListener.class);
        stageMock = mock(Stage.class);
        registerController = new RegistrationController(stageMock, controllerListenerMock, repMock);
        registerController.setBundle("fr");
        user = new User();
    }

    @Test
    public void testCreate_Success() throws Exception {
        when(repMock.add(user)).thenReturn(1);
        Method method = RegistrationController.class.getDeclaredMethod("create", User.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(registerController, user);
        assertTrue(result);
        verify(repMock, times(1)).add(user);
        assertEquals(1, user.getId());
    }

    @Test
    public void testCreate_RepositoryException() throws Exception {
        doThrow(new RepositoryException("Database error")).when(repMock).add(user);
        try (MockedStatic<Utils> mockedUtils = mockStatic(Utils.class)) {
            Method method = RegistrationController.class.getDeclaredMethod("create", User.class);
            method.setAccessible(true);
            boolean result = (boolean) method.invoke(registerController, user);
            assertFalse(result);
            verify(repMock, times(1)).add(user);
        }
        catch (InvocationTargetException e) {
            fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCreate_IllegalArgumentException() throws Exception {
        doThrow(new IllegalArgumentException("Invalid argument")).when(repMock).add(user);
        try (MockedStatic<Utils> mockedUtils = mockStatic(Utils.class)) {
            Method method = RegistrationController.class.getDeclaredMethod("create", User.class);
            method.setAccessible(true);
            boolean result = (boolean) method.invoke(registerController, user);
            assertFalse(result);
            verify(repMock, times(1)).add(user);
        }
        catch (InvocationTargetException e) {
            fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCreate_AlreadyExistException() throws Exception {
        doThrow(new AlreadyExistException("Email already exists")).when(repMock).add(user);
        try (MockedStatic<Utils> mockedUtils = mockStatic(Utils.class)) {
            Method method = RegistrationController.class.getDeclaredMethod("create", User.class);
            method.setAccessible(true);
            boolean result = (boolean) method.invoke(registerController, user);
            assertFalse(result);
            verify(repMock, times(1)).add(user);
        }
        catch (InvocationTargetException e) {
            fail("Caught unexpected exception: " + e.getMessage());
        }
    }

}
