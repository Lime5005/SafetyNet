package com.lime.controller.dto;

import java.util.List;

public class FireAddressDto {
    private List<PersonWithRecord> personWithRecordList;
    private List<Integer> stationNumbers;

    public FireAddressDto() {
    }

    public FireAddressDto(List<PersonWithRecord> personWithRecordList, List<Integer> stationNumbers) {
        this.personWithRecordList = personWithRecordList;
        this.stationNumbers = stationNumbers;
    }

    public List<PersonWithRecord> getPersonWithRecordList() {
        return personWithRecordList;
    }

    public void setPersonWithRecordList(List<PersonWithRecord> personWithRecordList) {
        this.personWithRecordList = personWithRecordList;
    }

    public List<Integer> getStationNumbers() {
        return stationNumbers;
    }

    public void setStationNumbers(List<Integer> stationNumbers) {
        this.stationNumbers = stationNumbers;
    }
}
