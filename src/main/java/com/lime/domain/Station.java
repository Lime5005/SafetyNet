package com.lime.domain;

public class Station {
    private String address;
    private int station;

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
