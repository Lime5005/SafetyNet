package com.lime.controller;

import com.lime.domain.Person;
import com.lime.domain.Station;
import com.lime.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashSet;
import java.util.List;

@Controller
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ResponseBody
    @RequestMapping("/firestations")
    public List<Station> getStations() {
        return personService.getAllStations();
    }

    @ResponseBody
    @RequestMapping(path="/phoneAlert", method = RequestMethod.GET)
    public LinkedHashSet<String> getPhones(@RequestParam int firestation) {
        return personService.getAllPhonesByStation(firestation);
    }

    @ResponseBody
    @RequestMapping("/persons")
    public List<Person> getPersons() {
         return personService.getAllPersons();
    }

    @ResponseBody
    @RequestMapping(path="/communityEmail", method = RequestMethod.GET)
    public LinkedHashSet<String> getAllEmailsByCity(@RequestParam String city) {
        return personService.getAllEmailsByCity(city);
    }

}
