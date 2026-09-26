package database.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import database.dao.ExerciseDao;
import exceptions.RepositoryException;
import models.Exercise;
import models.Muscle;
import models.Programme;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TestExerciseRepository {

    @Mock
    private ExerciseDao exerciseDaoMock;
    private ExerciseRepository rep;
    private Exercise validExerciseTest;
    private Exercise validExercise1Test;
    private Exercise exerciseWithPauseTest;
    private Exercise exerciseWithPauseTest2;
    private Exercise validExercise2Test;
    private List<Exercise> isMusculation;
    private Muscle muscleValidTest;
    private Muscle muscleInvalidTest;
    private List<Exercise> isMusculationWithPause;
    private List<Exercise> all;
    private List<Exercise> all1;
    private Exercise invalidExercise;
    private Programme programmeTest;

    @BeforeEach
    void setUp() throws RepositoryException{
        isMusculationWithPause = new ArrayList<>();
        validExerciseTest = new Exercise(1, 1, "course à pied", "course en haute montagne", "Endurance", 5 ,"logo.png", new byte[]{0}, 18000, 1000);
        validExercise1Test = new Exercise(2, 1, "squats", "exercice difficile", "Musculation",5, "logo.png", new byte[]{0},600, 2000 );
        invalidExercise = new Exercise();
        exerciseWithPauseTest = new Exercise(3, 1, "Pause", "Repos", null, 1, "logo.png", new byte[]{0}, 30, 0);
        validExercise2Test = new Exercise(4, "altères", "très dure", "Musculation", 4, "logo.png", new byte[]{0}, 300, 1000);
        exerciseWithPauseTest2 = new Exercise(5, 1, "Pause", "Repos", "Musculation", 2, "logo.png",new byte[]{0}, 30, 0);
        programmeTest = new Programme(1, "renforcement musculaire", 5, "dure", 1);
        muscleValidTest = new Muscle(1, "Buccinator");
        muscleInvalidTest = new Muscle(1, "");
        exerciseDaoMock = mock(ExerciseDao.class);
        rep = new ExerciseRepository(exerciseDaoMock);
        all = new ArrayList<>();
        all.add(validExerciseTest);
        all.add(validExercise1Test);
        all1 = new ArrayList<>();
        all1.add(validExerciseTest);
        all1.add(validExercise1Test);
        all1.add(exerciseWithPauseTest);
        programmeTest.setExercises(all1);
        isMusculation = new ArrayList<>();
        isMusculation.add(validExercise1Test);
        isMusculation.add(validExercise2Test);
        isMusculationWithPause.add(validExercise1Test);
        isMusculationWithPause.add(validExercise2Test);
        isMusculationWithPause.add(exerciseWithPauseTest2);
        Mockito.lenient().when(exerciseDaoMock.select(validExerciseTest.getKey())).thenReturn(validExerciseTest);
        Mockito.lenient().when(exerciseDaoMock.select(-1)).thenReturn(null);
        Mockito.lenient().when(exerciseDaoMock.selectAll()).thenReturn(all);
        Mockito.lenient().when(exerciseDaoMock.getAllWithId(validExerciseTest.getUserId())).thenReturn(all);
        Mockito.lenient().when(exerciseDaoMock.getAllWithIdWithoutThePauses(validExerciseTest.getUserId())).thenReturn(all);
        Mockito.lenient().when(exerciseDaoMock.getAllExerciseFromProgram(programmeTest.getUserId())).thenReturn(all1);
        Mockito.lenient().when(exerciseDaoMock.selectAllByType(validExerciseTest.getType(), validExerciseTest.getUserId())).thenReturn(isMusculation);
        Mockito.lenient().when(exerciseDaoMock.selectAllByType(null, exerciseWithPauseTest.getUserId())).thenReturn(null);
        Mockito.lenient().when(exerciseDaoMock.selectAllByTypeWithoutThePauses(validExerciseTest.getType(), validExerciseTest.getUserId())).thenReturn(isMusculation);
        Mockito.lenient().when(exerciseDaoMock.selectAllByDifficulty("5", validExerciseTest.getUserId())).thenReturn(all);
        Mockito.lenient().when(exerciseDaoMock.selectAllByDifficulty(null, invalidExercise.getUserId())).thenReturn(null);
        Mockito.lenient().when(exerciseDaoMock.selectAllByMuscle("", muscleInvalidTest.getId())).thenReturn(null);
    }

    @Test
    public void testAddWhenValid() throws Exception{
        rep.add(validExerciseTest);
        Mockito.verify(exerciseDaoMock, times(1)).insert(any(Exercise.class));
    }

    @Test
    public void testAddWhenNotValid() throws Exception{
        assertThrows(IllegalArgumentException.class, ()->{
            rep.add(invalidExercise);
        });
        Mockito.verify(exerciseDaoMock, times(0)).insert(any(Exercise.class));
    }

    @Test
    public void testGetExists()throws Exception{
        Exercise result = rep.get(validExerciseTest.getKey());
        assertEquals(validExerciseTest, result);
        Mockito.verify(exerciseDaoMock, times(1)).select(validExerciseTest.getKey());
    }

    @Test
    public void testGetAll() throws Exception{
        List<Exercise> result = rep.getAll();
        assertEquals(result, all);
        Mockito.verify(exerciseDaoMock, times(1)).selectAll();
    }

    @Test
    public void testGetAllWithId()throws Exception{
        List<Exercise> result = rep.getAllWithId(validExerciseTest.getUserId());
        assertEquals(result, all);
        Mockito.verify(exerciseDaoMock, times(1)).getAllWithId(validExerciseTest.getUserId());
    }

    @Test
    public void testGetAllWithIdWithoutThePauses() throws Exception{
        List<Exercise> result = rep.getAllWithIdWithoutThePauses(validExerciseTest.getUserId());
        assertEquals(result, all);
        Mockito.verify(exerciseDaoMock, times(1)).getAllWithIdWithoutThePauses(validExerciseTest.getUserId());
    }

    @Test
    public void testGetAllExerciseFromProgram()throws Exception{
        List<Exercise> result = rep.getAllExerciseFromProgram(programmeTest.getIdProgramme());
        assertEquals(result, all1);
        Mockito.verify(exerciseDaoMock, times(1)).getAllExerciseFromProgram(programmeTest.getIdProgramme());
    }

    @Test
    public void testGetAllByType()throws Exception{
        List<Exercise> result = rep.getAllByType(validExerciseTest.getType(), validExerciseTest.getUserId());
        assertEquals(result, isMusculation);
        Mockito.verify(exerciseDaoMock, times(1)).selectAllByType(validExerciseTest.getType(), validExerciseTest.getUserId());
    }

    @Test
    public void testGetNotAllByType()throws Exception{
        assertThrows(IllegalArgumentException.class, ()->{
            rep.getAllByType(exerciseWithPauseTest.getType(),exerciseWithPauseTest.getUserId());
        });
        Mockito.verify(exerciseDaoMock, times(0)).selectAllByType(exerciseWithPauseTest.getType(), exerciseWithPauseTest.getUserId());
    }

    @Test
    public void testGetAllByTypeWithoutThePauses()throws Exception{
        List<Exercise> result = rep.getAllByTypeWithoutThePauses(validExerciseTest.getType(), validExerciseTest.getUserId());
        assertEquals(result, isMusculation);
        Mockito.verify(exerciseDaoMock, times(1)).selectAllByTypeWithoutThePauses(validExerciseTest.getType(), validExerciseTest.getUserId());
    }

    @Test
    public void testGetNotAllByTypeWithoutPauses()throws Exception{}

    @Test
    public void testGetAllByDifficulty()throws Exception{
        List<Exercise> result = rep.getAllByDifficulty(""+ validExerciseTest.getDifficulty(), validExerciseTest.getUserId());
        assertEquals(result.size(), isMusculation.size());
        Mockito.verify(exerciseDaoMock, times(1)).selectAllByDifficulty("" + validExerciseTest.getDifficulty(), validExerciseTest.getUserId());
    }

    @Test
    public void testGetNotAllByMuscle() throws Exception{
        assertThrows(IllegalArgumentException.class, ()->{
            rep.getAllByMuscle(muscleInvalidTest.getName(), muscleInvalidTest.getId());
        });
        Mockito.verify(exerciseDaoMock, times(0)).selectAllByMuscle(muscleInvalidTest.getName(), muscleInvalidTest.getId());
    }
}

