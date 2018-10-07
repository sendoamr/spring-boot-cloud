package com.sendoa.opendata.builder;

import com.sendoa.opendata.model.response.Links;
import com.sendoa.opendata.model.response.Pagination;

public final class PaginationBuilder {

    public static final String OPEN_DATA_URI = "/api/v1/packages?pageKey=%s&pageSize=%s";
    private Pagination pagination = new Pagination();
    public static PaginationBuilder aPaginationBuilder() {
        return new PaginationBuilder();
    }
    private String queryParms = "";

    public PaginationBuilder withPageKey(final Integer pageKey) {
        pagination.setPage(pageKey);
        return this;
    }

    public PaginationBuilder withPageSize(final Integer size) {
        pagination.setPageSize(size);
        return this;
    }

    public PaginationBuilder withSort(String sort, String direction) {
        this.queryParms = "".equals(sort) ? "" : String.format("&sort=%s&direction=%s", sort, direction);
        return this;
    }


    public PaginationBuilder withTotalElements(final Integer totalElements) {
        pagination.setTotal(totalElements);
        return this;
    }


    public Pagination build() {
        //Create link variables
        final Links links = new Links();
        final Integer numPages = pagination.getTotal() / pagination.getPageSize();
        final Integer pageKey = pagination.getPage();

        //Set the link pagination
        links.setFirstPage(String.format(OPEN_DATA_URI, 1, pagination.getPageSize()).concat(queryParms));
        links.setLastPage(String.format(OPEN_DATA_URI, numPages, pagination.getPageSize()).concat(queryParms));
        links.setNext(String.format(OPEN_DATA_URI, (pageKey < numPages ? (pageKey+1) : numPages), pagination.getPageSize()).concat(queryParms));
        links.setPrev(String.format(OPEN_DATA_URI, (pageKey == 1 ? 1 : pageKey-1), pagination.getPageSize()).concat(queryParms));
        pagination.setLinks(links);
        pagination.setNumPages(numPages);

        //return formate pagination node
        return pagination;
    }

}
