package com.lime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.controller.PersonController;
import com.lime.domain.Person;
import com.lime.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc
public class PersonCrudTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @Test
    public void createPerson_withAPerson_shouldReturnTrue() throws Exception {
        Person person = new Person("Lili", "Rose", "1509 Culver St", "Culver", "97451", "841-874-6512", "lili@email.com");
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(personService, Mockito.times(1)).createPerson(ArgumentMatchers.any(Person.class));
    }

    @Test
    public void deletePerson_withNotExistPerson_shouldReturnFalse() throws Exception {
        Person person = new Person("PPP", "LLL", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        mockMvc.perform(MockMvcRequestBuilders.delete("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(personService, Mockito.times(1)).deletePerson(ArgumentMatchers.any(Person.class));
    }

    @Test
    public void deletePerson_withAPerson_shouldReturnTrue() throws Exception {
        Person person = new Person("Clive", "Ferguson", "748 Townings Dr", "Culver", "97451", "841-874-6741", "clivfd@ymail.com");
        mockMvc.perform(MockMvcRequestBuilders.delete("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(personService, Mockito.times(1)).deletePerson(ArgumentMatchers.any(Person.class));
    }

    @Test
    public void updatePerson_withAPerson_shouldReturnTrue() throws Exception {
        Person person = new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", "gramps@email.com");
        MvcResult mvcResult = this.mockMvc.perform( MockMvcRequestBuilders
                        .put("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(personService, Mockito.times(1)).updatePerson(ArgumentMatchers.any(Person.class));
    }

    @Test
    public void updatePerson_withNotExistPerson_shouldReturnFalse() throws Exception {
        Person person = new Person("AAA", "BBB", "1000 Culver St", "Culver", "97451", "111-999-0000", "boyd@email.com");
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(personService, Mockito.times(1)).updatePerson(ArgumentMatchers.any(Person.class));
    }
}
