package com.lime.controller;

import com.lime.controller.dto.EmptyJsonResponse;
import com.lime.domain.Person;
import com.lime.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(path="/phoneAlert", method = RequestMethod.GET)
    public ResponseEntity<Object> getPhones(@RequestParam int firestation) {
        LinkedHashSet<String> phones = personService.getAllPhonesByStation(firestation);
        if (phones != null) {
            logger.info("Fire station " + firestation + " is queried to get phones.");
            return new ResponseEntity<>(phones, HttpStatus.OK);
        } else {
            logger.error("Failed to get phones by fire station: " + firestation);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path="/communityEmail", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllEmailsByCity(@RequestParam String city) {
        LinkedHashSet<String> emails = personService.getAllEmailsByCity(city);
        if (emails != null) {
            logger.info("City " + city + " is queried to get all emails.");
            return new ResponseEntity<>(emails, HttpStatus.OK);
        } else {
            logger.error("Failed to get all emails by city: " + city);
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/persons")
    public List<Person> getPersons() {
//        List<Person> persons = personService.getAllPersons();
//        if (!persons.isEmpty()) {
//            logger.info("Query to get all persons: " + persons);//log all data??
//        }
        return personService.getAllPersons();
    }

    @PostMapping(path = "/persons")
    public ResponseEntity<Boolean> createPerson(@RequestBody Person person) {
        Boolean bool = personService.createPerson(person);
        if (bool) {
            logger.info("Person " + person + " is created!");
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }
        logger.error("Failed to create Person: " + person);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path="/persons")
    public ResponseEntity<Boolean> updatePerson(@RequestBody Person person) {
        Boolean bool = personService.updatePerson(person);
        if (bool) {
            logger.info("Person " + person + " is updated!");
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }
        logger.error("Failed to update Person: " + person);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/persons")
    public ResponseEntity<Boolean> deletePerson(@RequestBody Person person) {
        Boolean bool = personService.deletePerson(person);
        if (bool) {
            logger.info("Person " + person + " is deleted!");
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            logger.error("Failed to delete Person: " + person);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

}
