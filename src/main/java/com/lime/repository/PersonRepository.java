package com.lime.repository;

import com.lime.domain.Database;
import com.lime.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    Database database;

    @Autowired
    public PersonRepository(Database database) {
        this.database = database;
    }

    public List<Person> getAllPersons() {
        return database.getPersons();
    }

    public Person findPersonByName(String firstName, String lastName) {
        List<Person> persons = this.getAllPersons();
        for (Person person : persons) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                return person;
            }
        }
        return null;
    }

    public List<Person> findPersonsByAddress(String address) {
        List<Person> persons = this.getAllPersons();
        List<Person> list = new ArrayList<>();
        for (Person person : persons) {
            if (person.getAddress().equals(address)) {
                list.add(person);
            }
        }
        return list;
    }

}
