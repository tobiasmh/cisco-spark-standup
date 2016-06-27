package tobias.standup.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import tobias.standup.dao.IStandupDao;
import tobias.standup.entity.StandupStatus;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;

/**
 * Created by tobias on 25/06/16.
 */
public class StandupServiceTest {

    private IStandupDao dao = Mockito.mock(IStandupDao.class);

    private StandupService service;

    @Before
    public void before() {
        this.service = new StandupService(this.dao);
    }

    @Test
    public void getStandupStatusForRoom_DaoCalled_CorrectRoomIdIsPassedToDao() {

        Iterable<StandupStatus> mockIterable = new ArrayList<StandupStatus>();
        Mockito.when(this.dao.findByRoomId(Mockito.anyString())).thenReturn(mockIterable);

        Iterable<StandupStatus> result = this.service.getStandupStatusForRoom("Example_12345");

        Assert.assertThat(result, is(equalTo(mockIterable)));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.dao).findByRoomId(argumentCaptor.capture());

        Assert.assertThat(argumentCaptor.getValue(), is(equalTo("Example_12345")));

    }

    @Test
    public void saveStandupStatus_DaoIsCalled_StandupStatusIsPassedToDao() {

        StandupStatus mockStandupStatus = new StandupStatus();

        this.service.saveStandupStatus(mockStandupStatus);

        ArgumentCaptor<StandupStatus> argumentCaptor = ArgumentCaptor.forClass(StandupStatus.class);

        Mockito.verify(this.dao).save(argumentCaptor.capture());

        Assert.assertThat(argumentCaptor.getValue(), is(equalTo(mockStandupStatus)));

    }
}
