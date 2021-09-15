package com.lime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.domain.Station;
import com.lime.service.StationService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StationCrudTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StationService stationService;

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
        Mockito.verify(stationService, Mockito.times(1)).addStation(ArgumentMatchers.any(Station.class));
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
        Mockito.verify(stationService, Mockito.times(1)).updateStation(ArgumentMatchers.any(Station.class));
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
        Mockito.verify(stationService, Mockito.times(1)).deleteStation(ArgumentMatchers.any(Station.class));
    }

}
