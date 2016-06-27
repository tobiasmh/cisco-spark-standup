package tobias.standup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by tobias on 7/06/16.
 */
@Entity
public class StandupStatus {

    @Column()
    private String message;

    @Column()
    private String userId;

    @Column()
    private String userDisplayName;

    @Column()
    private Date creationDate;

    @Column()
    private String roomId;

    // TODO this will not be unique across multiple HipChat server instances. It needs to be prefixed with a server identifier.
    @Id()
    private String id;
    public String id() {
        return String.format("%s_%s", userId, roomId);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void populateId() {
        this.id = String.format("%s_%s", userId, roomId);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
