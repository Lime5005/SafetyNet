package com.lime.domain;

public class Station {
    private String address;
    private String station;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
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
