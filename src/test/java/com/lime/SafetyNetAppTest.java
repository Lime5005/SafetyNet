package com.lime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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
public class SafetyNetAppTest {
    @Autowired
    private MockMvc mockMvc;

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

    @Test
    public void createPerson_withPerson_shouldReturnBoolean() throws Exception {
        Person person = new Person("Lili", "Rose", "1509 Culver St", "Culver", "97451", "841-874-6512", "lili@email.com");
        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/persons")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //andExpect(status().isCreated())?
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updatePerson_withWrongPerson_shouldReturnFalse() throws Exception {
        Person person = new Person("AAA", "BBB", "1000 Culver St", "Culver", "97451", "111-999-0000", "boyd@email.com");
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/persons")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void deletePerson_withPerson_shouldReturnTrue() throws Exception {
        Person person = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/persons")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void deletePerson_withWrongPerson_shouldReturnfalse() throws Exception {
        Person person = new Person("PPP", "LLL", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/persons")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }





    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
