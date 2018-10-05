package com.sendoa.opendata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sendoa.opendata.model.response.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseModel {

    @Getter
    @Setter
    private List<Model> data;

    @Getter
    @Setter
    private Pagination pagination;

}
