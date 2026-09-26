package ulb.controller;

import javafx.stage.Stage;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import ulb.controllers.RunSessionController;
import ulb.models.Session;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TestRunSessionController extends TestCase{
    private Session sessionMock;
    private RunSessionController.ControllerListener controllerListenerMock;
    private Stage stageMock;
    private RunSessionController runSessionController;

    @BeforeEach
    public void setUp() {
        sessionMock = mock(Session.class);
        controllerListenerMock = mock(RunSessionController.ControllerListener.class);
        stageMock = mock(Stage.class);
        RunSessionController runSessionController = spy(new RunSessionController(stageMock, controllerListenerMock, sessionMock));

    }

}
