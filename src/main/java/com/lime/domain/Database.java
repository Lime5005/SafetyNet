package com.lime.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class Database {
    List<Person> persons = new ArrayList<>();
    List<Station> firestations = new ArrayList<>();
    List<Record> medicalrecords = new ArrayList<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        try {
            var allInfo = mapper.readValue(new URL("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json"), Database.class);
            this.persons = allInfo.getPersons();
            this.firestations = allInfo.getFirestations();
            this.medicalrecords = allInfo.getMedicalrecords();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Record> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<Record> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    public List<Station> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Station> firestations) {
        this.firestations = firestations;
    }
}
