package ulb.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ulb.database.dao.ProgrammeDao;
import ulb.exceptions.RepositoryException;
import ulb.models.Programme;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TestProgrammeRepository {

    @Mock
    private ProgrammeDao ProgrammeDaoMock;

    private ProgrammeRepository rep;

    private Programme test;
    private Programme test2;
    private Programme test3;
    private List<Programme> programmes;

    @BeforeEach
    void setUp() throws RepositoryException {
        test = new Programme(1,"test1",1,"test",1);
        test2 = new Programme(2);
        test3 = new Programme("test3",1,"test",3);
        ProgrammeDaoMock = mock(ProgrammeDao.class);
        rep = new ProgrammeRepository(ProgrammeDaoMock);
        programmes = new ArrayList<>();
        programmes.add(test);
        programmes.add(test2);
        programmes.add(test3);
        Mockito.lenient().when(ProgrammeDaoMock.select(test.getKey())).thenReturn(test);
        Mockito.lenient().when(ProgrammeDaoMock.select(test2.getKey())).thenReturn(test2);
        Mockito.lenient().when(ProgrammeDaoMock.select(test3.getKey())).thenReturn(test3);
        Mockito.lenient().when(ProgrammeDaoMock.selectAll()).thenReturn(programmes);

    }
    @Test
    public void testAddWhenValid() throws Exception {
        rep.add(test);
        rep.add(test3);
        Mockito.verify(ProgrammeDaoMock, times(2)).insert(any(Programme.class));
    }

    @Test
    public void testAddWhenNotValid() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            rep.add(null);
        });
        Mockito.verify(ProgrammeDaoMock, times(0)).insert(any(Programme.class));
    }
    @Test
    public void testRemoveWhenValid() throws Exception{
        rep.remove(test3.getKey());
        Mockito.verify(ProgrammeDaoMock, times(1)).delete(test3.getKey());
    }

    @Test
    public void testGetExists()throws Exception{
        Programme result = rep.get(test2.getKey());
        assertEquals(test2, result);
        Mockito.verify(ProgrammeDaoMock, times(1)).select(test2.getKey());
    }

    @Test
    public void testGetNotExists()throws Exception{
        Programme result = rep.get(4);
        Assertions.assertNull(result);
        Mockito.verify(ProgrammeDaoMock, times(1)).select(4);
    }

}
