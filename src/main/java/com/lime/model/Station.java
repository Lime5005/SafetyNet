package com.lime.model;

public class Station {
    private String address;
    private int stationNumber;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return stationNumber;
    }

    public void setStation(int station) {
        this.stationNumber = station;
    }

    @Override
    public String toString() {
        return "Station{" +
                "address='" + address + '\'' +
                ", station='" + stationNumber + '\'' +
                '}';
    }
}
