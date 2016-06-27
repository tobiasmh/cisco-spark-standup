package tobias.standup.service;

import tobias.standup.entity.StandupStatus;

/**
 * Created by tobias on 24/06/16.
 */
public interface IStandupService {

    Iterable<StandupStatus> getStandupStatusForRoom(String roomId);

    void saveStandupStatus(StandupStatus standupStatus);

}
