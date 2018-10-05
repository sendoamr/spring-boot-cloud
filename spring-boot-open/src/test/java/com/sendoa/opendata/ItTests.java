package com.sendoa.opendata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendoa.opendata.config.TestConfig;
import com.sendoa.opendata.model.MockData;
import com.sendoa.opendata.model.ResponseData;
import com.sendoa.opendata.model.TestData;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseCreator;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItTests {

    private ObjectMapper om = new ObjectMapper();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    protected RestTemplate restTemplate;
    protected MockRestServiceServer mockOpenServer;

    /**
     * Principal method that loop resources and launch the dynamic tests
     */
    @DisplayName("Open data json dynamic test")
    @TestFactory
    Iterable<DynamicTest> dynamicJsonTests() throws IOException {
        List<DynamicTest> testsToRun = new ArrayList();
        Iterator testJsonFiles = getTestFiles().iterator();

        //Loop all json in folder test and launch test
        while(testJsonFiles.hasNext()) {
            String test = String.valueOf(testJsonFiles.next());

            //Get data to launch test and request and launch the test
            TestData serviceRequest = om.readValue(new File(ItTests.class.getResource(String.format("/tests/%s", test)).getFile()), TestData.class);
            testsToRun.add(DynamicTest.dynamicTest(serviceRequest.getTitle(), () -> testService(test, serviceRequest)));
        }

        return testsToRun;
    }

    private void testService(String fileToCheck, TestData testData) throws Exception {
        //Get mock json file if is necessary and load the data
        URL mockUri = ItTests.class.getResource(String.format("/mock/%s", fileToCheck));
        MockData mockData = null;
        if (mockUri != null) {
            mockData = om.readValue(new File(mockUri.getFile()), MockData.class);
        }

        //Load the response json file data
        ResponseData responseData = om.readValue(new File(ItTests.class.getResource(String.format("/responses/%s", fileToCheck)).getFile()), ResponseData.class);

        //Call to service with mock and check the status response
        this.callServiceMockAndCheck(testData, mockData, responseData);
    }

    private void callServiceMockAndCheck(TestData testData, MockData mockData, ResponseData responseData) throws Exception {
        //If test need mock load the data and inject mock in the restTemplate call
        if (mockData != null) {
            mockOpenServer = MockRestServiceServer.bindTo(restTemplate).build();
            ResponseCreator response = MockRestResponseCreators.withSuccess(om.writeValueAsString(mockData.getBody()), MediaType.APPLICATION_JSON);
            URI uri = URI.create(mockData.getUri());
            mockOpenServer.expect(MockRestRequestMatchers.requestTo(uri)).andExpect(MockRestRequestMatchers.method(HttpMethod.resolve(mockData.getMethod()))).andRespond(response);
        }

        //Request to the service with test data and check status
        byte[] response = webTestClient.method(HttpMethod.valueOf(testData.getMethod())).uri(testData.getUri()).exchange()
                .expectStatus().isEqualTo(responseData.getStatus())
                .expectBody().returnResult().getResponseBody();

        //If response expected body is compete check it
        if (responseData.getBody() != null) {
            checkResponse(om.writeValueAsString(responseData.getBody()), new String(response, StandardCharsets.UTF_8));
        }
    }

    protected void checkResponse(String expected, String response){
        try {
            JSONAssert.assertEquals(expected, response, JSONCompareMode.STRICT_ORDER);
        } catch (JSONException ex) {
            Assertions.assertFalse(true, "Fail checking response bodys");
        }

    }

    /**
     * Find all json files in the test folder and retuirn filenames
     */
    private List<String> getTestFiles() throws IOException {
        List<String> tests = new ArrayList();
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] testsJsons = resolver.getResources("classpath*:/tests/*.json");

        for(int iterator = 0; iterator < testsJsons.length; iterator++) {
            tests.add(testsJsons[iterator].getFilename());
        }

        return tests;
    }
}
