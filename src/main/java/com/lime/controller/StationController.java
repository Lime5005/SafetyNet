package com.lime.controller;

import com.lime.domain.Station;
import com.lime.repository.StationRepository;
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

    @Autowired
    public StationController(SafetyNetService safetyNetService) {
        this.safetyNetService = safetyNetService;
    }

    @ResponseBody
    @RequestMapping("/firestation")
    public List<Station> getStations() {
        return safetyNetService.getAllStations();
    }

    @ResponseBody
    @RequestMapping(path="/phoneAlert", method = RequestMethod.GET)
    public LinkedHashSet<String> getPhones(@RequestParam int firestation) {
        return safetyNetService.getAllPhonesByStation(firestation);
    }
}
