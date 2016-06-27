package tobias.standup.api.ciscospark.dto;

/**
 * Created by tobias on 25/06/16.
 */

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "id",
        "roomId",
        "personId",
        "personEmail",
        "created"
})
public class Data {

    @JsonProperty("id")
    private String id;
    @JsonProperty("roomId")
    private String roomId;
    @JsonProperty("personId")
    private String personId;
    @JsonProperty("personEmail")
    private String personEmail;
    @JsonProperty("created")
    private String created;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The roomId
     */
    @JsonProperty("roomId")
    public String getRoomId() {
        return roomId;
    }

    /**
     *
     * @param roomId
     * The roomId
     */
    @JsonProperty("roomId")
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    /**
     *
     * @return
     * The personId
     */
    @JsonProperty("personId")
    public String getPersonId() {
        return personId;
    }

    /**
     *
     * @param personId
     * The personId
     */
    @JsonProperty("personId")
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     *
     * @return
     * The personEmail
     */
    @JsonProperty("personEmail")
    public String getPersonEmail() {
        return personEmail;
    }

    /**
     *
     * @param personEmail
     * The personEmail
     */
    @JsonProperty("personEmail")
    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    /**
     *
     * @return
     * The created
     */
    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    /**
     *
     * @param created
     * The created
     */
    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
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

