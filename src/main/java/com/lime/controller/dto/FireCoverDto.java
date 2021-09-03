package com.lime.controller.dto;

import java.util.List;

public class FireCoverDto {
    private List<APerson> personList;
    private int total_adult;
    private int total_child;

    public FireCoverDto() {
    }

    public FireCoverDto(List<APerson> personList, int total_adult, int total_child) {
        this.personList = personList;
        this.total_adult = total_adult;
        this.total_child = total_child;
    }

    public List<APerson> getPersonList() {
        return personList;
    }

    public void setPersonList(List<APerson> personList) {
        this.personList = personList;
    }

    public int getTotal_adult() {
        return total_adult;
    }

    public void setTotal_adult(int total_adult) {
        this.total_adult = total_adult;
    }

    public int getTotal_child() {
        return total_child;
    }

    public void setTotal_child(int total_child) {
        this.total_child = total_child;
    }
}
