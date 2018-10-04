package com.sendoa.opendata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultModel {

    @JsonProperty("success")
    private Boolean status;
    @JsonProperty("result")
    private Result result;

}
