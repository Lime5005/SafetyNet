package com.lime.service;

import com.lime.controller.dto.*;
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
    public List<FloodStationDto> findAllPersonByStation(List<Integer> stations) {
        // find why it's only taking the first address in account?
        // Because I returned the result too early in the for loop!
        List<FloodStationDto> list = new ArrayList<>();
        List<Station> stationsByNumber;
        List<String> allAddresses;
        List<String> groupAddress = new ArrayList<>();

        //1, Find all addresses concerned first:
        for (Integer station : stations) {

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

    /**
     * Find all person living in this address and the list of fire stations.
     * @param address the address
     * @return a list of person with medical records and the station numbers.
     */
    public List<FireAddressDto> findStationsByAddress(String address) {
        List<FireAddressDto> list = new ArrayList<>();
        List<Station> stations = stationRepository.getStationsByAddress(address);
        //1, Find all station numbers for this address:
        List<Integer> numbers = new ArrayList<>();
        for (Station station : stations) {
            numbers.add(station.getStation());
        }
        //2, Find all person nearby:
        List<Person> persons = personRepository.findPersonsByAddress(address);
        //3, Find the records:
        for (Person person : persons) {
            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            String phone = person.getPhone();
            Record recordByName = recordRepository.findRecordByName(firstName, lastName);
            int age = recordRepository.getAge(recordByName.getBirthdate());
            List<String> medications = recordByName.getMedications();
            List<String> allergies = recordByName.getAllergies();
            list.add(new FireAddressDto(firstName, lastName, phone, age, medications, allergies, numbers));
        }

        return list;
    }

    /**
     * Find all children living in the address and their family members.
     * @param address
     * @return a list of children or an empty list.
     */
    public List<ChildAlertDto> findChildByAddress(String address) {
        List<ChildAlertDto> list = new ArrayList<>();
        //1, All person living in the address:
        List<Person> persons = personRepository.findPersonsByAddress(address);
        List<Person> family = new ArrayList<>();

        for (Person person : persons) {
            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            // Who are children? Find age by name:
            int age = recordRepository.getAgeByName(firstName, lastName);
            // Find all family members:
            if (person.getLastName().equals(lastName)) {
                family.add(person);
            }
            if (age <= 18) {
                family.remove(person);
                list.add(new ChildAlertDto(firstName, lastName, age, family));
            }
        }

        return list;
    }

    /**
     * Find all person covered by the fire station.
     * @param station a station number
     * @return An object with persons and total number for adults and children respectively.
     */
    public FireCoverDto findPersonsByStationNumber(int station) {
        FireCoverDto fireCoverDto = new FireCoverDto();
        List<APerson> personList = new ArrayList<>();
        int total_adult = 0;
        int total_child = 0;
        //1, Find all addresses of this station number:
        List<Station> stationsByNumber = stationRepository.getStationsByNumber(station);
        List<String> addresses = stationRepository.findAllAddresses(stationsByNumber);
        //2, Find all person living in these addresses:
        for (String address : addresses) {
            List<Person> persons = personRepository.findPersonsByAddress(address);
            for (Person person : persons) {
                //3, Find age by name:
                String firstName = person.getFirstName();
                String lastName = person.getLastName();
                int age = recordRepository.getAgeByName(firstName, lastName);
                if (age <= 18) {
                    total_child += 1;
                } else {
                    total_adult += 1;
                }
                personList.add(new APerson(firstName, lastName, address, person.getPhone()));
            }
        }
        fireCoverDto.setPersonList(personList);
        fireCoverDto.setTotal_adult(total_adult);
        fireCoverDto.setTotal_child(total_child);

        return fireCoverDto;
    }

}


