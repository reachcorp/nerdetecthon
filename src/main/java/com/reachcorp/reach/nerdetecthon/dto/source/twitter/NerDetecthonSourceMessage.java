package com.reachcorp.reach.nerdetecthon.dto.source.twitter;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "idBio",
        "tweet"
})

public class NerDetecthonSourceMessage {


    @JsonProperty("idBio")
    private Long idBio;
    @JsonProperty("tweet")
    private TwitterSourceMessage tweet;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();



    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getText() {
        return tweet.getText();
    }

    public String getCreatedAt() {
        return tweet.getCreatedAt();
    }

}
