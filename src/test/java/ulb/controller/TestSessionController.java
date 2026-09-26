package ulb.controller;

import javafx.stage.Stage;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ulb.controllers.SessionController;
import ulb.database.repository.ExerciseRepository;
import ulb.exceptions.RepositoryException;
import ulb.models.Exercise;
import ulb.models.Programme;
import ulb.utils.Utils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestSessionController extends TestCase {
    private ExerciseRepository repMock;
    private SessionController.ControllerListener controllerListenerMock;
    private Stage stageMock;
    private SessionController sessionController;

    @BeforeEach
    public void setUp() {
        repMock = mock(ExerciseRepository.class);
        controllerListenerMock = mock(SessionController.ControllerListener.class);
        stageMock = mock(Stage.class);
        sessionController = new SessionController(stageMock, controllerListenerMock, repMock);
        sessionController.setBundle("fr");
    }

    @Test
    public void testgetExerciesFromDB_success() throws Exception{
        Programme program = new Programme(1);
        List<Exercise> mockExercises = Arrays.asList(new Exercise(), new Exercise());
        when(repMock.getAllExerciseFromProgram(program.getIdProgramme())).thenReturn(mockExercises);
        Method method = SessionController.class.getDeclaredMethod("getExercisesFromDB", Programme.class);
        method.setAccessible(true);
        method.invoke(sessionController, program);
        assertEquals(mockExercises, program.getExercises());
    }

    @Test
    public void testGetExercisesFromDB_repositoryException() throws Exception {
        Programme program = new Programme(1);
        doThrow(new RepositoryException("Database error")).when(repMock).getAllExerciseFromProgram(program.getIdProgramme());
        Method method = SessionController.class.getDeclaredMethod("getExercisesFromDB", Programme.class);
        method.setAccessible(true);
        try (MockedStatic<Utils> mockedUtils = mockStatic(Utils.class)){
            method.invoke(sessionController, program);
            assertTrue(program.getExercises().isEmpty());
        }
    }

}
