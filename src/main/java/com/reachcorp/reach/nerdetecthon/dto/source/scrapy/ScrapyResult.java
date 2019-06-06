package com.reachcorp.reach.nerdetecthon.dto.source.scrapy;


import com.fasterxml.jackson.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "url",
        "content"
})


public class ScrapyResult {

    @JsonProperty("url")
    private String url;
    @JsonProperty("content")
    private String content;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getDomainFromUrl()
    {
        String domain="";
        try {
                URI uri=new URI(this.url);
                domain=uri.getHost();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return domain;
    }

}
