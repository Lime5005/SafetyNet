package com.lime.controller;

import com.lime.controller.dto.FireAddressDto;
import com.lime.controller.dto.FireCoverDto;
import com.lime.controller.dto.FloodStationDto;
import com.lime.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StationController {

    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @ResponseBody
    @RequestMapping(path="/flood/stations", method = RequestMethod.GET)
    public List<FloodStationDto> getPersonsByStation(@RequestParam List<Integer> stations) {
        return stationService.findAllPersonByStation(stations);
    }

    @ResponseBody
    @RequestMapping(path="/fire", method = RequestMethod.GET)
    public FireAddressDto getStationsByAddress(@RequestParam String address) {
        return stationService.findStationsByAddress(address);
    }

    @ResponseBody
    @RequestMapping(path = "/firestation", method = RequestMethod.GET)
    public FireCoverDto getPersonsByStationNumber(@RequestParam int stationNumber) {
        return stationService.findPersonsByStationNumber(stationNumber);
    }
}
