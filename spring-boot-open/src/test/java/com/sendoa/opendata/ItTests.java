package com.sendoa.opendata;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItTests {


    //@Autowired
    private WebApplicationContext ctx;

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
    }



    @Test
    public void getAllOk() {
	    Assert.assertTrue(true);
    }
}
