package database.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import database.dao.ExerciseProgrammeDao;
import exceptions.ConfigManagerException;
import exceptions.RepositoryException;
import models.ExerciseProgramme;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TestExerciseProgrammeRepository {
    @Mock
    private ExerciseProgrammeDao ExerciseProgrammeDaoMock;

    private ExerciseProgrammeRepository rep;

    private ExerciseProgramme test;
    private ExerciseProgramme test2;
    private List<ExerciseProgramme> exerciseProgrammes;

    @BeforeEach
    void setUp() throws RepositoryException, ConfigManagerException {
        test = new ExerciseProgramme(1,1,1,1);
        test2 = new ExerciseProgramme(2,2,2,2);
        ExerciseProgrammeDaoMock = mock(ExerciseProgrammeDao.class);

        rep = new ExerciseProgrammeRepository(ExerciseProgrammeDaoMock);
        exerciseProgrammes = new ArrayList<>();
        exerciseProgrammes.add(test);
        exerciseProgrammes.add(test2);
        Mockito.lenient().when(ExerciseProgrammeDaoMock.select(test.getKey())).thenReturn(test);
        Mockito.lenient().when(ExerciseProgrammeDaoMock.select(test2.getKey())).thenReturn(test2);
        Mockito.lenient().when(ExerciseProgrammeDaoMock.selectAll()).thenReturn(exerciseProgrammes);
    }
    @Test
    public void testAddWhenValid() throws Exception {
        rep.add(test);
        Mockito.verify(ExerciseProgrammeDaoMock, times(1)).insert(any(ExerciseProgramme.class));
    }

}
