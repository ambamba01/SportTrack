package controller;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import controllers.ProgrammeController;
import database.repository.ProgrammeRepository;
import models.User;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestProgramController {
    private ProgrammeRepository repMock;
    private ProgrammeController.ControllerListener controllerListenerMock;
    private Stage stageMock;
    private ProgrammeController programmeController;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        repMock = mock(ProgrammeRepository.class);
        controllerListenerMock = mock(ProgrammeController.ControllerListener.class);
        stageMock = mock(Stage.class);
        programmeController = new ProgrammeController(stageMock, user, controllerListenerMock);
    }

    @Test
    public void testShowHome() {
        programmeController.showHome();
        verify(stageMock, times(1)).hide();
        verify(controllerListenerMock, times(1)).showHome();
    }

    @Test
    public void testGoToAddProgramme() {
        programmeController.goToaddProgramme();
        verify(stageMock, times(1)).hide();
        verify(controllerListenerMock, times(1)).showAddProgramme();
    }

}
