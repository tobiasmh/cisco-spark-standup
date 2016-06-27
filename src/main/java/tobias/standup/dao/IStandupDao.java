package tobias.standup.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tobias.standup.entity.StandupStatus;

/**
 * Created by tobias on 24/06/16.
 */
@Repository
public interface IStandupDao extends CrudRepository<StandupStatus, Long> {

    Iterable<StandupStatus> findByRoomId(String roomId);

}
