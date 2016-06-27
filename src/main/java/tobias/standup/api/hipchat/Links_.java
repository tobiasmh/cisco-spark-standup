
package tobias.standup.api.hipchat;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "participants",
        "self",
        "webhooks"
})
public class Links_ {

    @JsonProperty("participants")
    private String participants;
    @JsonProperty("self")
    private String self;
    @JsonProperty("webhooks")
    private String webhooks;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The participants
     */
    @JsonProperty("participants")
    public String getParticipants() {
        return participants;
    }

    /**
     *
     * @param participants
     * The participants
     */
    @JsonProperty("participants")
    public void setParticipants(String participants) {
        this.participants = participants;
    }

    /**
     *
     * @return
     * The self
     */
    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    /**
     *
     * @param self
     * The self
     */
    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     *
     * @return
     * The webhooks
     */
    @JsonProperty("webhooks")
    public String getWebhooks() {
        return webhooks;
    }

    /**
     *
     * @param webhooks
     * The webhooks
     */
    @JsonProperty("webhooks")
    public void setWebhooks(String webhooks) {
        this.webhooks = webhooks;
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