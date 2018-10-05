package com.sendoa.opendata.model.open;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendoa.opendata.model.Model;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("sort")
    private String sort;
    @JsonProperty("results")
    private List<Model> results;

}
