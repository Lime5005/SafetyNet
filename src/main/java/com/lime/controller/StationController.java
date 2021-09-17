package com.lime.controller;

import com.lime.controller.dto.*;
import com.lime.domain.Station;
import com.lime.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class StationController {

    private final StationService stationService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @RequestMapping(path="/flood/stations", method = RequestMethod.GET)
    public ResponseEntity<Object> getPersonsByStation(@RequestParam List<Integer> stations) {
        Map<String, List<PersonWithRecord>> listMap = stationService.findAllPersonByStation(stations);
        if (listMap != null) {
            logger.info("Stations " + stations + " is queried to get persons.");
            return new ResponseEntity<>(listMap, HttpStatus.OK);
        } else {
            logger.error("Failed to get persons by stations: " + stations);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path="/fire", method = RequestMethod.GET)
    public ResponseEntity<Object> getStationsByAddress(@RequestParam String address) {
        FireAddressDto addressDto = stationService.findStationsByAddress(address);
        if (stationService.findStationsByAddress(address) != null) {
            logger.info("Station address " + address + " is queried to get stations.");
            return new ResponseEntity<>(addressDto, HttpStatus.OK);
        } else {
            logger.error("Failed to get stations by address: " + address);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/firestation", method = RequestMethod.GET)
    public ResponseEntity<Object> getPersonsByStationNumber(@RequestParam int stationNumber) {
        FireCoverDto persons = stationService.findPersonsByStationNumber(stationNumber);
        if (persons != null) {
            logger.info("Station number " + stationNumber + " is queried to get persons.");//Todo: log persons?
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } else {
            logger.error("Failed to get persons by station number: " + stationNumber);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/firestations")
    public List<Station> getAllStations() {
        return stationService.getAllStations();
    }
    
    @PostMapping(path = "/firestations")
    public ResponseEntity<Boolean> addStation(@RequestBody Station station) {
        Boolean bool = stationService.addStation(station);
        if (bool) {
            logger.info("Station " + station + " is created!");
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            logger.error("Station " + station + " failed to be created (is null or it already exists).");
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/firestations")
    public ResponseEntity<Boolean> updateStation(@RequestBody Station station) {
        Boolean bool = stationService.updateStation(station);
        if (bool) {
            logger.info("Station " + station + " is updated!");
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            logger.error("Station " + station + " failed to be updated (is the same data or doesn't exist).");
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/firestations")
    public ResponseEntity<Boolean> deleteStation(@RequestBody Station station) {
        Boolean bool = stationService.deleteStation(station);
        if (bool) {
            logger.info("Station " + station + " is deleted!");
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            logger.error("Station " + station + " failed to be deleted (is null or doesn't exist).");
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
