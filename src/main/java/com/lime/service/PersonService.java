package com.lime.service;

import com.lime.domain.Person;
import com.lime.domain.Station;
import com.lime.repository.PersonRepository;
import com.lime.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class PersonService {

    PersonRepository personRepository;
    StationRepository stationRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, StationRepository stationRepository) {
        this.personRepository = personRepository;
        this.stationRepository = stationRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.getAllPersons();
    }

    public List<Station> getAllStations() {
        return stationRepository.getAllStations();
    }

    /**
     * Get a list of email for all the person living in the same city.
     * @param city the city name.
     * @return a list of email.
     */
    public LinkedHashSet<String> getAllEmailsByCity(String city) {
        LinkedHashSet<String> list = new LinkedHashSet<>();
        for (Person person : this.getAllPersons()) {
            if (person.getCity().equals(city)) {
                list.add(person.getEmail());
            }
        }
        if (list.isEmpty()) return null;
        return list;
    }

    /**
     * Get all phone numbers of person living nearby the fire station.
     * Each station may have multiple addresses, find all the phones of person living in the addresses.
     * @param station station number.
     * @return a list of phone numbers.
     */
    public LinkedHashSet<String> getAllPhonesByStation(int station) {
        LinkedHashSet<String> phones = new LinkedHashSet<>();
        List<String> addresses = new ArrayList<>();
        List<Station> allStations = this.getAllStations();
        List<Person> allPersons = this.getAllPersons();

        for (Station aStation : allStations) {
                if (aStation.getStation() == station) {
                    addresses.add(aStation.getAddress());
                }
        }
        if (addresses.isEmpty()) return null;

        for (String address : addresses) {
            for (Person person : allPersons) {
                if (person.getAddress().equals(address)) {
                    phones.add(person.getPhone());
                }
            }
        }

        return phones;
    }

    public Boolean createPerson(Person person) {
        return personRepository.savePerson(person);
    }

    public Boolean updatePerson(Person person) {
        return personRepository.updatePerson(person);
    }

    public Boolean deletePerson(Person person) {
        return personRepository.deletePerson(person);
    }
}
