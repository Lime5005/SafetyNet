package com.lime.service;

import com.lime.controller.dto.AChild;
import com.lime.controller.dto.ChildAlertDto;
import com.lime.controller.dto.PersonInfoDto;
import com.lime.domain.Person;
import com.lime.domain.Record;
import com.lime.repository.PersonRepository;
import com.lime.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService {
    PersonRepository personRepository;
    RecordRepository recordRepository;

    @Autowired
    public RecordService(PersonRepository personRepository, RecordRepository recordRepository) {
        this.personRepository = personRepository;
        this.recordRepository = recordRepository;
    }
    
    public List<Record> getAllRecords() {
        return recordRepository.getAllRecords();
    }

    /**
     * Find a person with his medical records by name.
     * @param firstName firstname.
     * @param lastName lastname.
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
     * Find all children living in the address and their family members.
     * @param address the address.
     * @return a list of children or an empty list.
     */
    public ChildAlertDto findChildByAddress(String address) {
        ChildAlertDto childAlertDto = new ChildAlertDto();
        List<AChild> list = new ArrayList<>();
        //1, All person living in the address:
        List<Person> persons = personRepository.findPersonsByAddress(address);
        List<Person> family = new ArrayList<>();

        for (Person person : persons) {
            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            // Who are children? Find age by name:
            int age = recordRepository.getAgeByName(firstName, lastName);
            if (age <= 18) {
                list.add(new AChild(firstName, lastName, age));
            }
            // Find all family members:
            if (person.getLastName().equals(lastName) && age > 18) {
                family.add(person);
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        childAlertDto.setChildren(list);
        childAlertDto.setFamilyMembers(family);
        return childAlertDto;
    }

    public Boolean addRecord(Record record) {
        return recordRepository.saveRecord(record);
    }

    public Boolean updateRecord(Record record) {
        return recordRepository.updateRecord(record);
    }

    public Boolean deleteRecord(Record record) {
        return recordRepository.deleteRecord(record);
    }
}
