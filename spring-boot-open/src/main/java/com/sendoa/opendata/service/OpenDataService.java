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

import static com.sendoa.opendata.builders.PaginationBuilder.aPaginationBuilder;

@Service
public class OpenDataService {

    private static Logger logger = LoggerFactory.getLogger(OpenDataService.class);
    public static final String QUERY_PARAMS_EXPRESSION = "?rows=%s&start=%s&sort=%s %s";
    public static final String FIND_ONE__EXPRESSION = "?fq=code:%s";
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    private Config config;

    public Model findOne(String code) {

        String params = String.format(FIND_ONE__EXPRESSION, code);

        ResultModel result = callOpenAndParse(params);

        return result.getResult().getResults().isEmpty() ? null : result.getResult().getResults().get(0);
    }

    public ResponseModel findFiltered(Integer pageKey,
                                     Integer pageSize,
                                     String sort,
                                     String direction) {

        String params = String.format(QUERY_PARAMS_EXPRESSION, pageSize, pageKey == 1 ? 0 : (pageSize * (pageKey - 1)) -1, sort, direction);

        ResultModel result = callOpenAndParse(params);

        return new ResponseModel(result.getResult().getResults(), aPaginationBuilder()
                .withPageKey(pageKey)
                .withPageSize(pageSize)
                .withSort(sort, direction)
                .withTotalElements(result.getResult().getCount()).build());
    }

    private ResultModel callOpenAndParse(String params) {
        String restResponse = restTemplate.getForObject(config.getHost() + params, String.class);
        ResultModel result;
        try {
            result = om.readValue(restResponse, ResultModel.class);
        } catch (IOException e) {
            logger.error("Problems parsing json body of opendata");
            throw new ApplicationException("Problems parsing json body");
        }
        return result;
    }

}
