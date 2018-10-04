package com.sendoa.opendata.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Links {

    @Getter
    @Setter
    private String firstPage;

    @Getter
    @Setter
    private String lastPage;

    @Getter
    @Setter
    private String next;

    @Getter
    @Setter
    private String prev;
}
