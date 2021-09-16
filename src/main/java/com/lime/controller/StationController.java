package com.lime.controller;

import com.lime.controller.dto.*;
import com.lime.domain.Station;
import com.lime.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class StationController {

    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @RequestMapping(path="/flood/stations", method = RequestMethod.GET)
    public ResponseEntity<Object> getPersonsByStation(@RequestParam List<Integer> stations) {
        Map<String, List<PersonWithRecord>> listMap = stationService.findAllPersonByStation(stations);
        if (listMap != null) {
            return new ResponseEntity<>(listMap, HttpStatus.OK);
        } else return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path="/fire", method = RequestMethod.GET)
    public ResponseEntity<Object> getStationsByAddress(@RequestParam String address) {
        FireAddressDto addressDto = stationService.findStationsByAddress(address);
        if (stationService.findStationsByAddress(address) != null) {
            return new ResponseEntity<>(addressDto, HttpStatus.OK);
        } else return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/firestation", method = RequestMethod.GET)
    public ResponseEntity<Object> getPersonsByStationNumber(@RequestParam int stationNumber) {
        FireCoverDto persons = stationService.findPersonsByStationNumber(stationNumber);
        if (persons != null) {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } else return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
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
