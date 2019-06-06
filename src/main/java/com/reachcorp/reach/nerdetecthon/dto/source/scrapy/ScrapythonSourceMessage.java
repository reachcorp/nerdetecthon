package com.reachcorp.reach.nerdetecthon.dto.source.scrapy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "idBio",
        "scrapyResults"
})

public class ScrapythonSourceMessage {

    @JsonProperty("idBio")
    private String idBio;
    @JsonProperty("scrapyResults")
    private List<ScrapyResult> scrapyResults= null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getIdBio() {
        return idBio;
    }

    public void setIdBio(String idBio) {
        this.idBio = idBio;
    }

    public List<ScrapyResult> getScrapyResults() {
        return scrapyResults;
    }

    public void setScrapyResults(List<ScrapyResult> scrapyResults) {
        this.scrapyResults = scrapyResults;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

}
