package ulb.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ulb.database.dao.MuscleDao;
import ulb.exceptions.RepositoryException;
import ulb.models.Muscle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TestMuscleRepository {
    @Mock
    private MuscleDao muscleDaoMock;
    private MuscleRepository rep;
    private Muscle muscleValidTest;
    private Muscle muscleValidTest1;
    private List<Muscle> all;



    @BeforeEach
    void setUp() throws RepositoryException {
        muscleValidTest = new Muscle(1, "testMuscle");
        muscleValidTest1 = new Muscle(2, "testMuscle1");
        muscleDaoMock = mock(MuscleDao.class);
        rep = new MuscleRepository(muscleDaoMock);
        all = new ArrayList<>();
        all.add(muscleValidTest);
        all.add(muscleValidTest1);
        Mockito.lenient().when(muscleDaoMock.select(muscleValidTest.getKey())).thenReturn(muscleValidTest);
        Mockito.lenient().when(muscleDaoMock.selectAll()).thenReturn(all);
    }

    @Test
    public void testAddWhenValid() throws Exception {
        rep.add(muscleValidTest);
        Mockito.verify(muscleDaoMock, times(1)).insert(any(Muscle.class));
    }

    @Test
    public void testAddWhenNotValid() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
                    rep.add(null);
        });
        Mockito.verify(muscleDaoMock, times(0)).insert(any(Muscle.class));
    }

    @Test
    public void testGetExists()throws Exception{
        Muscle result = rep.get(muscleValidTest.getKey());
        assertEquals(muscleValidTest, result);
        Mockito.verify(muscleDaoMock, times(1)).select(muscleValidTest.getKey());
    }

    @Test
    public void testGetNotExists()throws Exception{
        Muscle result = rep.get(3);
        Assertions.assertNull(result);
        Mockito.verify(muscleDaoMock, times(1)).select(3);
    }

    @Test
    public void testGetAll()throws Exception{
        List<Muscle> result = rep.getAll();
        assertEquals(all, result);
        Mockito.verify(muscleDaoMock, times(1)).selectAll();
    }

}
