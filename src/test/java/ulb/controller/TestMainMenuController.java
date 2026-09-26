package ulb.controller;

import javafx.stage.Stage;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ulb.controllers.MainMenuController;
import ulb.models.User;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestMainMenuController extends TestCase {

    @Mock
    private MainMenuController.ControllerListener listenerMock;
    @Mock
    private Stage stageMock;
    private User user;
    private MainMenuController mainMenuController;

    @BeforeEach
    public void setUp(){
        user = new User();
        listenerMock = mock(MainMenuController.ControllerListener.class);
        stageMock = mock(Stage.class);
        mainMenuController = new MainMenuController(stageMock, user, listenerMock);
    }

    @Test
    public void testGoNextMenu() throws Exception {
        List<String> nextMenuList = Arrays.asList("addSession", "addExercice", "showProgramme", "profileUser", "settings");
        List<String> listenerMethods = Arrays.asList("showSession", "showListExercises", "showProgramme", "showMyProfile", "showSettings");
        for (int i = 0; i < nextMenuList.size(); i++) {
            String nextMenu = nextMenuList.get(i);
            String listenerMethod = listenerMethods.get(i);
            reset(listenerMock, stageMock);
            mainMenuController.goNextMenu(nextMenu);
            if(!nextMenu.equals("addSession")) verify(stageMock).hide();
            Method method = MainMenuController.ControllerListener.class.getMethod(listenerMethod);
            method.invoke(verify(listenerMock), (Object[]) null);
        }
    }

    @Test
    public void testDisconnect() {
        mainMenuController.disconnect();
        verify(listenerMock).setCurrentUser(null);
        verify(stageMock).hide();
        verify(listenerMock).showHome();
        verifyNoMoreInteractions(listenerMock, stageMock);
    }

}
