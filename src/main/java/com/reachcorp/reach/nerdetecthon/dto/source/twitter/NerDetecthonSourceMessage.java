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
    private String idBio;
    @JsonProperty("tweet")
    private TwitterSourceMessage tweet;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public String getIdBio() {
        return idBio;
    }

    public void setIdBio(String idBio) {
        this.idBio = idBio;
    }

    public TwitterSourceMessage getTweet() {
        return tweet;
    }

    public void setTweet(TwitterSourceMessage tweet) {
        this.tweet = tweet;
    }

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
