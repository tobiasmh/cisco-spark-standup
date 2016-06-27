package tobias.standup.api.ciscospark.dto;

/**
 * Created by tobias on 25/06/16.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "id",
        "name",
        "resource",
        "event",
        "filter",
        "data"
})
public class CiscoSparkWebhookRequest {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("resource")
    private String resource;
    @JsonProperty("event")
    private String event;
    @JsonProperty("filter")
    private String filter;
    @JsonProperty("data")
    private Data data;
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
     * The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The resource
     */
    @JsonProperty("resource")
    public String getResource() {
        return resource;
    }

    /**
     *
     * @param resource
     * The resource
     */
    @JsonProperty("resource")
    public void setResource(String resource) {
        this.resource = resource;
    }

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
     * The filter
     */
    @JsonProperty("filter")
    public String getFilter() {
        return filter;
    }

    /**
     *
     * @param filter
     * The filter
     */
    @JsonProperty("filter")
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     *
     * @return
     * The data
     */
    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
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