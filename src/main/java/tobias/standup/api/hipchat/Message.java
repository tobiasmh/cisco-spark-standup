package tobias.standup.api.hipchat;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "date",
        "from",
        "id",
        "mentions",
        "message",
        "type"
})
public class Message {

    @JsonProperty("date")
    private String date;
    @JsonProperty("from")
    private From from;
    @JsonProperty("id")
    private String id;
    @JsonProperty("mentions")
    private List<Object> mentions = new ArrayList<Object>();
    @JsonProperty("message")
    private String message;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The date
     */
    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The from
     */
    @JsonProperty("from")
    public From getFrom() {
        return from;
    }

    /**
     *
     * @param from
     * The from
     */
    @JsonProperty("from")
    public void setFrom(From from) {
        this.from = from;
    }

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
     * The mentions
     */
    @JsonProperty("mentions")
    public List<Object> getMentions() {
        return mentions;
    }

    /**
     *
     * @param mentions
     * The mentions
     */
    @JsonProperty("mentions")
    public void setMentions(List<Object> mentions) {
        this.mentions = mentions;
    }

    /**
     *
     * @return
     * The message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
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