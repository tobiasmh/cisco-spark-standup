
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
        "is_archived",
        "links",
        "name",
        "privacy",
        "version"
})
public class Room {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("is_archived")
    private Boolean isArchived;
    @JsonProperty("links")
    private Links_ links;
    @JsonProperty("name")
    private String name;
    @JsonProperty("privacy")
    private String privacy;
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
     * The isArchived
     */
    @JsonProperty("is_archived")
    public Boolean getIsArchived() {
        return isArchived;
    }

    /**
     *
     * @param isArchived
     * The is_archived
     */
    @JsonProperty("is_archived")
    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    /**
     *
     * @return
     * The links
     */
    @JsonProperty("links")
    public Links_ getLinks() {
        return links;
    }

    /**
     *
     * @param links
     * The links
     */
    @JsonProperty("links")
    public void setLinks(Links_ links) {
        this.links = links;
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
     * The privacy
     */
    @JsonProperty("privacy")
    public String getPrivacy() {
        return privacy;
    }

    /**
     *
     * @param privacy
     * The privacy
     */
    @JsonProperty("privacy")
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
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