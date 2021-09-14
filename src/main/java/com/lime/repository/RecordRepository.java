package com.lime.repository;

import com.lime.domain.Database;
import com.lime.domain.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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

    // Add a new record:
    public Boolean saveRecord(Record record) {
        if (record.getFirstName() != null && record.getLastName() != null) {
            for (Record aRecord : this.getAllRecords()) {
                if (record.getFirstName().equals(aRecord.getFirstName())
                && record.getLastName().equals(aRecord.getLastName())) {
                    return false;
                } else {
                    return this.getAllRecords().add(record);
                }
            }
        }
        return false;
    }

    public Boolean updateRecord(Record record) {
        if (record.getFirstName() != null && record.getLastName() != null) {
            for (Record medicalrecord : this.getAllRecords()) {
                if (medicalrecord.getFirstName().equals(record.getFirstName())
                        && medicalrecord.getLastName().equals(record.getLastName())) {
                    medicalrecord.setBirthdate(record.getBirthdate());
                    medicalrecord.setMedications(record.getMedications());
                    medicalrecord.setAllergies(record.getAllergies());
                    return this.getAllRecords().add(medicalrecord);
                }
            }
        }
        return false;
    }

    public Boolean deleteRecord(Record record) {
        if (record.getFirstName() != null && record.getLastName() != null) {
            for (Record medicalrecord : this.getAllRecords()) {
                if (medicalrecord.getFirstName().equals(record.getFirstName())
                        && medicalrecord.getLastName().equals(record.getLastName())) {
                    return this.getAllRecords().remove(medicalrecord);
                }
            }
        }
        return false;
    }
}
