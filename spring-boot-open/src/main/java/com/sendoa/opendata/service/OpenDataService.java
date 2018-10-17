package com.sendoa.opendata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendoa.opendata.configuration.Config;
import com.sendoa.opendata.error.ApplicationException;
import com.sendoa.opendata.model.ResponseModel;
import com.sendoa.opendata.model.open.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.sendoa.opendata.model.Model;
import java.io.IOException;
import java.util.List;

import static com.sendoa.opendata.builder.PaginationBuilder.aPaginationBuilder;

@Service
public class OpenDataService {

    public static final int MAX_SOLR_REGISTERS = 2147483647;
    public static final int INIT_REGISTER = 0;
    public static final String QUERY_PARAMS_EXPRESSION = "?rows=%s&start=%s";
    public static final String QUERY_PARAMS_SORT = "&sort=%s %s";
    public static final String FIND_ONE_EXPRESSION = "?fq=code:%s";

    private static Logger logger = LoggerFactory.getLogger(OpenDataService.class);

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Config config;

    public Model findOne(String code) {
        String params = String.format(FIND_ONE_EXPRESSION, code);

        ResultModel result = callOpenAndParse(params);

        return result.getResult().getResults().isEmpty() ? null : result.getResult().getResults().get(0);
    }

    public ResponseModel findFiltered(Integer pageKey,
                                     Integer pageSize,
                                     String sort,
                                     String direction) {
        //Traslate sort field to open format and generate uri
        String openSort = translateSort(sort);
        String params = String.format(QUERY_PARAMS_EXPRESSION + ("".equals(openSort) ? "" : QUERY_PARAMS_SORT), pageSize, pageKey == 1 ? 0 : (pageSize * (pageKey - 1)) -1, openSort, direction);

        //Call to open and get bean
        ResultModel result = callOpenAndParse(params);

        //Return list with pagination
        return new ResponseModel(result.getResult().getResults(), aPaginationBuilder()
                .withPageKey(pageKey)
                .withPageSize(pageSize)
                .withSort(sort, direction)
                .withTotalElements(result.getResult().getCount()).build());
    }

    public List<Model> findAll() {

        String params = String.format(QUERY_PARAMS_EXPRESSION, MAX_SOLR_REGISTERS, INIT_REGISTER);

        ResultModel result = callOpenAndParse(params);

        return result.getResult().getResults();
    }

    private ResultModel callOpenAndParse(String params) {
        String restResponse = restTemplate.getForObject(config.getHost() + params, String.class);
        ResultModel result;
        try {
            result = om.readValue(restResponse, ResultModel.class);
        } catch (IOException e) {
            logger.error("Problems parsing json body of opendata", e);
            throw new ApplicationException("Problems parsing json body");
        }
        return result;
    }

    private String translateSort(String sort) {
        if (sort != null && "description".equals(sort)) {
            return "notes_translated.en";
        }
        return sort;
    }
}
