package tobias.standup.api.hipchat;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "id",
        "links",
        "mention_name",
        "name",
        "version"
})
/**
 * Created by tobias on 7/06/16.
 */
public class From {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("mention_name")
    private String mentionName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("version")
    private String version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The links
     */
    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    /**
     *
     * @param links
     * The links
     */
    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
    }

    /**
     *
     * @return
     * The mentionName
     */
    @JsonProperty("mention_name")
    public String getMentionName() {
        return mentionName;
    }

    /**
     *
     * @param mentionName
     * The mention_name
     */
    @JsonProperty("mention_name")
    public void setMentionName(String mentionName) {
        this.mentionName = mentionName;
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
     * The version
     */
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version
     * The version
     */
    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
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