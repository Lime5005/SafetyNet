package com.lime.controller;

import com.lime.controller.dto.*;
import com.lime.domain.Station;
import com.lime.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class StationController {

    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @RequestMapping(path="/flood/stations", method = RequestMethod.GET)
    public Map<String, List<PersonWithRecord>> getPersonsByStation(@RequestParam List<Integer> stations) {
        return stationService.findAllPersonByStation(stations);
    }

    @RequestMapping(path="/fire", method = RequestMethod.GET)
    public FireAddressDto getStationsByAddress(@RequestParam String address) {
        return stationService.findStationsByAddress(address);
    }

    @RequestMapping(path = "/firestation", method = RequestMethod.GET)
    public FireCoverDto getPersonsByStationNumber(@RequestParam int stationNumber) {
        return stationService.findPersonsByStationNumber(stationNumber);
    }

    @GetMapping(path = "/firestations")
    public List<Station> getAllStations() {
        return stationService.getAllStations();
    }
    
    @PostMapping(path = "/firestations")
    public Boolean addStation(@RequestBody Station station) {
        return stationService.addStation(station);
    }

    @PutMapping(path = "/firestations")
    public Boolean updateStation(@RequestBody Station station) {
        return stationService.updateStation(station);
    }

    @DeleteMapping(path = "/firestations")
    public Boolean deleteStation(@RequestBody Station station) {
        return stationService.deleteStation(station);
    }
}
