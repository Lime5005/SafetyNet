package com.lime.repository;

import com.lime.domain.Database;
import com.lime.domain.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StationRepository {

    Database database;

    @Autowired
    public StationRepository(Database database) {
        this.database = database;
    }

    public List<Station> getAllStations() {
        return database.getFirestations();
    }

    /**
     * For all the stations with same number, but different addresses, get them all.
     * @param station station number.
     * @return a list of Station object.
     */
    public List<Station> getStationsByNumber(int station) {
        List<Station> list = new ArrayList<>();
        for (Station fireStation : this.getAllStations()) {
            if (fireStation.getStation() == station) {
                list.add(fireStation);
            }
        }
        return list;
    }

    public List<String> findAllAddresses(List<Station> stations) {
        List<String> addresses = new ArrayList<>();
        for (Station station : stations) {
            addresses.add(station.getAddress());
        }
        return addresses;
    }

    public List<Station> getStationsByAddress(String address) {
        List<Station> list = new ArrayList<>();
        for (Station fireStation : this.getAllStations()) {
            if (fireStation.getAddress().equals(address)) {
                list.add(fireStation);
            }
        }
        return list;
    }
}
