package com.lime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SafetyNetWebTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllEmails_givenCity_shouldReturnAList() throws Exception {
        this.mockMvc.perform(get("/communityEmail?city=Culver")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("drk@email.com")));
    }

    @Test
    public void getAllPhones_givenStation_shouldReturnAList() throws Exception {
        this.mockMvc.perform(get("/phoneAlert?firestation=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("841-874-6512")));
    }

    @Test
    public void getPersonsAndStations_givenAddress_shouldReturnAnObj() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        this.mockMvc.perform(get("/fire?address=112 Steppes Pl")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.stationNumbers").value(list));
    }

    @Test
    public void getPersons_givenStation_shouldReturnPersons() throws Exception {
        this.mockMvc.perform(get("/firestation?stationNumber=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.total_adult").value(5))
                .andExpect(jsonPath("$.total_child").value(1));
    }

    @Test
    public void getPerson_givenName_shouldReturnPersonInfo() throws Exception {
        this.mockMvc.perform(get("/personInfo?firstName=Jamie&lastName=Peters")).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(39))
                .andExpect(jsonPath("$.address", is("908 73rd St")));
    }

    @Test
    public void getChildren_givenAddress_shouldReturnChildAndParents() throws Exception {
        this.mockMvc.perform(get("/childAlert?address=1509 Culver St")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.children[0].age").value(9))
                .andExpect(jsonPath("$.children[1].age").value(4));
    }

    @Test
    public void getAddressesSorted_givenStations_shouldReturnPersons() throws Exception {
        this.mockMvc.perform(get("/flood/stations?stations=1,2")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(6)));
    }

    @Test
    public void getAllPersons() throws Exception {
        this.mockMvc.perform(get("/persons")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(23)));
    }

    @Test
    public void getAllStations() throws Exception {
        this.mockMvc.perform(get("/firestations")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(13)));
    }

    @Test
    public void getAllRecords() throws Exception {
        this.mockMvc.perform(get("/medicalRecords")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(23)));
    }

}
