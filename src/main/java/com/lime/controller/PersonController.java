package com.lime.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.domain.Person;
import com.lime.domain.Record;
import com.lime.service.SafetyNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Controller
public class PersonController {

    SafetyNetService safetyNetService;

    @Autowired
    public PersonController(SafetyNetService safetyNetService) {
        this.safetyNetService = safetyNetService;
    }

    @ResponseBody
    @RequestMapping("/person")
    public List<Person> getPersons() {
         return safetyNetService.getAllPersons();
    }

    @ResponseBody
    @RequestMapping(path="/personInfo", method = RequestMethod.GET)
    public Map<Person, Record> getPersonByName(@RequestParam String firstName, @RequestParam String lastName ) {
        return safetyNetService.getAPersonWithRecord(firstName, lastName);// TODO: change to dto.
    }

    @ResponseBody
    @RequestMapping(path="/communityEmail", method = RequestMethod.GET)
    public List<String> getAllPhonesByCity(@RequestParam String city) {
        return safetyNetService.getAllEmailsByCity(city);
    }


}
