package com.sendoa.opendata.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Pagination {

    @Getter
    @Setter
    private Integer page;

    @Getter
    @Setter
    private Integer numPages;

    @Getter
    @Setter
    private Integer pageSize;

    @Getter
    @Setter
    private Integer total;

    @Getter
    @Setter
    private Links links;
}
