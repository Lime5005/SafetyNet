package com.lime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.domain.Person;
import com.lime.domain.Record;
import com.lime.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SafetyNetAppTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllEmails_givenCity_shouldReturnAList() throws Exception {
        this.mockMvc.perform(get("/communityEmail?city=Culver")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("drk@email.com")))
                .andExpect(jsonPath("$.length()", is(15)));
    }

    @Test
    public void getAllPhones_givenStation_shouldReturnAList() throws Exception {
        this.mockMvc.perform(get("/phoneAlert?firestation=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("841-874-6512")))
                .andExpect(jsonPath("$.length()", is(4)));
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
                .andExpect(jsonPath("$.personList.length()", is(6)))
                .andExpect(jsonPath("$.total_adult").value(5))
                .andExpect(jsonPath("$.total_child").value(1));
    }

    @Test
    public void getPerson_givenName_shouldReturnPersonInfo() throws Exception {
        this.mockMvc.perform(get("/personInfo?firstName=Jamie&lastName=Peters")).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(39))
                .andExpect(jsonPath("$.address", is("908 73rd St")))
                .andExpect(jsonPath("$.medications.length()", is(0)))
                .andExpect(jsonPath("$.allergies.length()", is(0)));

    }

    @Test
    public void getAddressesSorted_givenStations_shouldReturnPersons() throws Exception {
        this.mockMvc.perform(get("/flood/stations?stations=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    public void getChildren_givenAddress_shouldReturnChildAndParents() throws Exception {
        this.mockMvc.perform(get("/childAlert?address=892 Downing Ct")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.children[0].age").value(4))
                .andExpect(jsonPath("$.children.length()", is(1)))
                .andExpect(jsonPath("$.familyMembers.length()", is(2)));
    }

    @Test
    public void createPerson_withAPerson_shouldReturnOk() throws Exception {
        Person person = new Person("Lili", "Rose", "1509 Culver St", "Culver", "97451", "841-874-6512", "lili@email.com");
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deletePerson_withAPerson_shouldReturnOk() throws Exception {
        Person person = new Person("Clive", "Ferguson", "748 Townings Dr", "Culver", "97451", "841-874-6741", "clivfd@ymail.com");
        mockMvc.perform(MockMvcRequestBuilders.delete("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void updatePerson_withAPerson_shouldReturnOk() throws Exception {
        Person person = new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", "gramps@email.com");
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void addStation_withAStation_shouldReturnOk() throws Exception {
        Station station = new Station("29 15th St", 5);
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .post("/firestations")
                        .content(objectMapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
//        System.out.println(response.getContentAsString()); //false
    }

    @Test
    public void updateStationNum_withStation_shouldReturnOk() throws Exception {
        Station station = new Station("489 Manchester St", 8);
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/firestations")
                        .content(objectMapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deleteStation_withStation_shouldReturnOk() throws Exception {
        Station station = new Station("951 LoneTree Rd", 2);
        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/firestations")
                        .content(objectMapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createRecord_withARecord_shouldReturnOk() throws Exception {
        List<String> medications = new ArrayList<>();
        medications.add("\"aznol:350mg\"");
        medications.add("hydrapermazol:100mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        Record record = new Record( "SSS", "DDD", new SimpleDateFormat( "yyyyMMdd" ).parse( "19840306" ), medications, allergies);
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .post("/medicalRecords")
                        .content(objectMapper.writeValueAsString(record))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

    @Test
    public void updateRecord_withARecord_shouldReturnOk() throws Exception {
        List<String> medications = new ArrayList<>();
        medications.add("\"aznol:350mg\"");
        medications.add("hydrapermazol:100mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("");
        Record record = new Record( "John", "Boyd", new SimpleDateFormat( "yyyyMMdd" ).parse( "19840306" ), medications, allergies);
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .put("/medicalRecords")
                        .content(objectMapper.writeValueAsString(record))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

    @Test
    public void deleteRecord_withARecord_shouldReturnOk() throws Exception {
        List<String> medications = new ArrayList<>();
        medications.add("\"aznol:350mg\"");
        medications.add("hydrapermazol:100mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("");
        Record record = new Record( "Eric", "Cadigan", new SimpleDateFormat( "yyyyMMdd" ).parse( "19840306" ), medications, allergies);
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .delete("/medicalRecords")
                        .content(objectMapper.writeValueAsString(record))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

}
