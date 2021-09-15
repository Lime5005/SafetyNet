package com.lime.controller;

import com.lime.domain.Person;
import com.lime.domain.Station;
import com.lime.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;

// @RestController = @Controller + @ResponseBody(return data to browser, or json if object)
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(path="/phoneAlert", method = RequestMethod.GET)
    public LinkedHashSet<String> getPhones(@RequestParam int firestation) {
        return personService.getAllPhonesByStation(firestation);
    }

    @RequestMapping(path="/communityEmail", method = RequestMethod.GET)
    public LinkedHashSet<String> getAllEmailsByCity(@RequestParam String city) {
        return personService.getAllEmailsByCity(city);
    }

    @GetMapping("/persons")
    public List<Person> getPersons() {
         return personService.getAllPersons();
    }


    @PostMapping(path = "/persons")
    public Boolean createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping(path="/persons")
    public Boolean updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping(path = "/persons")
    public Boolean deletePerson(@RequestBody Person person) {
        return personService.deletePerson(person);
    }

}
