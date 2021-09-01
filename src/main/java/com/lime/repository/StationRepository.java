package com.lime.repository;

import com.lime.domain.Database;
import com.lime.domain.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public Station getStationByNumber(int station) {
        for (Station fireStation : this.getAllStations()) {
            if (fireStation.getStation() == station) {
                return fireStation;
            }
        }
        return null;
    }

    public Station getStationByAddress(String address) {
        for (Station fireStation : this.getAllStations()) {
            if (fireStation.getAddress().equals(address)) {
                return fireStation;
            }
        }
        return null;
    }
}
