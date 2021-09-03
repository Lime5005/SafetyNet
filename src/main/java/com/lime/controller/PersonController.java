package com.lime.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.controller.dto.ChildAlertDto;
import com.lime.controller.dto.PersonInfoDto;
import com.lime.domain.Person;
import com.lime.domain.Record;
import com.lime.service.DtoService;
import com.lime.service.SafetyNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Controller
public class PersonController {

    SafetyNetService safetyNetService;
    DtoService dtoService;

    @Autowired
    public PersonController(SafetyNetService safetyNetService, DtoService dtoService) {
        this.safetyNetService = safetyNetService;
        this.dtoService = dtoService;
    }

    @ResponseBody
    @RequestMapping("/persons")
    public List<Person> getPersons() {
         return safetyNetService.getAllPersons();
    }

    @ResponseBody
    @RequestMapping(path="/personInfo", method = RequestMethod.GET)
    public PersonInfoDto getPersonByName(@RequestParam String firstName, @RequestParam String lastName ) {
        return dtoService.getAPersonWithRecord(firstName, lastName);
    }

    @ResponseBody
    @RequestMapping(path="/communityEmail", method = RequestMethod.GET)
    public LinkedHashSet<String> getAllPhonesByCity(@RequestParam String city) {
        return safetyNetService.getAllEmailsByCity(city);
    }

    @ResponseBody
    @RequestMapping(path = "/childAlert", method = RequestMethod.GET)
    public ChildAlertDto getChildrenAlert(@RequestParam String address) {
        return dtoService.findChildByAddress(address);
    }
}
