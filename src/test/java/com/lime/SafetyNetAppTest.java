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

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        mockMvc.perform(get("/communityEmail?city=Culver")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("drk@email.com")))
                .andExpect(jsonPath("$.length()", is(15)));
    }

    @Test
    public void getAllPhones_givenStation_shouldReturnAList() throws Exception {
        mockMvc.perform(get("/phoneAlert?firestation=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("841-874-6512")))
                .andExpect(jsonPath("$.length()", is(4)));
    }

    @Test
    public void getAllPhones_givenNotExistStation_shouldReturnAnEmptyList() throws Exception {
        mockMvc.perform(get("/phoneAlert?firestation=100")).andDo(print())
                .andExpect(jsonPath("$.length()", is(0)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getPersonsAndStations_givenAddress_shouldReturnAnObj() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        mockMvc.perform(get("/fire?address=112 Steppes Pl")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.stationNumbers").value(list));
    }

    @Test
    public void getPersonsAndStations_givenNotExistAddress_shouldReturnAnEmptyJson() throws Exception {
        mockMvc.perform(get("/fire?address=30 rue du province")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    public void getPersons_givenStation_shouldReturnPersons() throws Exception {
        mockMvc.perform(get("/firestation?stationNumber=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.personList.length()", is(6)))
                .andExpect(jsonPath("$.total_adult").value(5))
                .andExpect(jsonPath("$.total_child").value(1));
    }

    @Test
    public void getPerson_givenName_shouldReturnPersonInfo() throws Exception {
        mockMvc.perform(get("/personInfo?firstName=Jamie&lastName=Peters")).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(39))
                .andExpect(jsonPath("$.address", is("908 73rd St")))
                .andExpect(jsonPath("$.medications.length()", is(0)))
                .andExpect(jsonPath("$.allergies.length()", is(0)));

    }

    @Test
    public void getPerson_givenNotExistName_shouldReturnAnEmptyJson() throws Exception {
        mockMvc.perform(get("/personInfo?firstName=Jamie&lastName=Pet")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(0)));
    }


    @Test
    public void getAddressesSorted_givenStation_shouldReturnPersons() throws Exception {
        mockMvc.perform(get("/flood/stations?stations=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    public void getAddressesSorted_givenNotExistStation_shouldAnEmptyList() throws Exception {
        mockMvc.perform(get("/flood/stations?stations=800")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    public void getChildren_givenAddress_shouldReturnChildAndParents() throws Exception {
        mockMvc.perform(get("/childAlert?address=892 Downing Ct")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.children[0].age").value(4))
                .andExpect(jsonPath("$.children.length()", is(1)))
                .andExpect(jsonPath("$.familyMembers.length()", is(2)));
    }

    @Test
    public void getChildren_givenNotExistAddress_shouldReturnAnEmptyJson() throws Exception {
        mockMvc.perform(get("/childAlert?address=99 avenue Paris")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    //Below are CRUD tests for endpoints:
    //Crud for /persons
    @Test
    public void createPerson_withAPerson_shouldReturnTrue() throws Exception {
        Person person = new Person("Lili", "Rose", "1509 Culver St", "Culver", "97451", "841-874-6512", "lili@email.com");
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .post("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();
        assertEquals("true", response.getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    public void deletePerson_withAPerson_shouldReturnTrue() throws Exception {
        Person person = new Person("Clive", "Ferguson", "748 Townings Dr", "Culver", "97451", "841-874-6741", "clivfd@ymail.com");
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse();
        assertEquals("true", response.getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    public void updatePerson_withAPerson_shouldReturnTrue() throws Exception {
        Person person = new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", "gramps@email.com");
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .put("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse();
        assertEquals("true", response.getContentAsString(StandardCharsets.UTF_8));
    }

    //Crud for /firestations
    @Test
    public void addStation_withAStation_shouldReturnTrue() throws Exception {
        Station station = new Station("29 15th St", 9);
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .post("/firestations")
                        .content(objectMapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();
        assertEquals("true", response.getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    public void updateStationNum_withStation_shouldReturnTrue() throws Exception {
        Station station = new Station("489 Manchester St", 8);
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .put("/firestations")
                        .content(objectMapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse();
        assertEquals("true", response.getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    public void deleteStation_withStation_shouldReturnTrue() throws Exception {
        Station station = new Station("951 LoneTree Rd", 2);
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .delete("/firestations")
                        .content(objectMapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse();
        assertEquals("true", response.getContentAsString(StandardCharsets.UTF_8));
    }

    //Crud for /medicalRecords
    @Test
    public void createRecord_withARecord_shouldReturnTrue() throws Exception {
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
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();
        assertEquals("true", response.getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    public void updateRecord_withARecord_shouldReturnTrue() throws Exception {
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
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse();
        assertEquals("true", response.getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    public void deleteRecord_withARecord_shouldReturnTrue() throws Exception {
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
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse();
        assertEquals("true", response.getContentAsString(StandardCharsets.UTF_8));
    }

    // Tests for wrong input, should log error
    @Test
    public void createPerson_withExistPerson_shouldReturnFalse() throws Exception {
        Person person = new Person("John", "Boyd", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "ssanw@email.com");
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .post("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        assertEquals("false", response.getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    public void deleteRecord_withNotExistRecord_shouldReturnFalse() throws Exception {
        List<String> medications = new ArrayList<>();
        medications.add("\"aznol:350mg\"");
        medications.add("hydrapermazol:100mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("");
        Record record = new Record( "III", "LLL", new SimpleDateFormat( "yyyyMMdd" ).parse( "19840306" ), medications, allergies);
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .delete("/medicalRecords")
                        .content(objectMapper.writeValueAsString(record))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        assertEquals("false", response.getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    public void updateStationNum_withNotExistStation_shouldReturnFalse() throws Exception {
        Station station = new Station("888 Manchester St", 100);
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .put("/firestations")
                        .content(objectMapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        assertEquals("false", response.getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    public void deleteStation_withNotExistStation_shouldReturnFalse() throws Exception {
        Station station = new Station("000 LoneTree", 2);
        MockHttpServletResponse response = mockMvc.perform( MockMvcRequestBuilders
                        .delete("/firestations")
                        .content(objectMapper.writeValueAsString(station))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        assertEquals("false", response.getContentAsString(StandardCharsets.UTF_8));
    }
}
