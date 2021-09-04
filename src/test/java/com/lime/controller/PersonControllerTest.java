package com.lime.controller;

import com.lime.domain.Database;
import com.lime.service.StationService;
import com.lime.service.PersonService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class PersonControllerTest {
    private RecordController recordController;
    private PersonController personController;
    private StationService stationService;
    private Database database;
    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public void setUp() {
        database = new Database();
    }

    @MockBean
    PersonService personService;

    @BeforeEach
    public void init() {
        database.init();
    }

    @Test
    public void getAllEmails_withCityName_shouldReturnAList() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[\"jaboyd@email.com\",\"drk@email.com\",\"tenz@email.com\",\"tcoop@ymail.com\",\"lily@email.com\",\"soph@email.com\",\"ward@email.com\",\"zarc@email.com\",\"reg@email.com\",\"jpeter@email.com\",\"aly@imail.com\",\"bstel@email.com\",\"ssanw@email.com\",\"clivfd@ymail.com\",\"gramps@email.com\"]"));
    }


}
