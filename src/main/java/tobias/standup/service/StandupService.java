package tobias.standup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tobias.standup.dao.IStandupDao;
import tobias.standup.entity.StandupStatus;

/**
 * Created by tobias on 25/06/16.
 */
@Service
public class StandupService implements IStandupService {

    private IStandupDao standupDao;

    @Autowired
    public StandupService(IStandupDao standupDao) {
        this.standupDao = standupDao;
    }

    @Override
    public Iterable<StandupStatus> getStandupStatusForRoom(String roomId) {
        return this.standupDao.findByRoomId(roomId);
    }

    @Override
    public void saveStandupStatus(StandupStatus standupStatus) {
        standupStatus.populateId();
        this.standupDao.save(standupStatus);
    }
}
