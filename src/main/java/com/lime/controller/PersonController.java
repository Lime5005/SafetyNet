package com.lime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PersonController {

    private static final ObjectMapper mapper = new ObjectMapper();

    @ResponseBody
    @RequestMapping("/person")
    //public static List<Person> getPersons() {
    public static Object getPersons() {
        //List<Person> list = new ArrayList<>();
        try {
            var allInfo = mapper.readValue(new URL("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json"), Map.class);
            //System.out.println(allInfo);
            Object persons = allInfo.get("persons");
            //System.out.println(persons);
            //Person p = (Person) persons;
            return persons;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
