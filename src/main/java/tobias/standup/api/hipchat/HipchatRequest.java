



package tobias.standup.api.hipchat;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "event",
        "item",
        "oauth_client_id",
        "webhook_id"
})
public class HipchatRequest {

    @JsonProperty("event")
    private String event;
    @JsonProperty("item")
    private Item item;
    @JsonProperty("oauth_client_id")
    private String oauthClientId;
    @JsonProperty("webhook_id")
    private Integer webhookId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The event
     */
    @JsonProperty("event")
    public String getEvent() {
        return event;
    }

    /**
     *
     * @param event
     * The event
     */
    @JsonProperty("event")
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     *
     * @return
     * The item
     */
    @JsonProperty("item")
    public Item getItem() {
        return item;
    }

    /**
     *
     * @param item
     * The item
     */
    @JsonProperty("item")
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     *
     * @return
     * The oauthClientId
     */
    @JsonProperty("oauth_client_id")
    public String getOauthClientId() {
        return oauthClientId;
    }

    /**
     *
     * @param oauthClientId
     * The oauth_client_id
     */
    @JsonProperty("oauth_client_id")
    public void setOauthClientId(String oauthClientId) {
        this.oauthClientId = oauthClientId;
    }

    /**
     *
     * @return
     * The webhookId
     */
    @JsonProperty("webhook_id")
    public Integer getWebhookId() {
        return webhookId;
    }

    /**
     *
     * @param webhookId
     * The webhook_id
     */
    @JsonProperty("webhook_id")
    public void setWebhookId(Integer webhookId) {
        this.webhookId = webhookId;
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



