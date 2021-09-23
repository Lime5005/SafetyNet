package com.lime.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FireCoverDto {
    private Set<APerson> personSet;
    private int total_adult;
    private int total_child;

    public FireCoverDto() {
    }

    public FireCoverDto(Set<APerson> personSet, int total_adult, int total_child) {
        this.personSet = personSet;
        this.total_adult = total_adult;
        this.total_child = total_child;
    }

    public Set<APerson> getPersonSet() {
        return personSet;
    }

    public void setPersonSet(Set<APerson> personSet) {
        this.personSet = personSet;
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
