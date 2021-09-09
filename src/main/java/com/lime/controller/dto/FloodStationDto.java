package com.lime.controller.dto;

public class FloodStationDto {

    private String address;
    private PersonWithRecord personWithRecord;

    public FloodStationDto() {
    }

    public FloodStationDto(String address, PersonWithRecord personWithRecord) {
        this.address = address;
        this.personWithRecord = personWithRecord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PersonWithRecord getPersonWithRecord() {
        return personWithRecord;
    }

    public void setPersonWithRecord(PersonWithRecord personWithRecord) {
        this.personWithRecord = personWithRecord;
    }
}
