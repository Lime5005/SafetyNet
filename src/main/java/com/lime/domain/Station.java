package com.lime.domain;

import org.springframework.stereotype.Component;

@Component
public class Station {
    private String address;
    private int station;

    public Station() {
    }

    public Station(String address, int station) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "Station{" +
                "address='" + address + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
