package ulb.database.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ulb.database.dao.ExerciseMuscleDao;
import ulb.exceptions.RepositoryException;
import ulb.models.ExerciseMuscle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TestExerciseMuscleRepository {

    @Mock
    private ExerciseMuscleDao exerciseMuscleDao;
    private ExerciseMuscleRepository rep;
    private ExerciseMuscle exerciseMuscleValidTest;
    private ExerciseMuscle exerciseMuscleValid2Test;
    private ExerciseMuscle exerciseMuscleInvalidTest;
    private List<ExerciseMuscle> all;

    @BeforeEach
    void setUp() throws RepositoryException{
        exerciseMuscleValidTest = new ExerciseMuscle(1, 1);
        exerciseMuscleValid2Test = new ExerciseMuscle(2, 2);
        exerciseMuscleInvalidTest = new ExerciseMuscle(3, 0);
        all = new ArrayList<ExerciseMuscle>();
        all.add(exerciseMuscleValidTest);
        all.add(exerciseMuscleValid2Test);
        exerciseMuscleDao = mock(ExerciseMuscleDao.class);
        rep = new ExerciseMuscleRepository(exerciseMuscleDao);
        Mockito.lenient().when(exerciseMuscleDao.insert(exerciseMuscleValidTest)).thenReturn(exerciseMuscleValidTest.getMuscleId());
        Mockito.lenient().when(exerciseMuscleDao.insert(exerciseMuscleInvalidTest)).thenReturn(1);
        Mockito.lenient().when(exerciseMuscleDao.selectAll()).thenReturn(all);
        Mockito.lenient().when(exerciseMuscleDao.select(exerciseMuscleValidTest.getKey())).thenReturn(exerciseMuscleValidTest);
        Mockito.lenient().when(exerciseMuscleDao.select(exerciseMuscleInvalidTest.getKey())).thenReturn(exerciseMuscleInvalidTest);
        Mockito.lenient().when(exerciseMuscleDao.selectMuscleWithExId(exerciseMuscleValidTest.getExerciseId())).thenReturn(exerciseMuscleValidTest);
    }

    @Test
    public void testAddWhenValid()throws Exception{
        int result = rep.add(exerciseMuscleValidTest);
        assertEquals(result, exerciseMuscleValidTest.getMuscleId());
        Mockito.verify(exerciseMuscleDao, times(1)).insert(any(ExerciseMuscle.class));
    }

    @Test
    public void testGetAll()throws Exception{
        List<ExerciseMuscle> result = rep.getAll();
        assertEquals(result, all);
        Mockito.verify(exerciseMuscleDao, times(1)).selectAll();
    }

    @Test
    public void testGetExists()throws Exception{
        ExerciseMuscle result = rep.get(exerciseMuscleValidTest.getKey());
        assertEquals( exerciseMuscleValidTest,result);
        Mockito.verify(exerciseMuscleDao, times(1)).select(exerciseMuscleValidTest.getKey());
    }

    @Test
    public void testGetMuscleForExercise()throws Exception{
        ExerciseMuscle result = rep.getMuscleForExercise(exerciseMuscleValidTest.getExerciseId());
        assertEquals(result, exerciseMuscleValidTest);
        Mockito.verify(exerciseMuscleDao, times(1)).selectMuscleWithExId(exerciseMuscleValidTest.getExerciseId());
    }
}

