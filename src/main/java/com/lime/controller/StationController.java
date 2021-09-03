package com.lime.controller;

import com.lime.controller.dto.FireAddressDto;
import com.lime.controller.dto.FireCoverDto;
import com.lime.controller.dto.FloodStationDto;
import com.lime.domain.Station;
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
    @RequestMapping("/firestations")
    public List<Station> getStations() {
        return safetyNetService.getAllStations();
    }

    @ResponseBody
    @RequestMapping(path="/phoneAlert", method = RequestMethod.GET)
    public LinkedHashSet<String> getPhones(@RequestParam int firestation) {
        return safetyNetService.getAllPhonesByStation(firestation);
    }

    @ResponseBody
    @RequestMapping(path="/flood/stations", method = RequestMethod.GET)
    public List<FloodStationDto> getPersonsByStation(@RequestParam List<Integer> stations) {
        return dtoService.findAllPersonByStation(stations);
    }

    @ResponseBody
    @RequestMapping(path="/fire", method = RequestMethod.GET)
    public FireAddressDto getStationsByAddress(@RequestParam String address) {
        return dtoService.findStationsByAddress(address);
    }

    @ResponseBody
    @RequestMapping(path = "/firestation", method = RequestMethod.GET)
    public FireCoverDto getPersonsByStationNumber(@RequestParam int stationNumber) {
        return dtoService.findPersonsByStationNumber(stationNumber);
    }
}
