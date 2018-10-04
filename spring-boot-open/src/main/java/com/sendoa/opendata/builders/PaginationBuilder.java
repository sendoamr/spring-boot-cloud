package com.sendoa.opendata.builders;

import com.sendoa.opendata.model.Links;
import com.sendoa.opendata.model.Pagination;

public final class PaginationBuilder {

    public static final String OPEN_DATA_URI = "/v1/data?pageKey=%s";
    private Pagination pagination = new Pagination();
    public static PaginationBuilder aPaginationBuilder() {
        return new PaginationBuilder();
    }


    public PaginationBuilder withPageKey(final Integer pageKey) {
        pagination.setPage(pageKey);
        return this;
    }

    public PaginationBuilder withPageSize(final Integer size) {
        pagination.setPageSize(size);
        return this;
    }


    public PaginationBuilder withTotalElements(final Integer totalElements) {
        pagination.setTotal(totalElements);
        return this;
    }


    public Pagination build() {
        final Links links = new Links();
        final Integer numPages = pagination.getTotal() / pagination.getPageSize();
        final Integer pageKey = pagination.getPage();

        links.setFirstPage(String.format(OPEN_DATA_URI, 1));
        links.setLastPage(String.format(OPEN_DATA_URI, numPages));
        links.setNext(String.format(OPEN_DATA_URI, (pageKey < numPages ? (pageKey+1) : numPages)));
        links.setPrev(String.format(OPEN_DATA_URI, (pageKey-1)));
        pagination.setLinks(links);
        pagination.setNumPages(numPages);

        return pagination;
    }

}
