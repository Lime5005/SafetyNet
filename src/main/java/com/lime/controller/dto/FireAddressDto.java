package com.lime.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
