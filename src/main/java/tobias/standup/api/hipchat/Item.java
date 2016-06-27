package tobias.standup.api.hipchat;

/**
 * Created by tobias on 7/06/16.
 */

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "message",
        "room"
})
public class Item {

    @JsonProperty("message")
    private Message message;
    @JsonProperty("room")
    private Room room;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The message
     */
    @JsonProperty("message")
    public Message getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    @JsonProperty("message")
    public void setMessage(Message message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The room
     */
    @JsonProperty("room")
    public Room getRoom() {
        return room;
    }

    /**
     *
     * @param room
     * The room
     */
    @JsonProperty("room")
    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}