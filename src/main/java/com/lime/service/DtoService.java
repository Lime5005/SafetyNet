package com.lime.service;

import com.lime.controller.dto.FloodStationDto;
import com.lime.controller.dto.PersonInfoDto;
import com.lime.domain.Person;
import com.lime.domain.Record;
import com.lime.domain.Station;
import com.lime.repository.PersonRepository;
import com.lime.repository.RecordRepository;
import com.lime.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DtoService {

    PersonRepository personRepository;
    RecordRepository recordRepository;
    StationRepository stationRepository;

    @Autowired
    public DtoService(PersonRepository personRepository, RecordRepository recordRepository, StationRepository stationRepository) {
        this.personRepository = personRepository;
        this.recordRepository = recordRepository;
        this.stationRepository = stationRepository;
    }

    /**
     * Find a person with his medical records by name.
     * @param firstName firstname
     * @param lastName lastname
     * @return Return firstName, lastName, address, age, email, and medical records.
     */
    public PersonInfoDto getAPersonWithRecord(String firstName, String lastName) {
        PersonInfoDto personInfoDto = new PersonInfoDto();

        Person personByName = personRepository.findPersonByName(firstName, lastName);
        Record recordByName = recordRepository.findRecordByName(firstName, lastName);

        personInfoDto.setFirstName(firstName);
        personInfoDto.setLastName(lastName);
        personInfoDto.setAddress(personByName.getAddress());
        int age = recordRepository.getAge(recordByName.getBirthdate());
        personInfoDto.setAge(age);
        personInfoDto.setEmail(personByName.getEmail());
        personInfoDto.setMedications(recordByName.getMedications());
        personInfoDto.setAllergies(recordByName.getAllergies());
        return personInfoDto;
    }

    /**
     * Find all the person living nearby the fire stations.
     * @param stations station numbers in list.
     * @return a list of person with medical records.
     */
    public List<FloodStationDto> findAllPersonByStation(List<String> stations) {
        // Todo: find why it's only taking the first address in count?
        List<FloodStationDto> list = new ArrayList<>();
        List<Station> stationsByNumber;
        List<String> allAddresses;
        List<String> groupAddress = new ArrayList<>();

        //1, Find all addresses concerned first:
        for (String station : stations) {

            stationsByNumber = stationRepository.getStationsByNumber(station);

            allAddresses = stationRepository.findAllAddresses(stationsByNumber);
            groupAddress.addAll(allAddresses);
        }

        //2, Then from addresses, find all people concerned:
        List<Person> allPersons = personRepository.getAllPersons();
        for (String address : groupAddress) {
            for (Person person : allPersons) {
                if (person.getAddress().equals(address)) {
                    String firstName = person.getFirstName();
                    String lastName = person.getLastName();
                    String phone = person.getPhone();
                    Record recordByName = recordRepository.findRecordByName(firstName, lastName);
                    int age = recordRepository.getAge(recordByName.getBirthdate());
                    List<String> medications = recordByName.getMedications();
                    List<String> allergies = recordByName.getAllergies();
                    list.add(new FloodStationDto(firstName, lastName, phone, address, age, medications, allergies));
                }
            }
        }
        return list;
    }

}


