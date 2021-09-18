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

    /**
     * If a person does not exist yet in the db, by checking the unique firstName and lastName, then add it.
     * @param person A Person Object.
     * @return true if success, or false if failed.
     */
    public Boolean savePerson(Person person) {
        boolean found = false;
        if (person.getFirstName() == null || person.getLastName() == null) {
            return false;
        }
        for (Person aPerson : this.getAllPersons()) {
            if (aPerson.getFirstName().equals(person.getFirstName())
                    && aPerson.getLastName().equals(person.getLastName())) {
                found = true;
                break;
            }
        }
        if (!found) {
            this.getAllPersons().add(person);
        }
        return !found;
    }

    /**
     * Use person's firstName and lastName to identify, if person exists, update it.
     * @param person A Person object.
     * @return true if success, or false if failed.
     */
    public Boolean updatePerson(Person person) {
        if (person.getFirstName() != null && person.getLastName() != null) {
            for (Person aPerson : this.getAllPersons()) {
                if (person.getFirstName().equals(aPerson.getFirstName())
                        && person.getLastName().equals(aPerson.getLastName())) {
                    aPerson.setAddress(person.getAddress());
                    aPerson.setCity(person.getCity());
                    aPerson.setZip(person.getZip());
                    aPerson.setPhone(person.getPhone());
                    aPerson.setEmail(person.getEmail());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Use firstName and lastName as identifier to locate a person and delete it.
     * @param person A Person object.
     * @return true if success, or false if failed.
     */
    public Boolean deletePerson(Person person) {
        if (person.getFirstName() != null && person.getLastName() != null) {
            for (Person aPerson : this.getAllPersons()) {
                if (person.getFirstName().equals(aPerson.getFirstName())
                        && person.getLastName().equals(aPerson.getLastName())) {
                    this.getAllPersons().remove(aPerson);
                    return true;
                }
            }
        }
        return false;
    }

}
