package com.lime.controller;

import com.lime.controller.dto.EmptyJsonResponse;
import com.lime.domain.Person;
import com.lime.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> getPhones(@RequestParam int firestation) {
        LinkedHashSet<String> phones = personService.getAllPhonesByStation(firestation);
        if (phones != null) {
            return new ResponseEntity<>(phones, HttpStatus.OK);
        } else return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path="/communityEmail", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllEmailsByCity(@RequestParam String city) {
        LinkedHashSet<String> emails = personService.getAllEmailsByCity(city);
        if (emails != null) {
            return new ResponseEntity<>(emails, HttpStatus.OK);
        } else return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/persons")
    public List<Person> getPersons() {
         return personService.getAllPersons();
    }

    @PostMapping(path = "/persons")
    public ResponseEntity<Boolean> createPerson(@RequestBody Person person) {
        Boolean bool = personService.createPerson(person);
        if (bool) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path="/persons")
    public ResponseEntity<Boolean> updatePerson(@RequestBody Person person) {
        Boolean bool = personService.updatePerson(person);
        if (bool) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/persons")
    public ResponseEntity<Boolean> deletePerson(@RequestBody Person person) {
        Boolean bool = personService.deletePerson(person);
        if (bool) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

}
