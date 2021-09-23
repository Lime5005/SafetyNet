package com.lime.repository;

import com.lime.domain.Database;
import com.lime.domain.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Set<Station> getStationsByNumber(int station) {
        Set<Station> set = new HashSet<>();
        for (Station fireStation : this.getAllStations()) {
            if (fireStation.getStation() == station) {
                set.add(fireStation);
            }
        }
        return set;
    }

    public Set<String> findAllAddresses(Set<Station> stations) {
        Set<String> addresses = new HashSet<>();
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

    /**
     * Create a new station if it does not exist yet, by checking both the address and the station number.
     * @param station A Station object.
     * @return true if added successfully, or false if failed.
     */
    public Boolean save(Station station) {
        boolean found = false;
        if (station.getAddress() == null || station.getStation() == 0) {
            return false;
        }
        for (Station aStation : this.getAllStations()) {
            if ( station.getAddress().equals(aStation.getAddress()) && station.getStation() == aStation.getStation()) {
                found = true;
                break;
            }
        }
        if (!found) {
            this.getAllStations().add(station);
        }
        return !found;
    }

    /**
     * Update a station number for an address.
     * @param station A Station object.
     * @return true if updated successfully, or false if failed.
     */
    public Boolean updateStation(Station station) {
        if (station.getAddress() != null && station.getStation() != 0) {
            for (Station aStation : this.getAllStations()) {
                if (station.getAddress().equals(aStation.getAddress()) &&
                        station.getStation() != aStation.getStation()) {
                    aStation.setStation(station.getStation());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Delete a station by its address.
     * @param station A Station object.
     * @return true if deleted successfully, or false if failed.
     */
    public Boolean deleteStation(Station station) {
        if (station.getAddress() != null && station.getStation() != 0) {
            for (Station aStation : this.getAllStations()) {
                if (station.getAddress().equals(aStation.getAddress())) {
                    this.getAllStations().remove(aStation);
                    return true;
                }
            }
        }
        return false;
    }
}
