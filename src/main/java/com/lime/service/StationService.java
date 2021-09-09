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
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
public class StationService {

    PersonRepository personRepository;
    RecordRepository recordRepository;
    StationRepository stationRepository;

    @Autowired
    public StationService(PersonRepository personRepository, RecordRepository recordRepository, StationRepository stationRepository) {
        this.personRepository = personRepository;
        this.recordRepository = recordRepository;
        this.stationRepository = stationRepository;
    }

    /**
     * Find all the person living nearby the fire stations.
     * @param stations station numbers in list.
     * @return a list of person with medical records.
     */
    public Map<String, List<PersonWithRecord>> findAllPersonByStation(List<Integer> stations) {
        Map<String, List<PersonWithRecord>> map;
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
                    list.add(new FloodStationDto(address, new PersonWithRecord(firstName, lastName, phone, age, medications, allergies)));
                }
            }
        }

        map = getMap(list, groupAddress);
        return map;
    }

    private static Map<String, List<PersonWithRecord>> getMap(List<FloodStationDto> list, List<String> addresses) {
        Map<String, List<PersonWithRecord>> dtoMap = new LinkedHashMap<>();

        //1, Use each address as key:
        for (String address : addresses) {
            //2, why all the persons are added for each address?
            // Because the list is not re-newed each time!
            List<PersonWithRecord> persons = new LinkedList<>();

            for (FloodStationDto floodStationDto : list) {
                if (floodStationDto.getAddress().equals(address)) {
                    //如果地址相同，加入到新的list，每次有新地址，都要新建list:
                    persons.add(floodStationDto.getPersonWithRecord());
                }
            }
            dtoMap.put(address, persons);
        }

        // Test "stations=1,2", got 6 addresses and each address has 11 persons.
//        for (FloodStationDto floodStationDto : list) {
//            // 2, if address is the same, then put in map,
//            for (String address : addresses) {
//                if (floodStationDto.getAddress().equals(address)) {
//                    persons.add(floodStationDto.getPersonWithRecord());
//                    dtoMap.put(address, persons);
//                }
//            }
//        }

        return dtoMap;
    }

    /**
     * Find all person living in this address and the list of fire stations.
     * @param address the address.
     * @return a list of person with medical records and the station numbers.
     */
    public FireAddressDto findStationsByAddress(String address) {
        FireAddressDto fireAddressDto = new FireAddressDto();
        List<PersonWithRecord> list = new ArrayList<>();
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
            list.add(new PersonWithRecord(firstName, lastName, phone, age, medications, allergies));
        }
        fireAddressDto.setPersonWithRecordList(list);
        fireAddressDto.setStationNumbers(numbers);
        return fireAddressDto;
    }

    /**
     * Find all person covered by the fire station.
     * @param station a station number.
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

