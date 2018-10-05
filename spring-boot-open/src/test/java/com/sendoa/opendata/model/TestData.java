package com.sendoa.opendata.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.HashMap;


@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestData {
    private String title;
    private String method;
    private String uri;
    private HashMap<String, String> headers;
}
