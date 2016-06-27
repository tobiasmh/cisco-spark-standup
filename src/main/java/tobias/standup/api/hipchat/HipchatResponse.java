
        package tobias.standup.api.hipchat;

        import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "color",
        "message",
        "notify",
        "message_format"
})
public class HipchatResponse {

    @JsonProperty("color")
    private String color;
    @JsonProperty("message")
    private String message;
    @JsonProperty("notify")
    private Boolean notify;
    @JsonProperty("message_format")
    private String messageFormat;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The color
     */
    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     * The color
     */
    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
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
     * The notify
     */
    @JsonProperty("notify")
    public Boolean getNotify() {
        return notify;
    }

    /**
     *
     * @param notify
     * The notify
     */
    @JsonProperty("notify")
    public void setNotify(Boolean notify) {
        this.notify = notify;
    }

    /**
     *
     * @return
     * The messageFormat
     */
    @JsonProperty("message_format")
    public String getMessageFormat() {
        return messageFormat;
    }

    /**
     *
     * @param messageFormat
     * The message_format
     */
    @JsonProperty("message_format")
    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
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