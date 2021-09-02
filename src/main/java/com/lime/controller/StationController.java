package com.lime.controller;

import com.lime.controller.dto.FloodStationDto;
import com.lime.domain.Station;
import com.lime.repository.StationRepository;
import com.lime.service.DtoService;
import com.lime.service.SafetyNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Controller
public class StationController {

    SafetyNetService safetyNetService;
    DtoService dtoService;

    @Autowired
    public StationController(SafetyNetService safetyNetService, DtoService dtoService) {
        this.safetyNetService = safetyNetService;
        this.dtoService = dtoService;
    }

    @ResponseBody
    @RequestMapping("/firestation")
    public List<Station> getStations() {
        return safetyNetService.getAllStations();
    }

    @ResponseBody
    @RequestMapping(path="/phoneAlert", method = RequestMethod.GET)
    public LinkedHashSet<String> getPhones(@RequestParam String firestation) {
        return safetyNetService.getAllPhonesByStation(firestation);
    }

    @ResponseBody
    @RequestMapping(path="/flood/stations", method = RequestMethod.GET)
    public List<FloodStationDto> getPersonsByStation(@RequestParam List<String> stations) {
        return dtoService.findAllPersonByStation(stations);
    }
}
