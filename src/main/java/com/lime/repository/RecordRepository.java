package com.lime.repository;

import com.lime.domain.Database;
import com.lime.domain.Person;
import com.lime.domain.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.stream.util.XMLEventConsumer;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class RecordRepository {
    Database database;

    @Autowired
    public RecordRepository(Database database) {
        this.database = database;
    }

    public List<Record> getAllRecords() {
        return database.getMedicalrecords();
    }

    public Record findRecordByName(String firstName, String lastName) {
        for (Record medicalrecord : this.getAllRecords()) {
            if (medicalrecord.getFirstName().equals(firstName) && medicalrecord.getLastName().equals(lastName)) {
                return medicalrecord;
            }
        }
        return null;
    }

    // From name to age:
    public int getAgeByName(String firstName, String lastName) {
        Record recordByName = this.findRecordByName(firstName, lastName);
        Date birthdate = recordByName.getBirthdate();
        return this.getAge(birthdate);
    }

    public int getAge(Date birthDate) {
        LocalDate birthday = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Period.between(birthday, localDate).getYears();
    }

    public List<Record> findAllChildren(Date birthDate) {
        List<Record> list = new ArrayList<>();
        for (Record medicalrecord : this.getAllRecords()) {
            Date birthdate = medicalrecord.getBirthdate();
            if (this.getAge(birthDate) <= 18) {
                list.add(medicalrecord);
            }
        }
        return list;
    }

}
