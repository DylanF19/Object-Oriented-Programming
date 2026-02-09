package java.cityrescue;

import java.cityrescue.enums.*;
import java.cityrescue.exceptions.*;
import java.cityrescue.Station;
import java.cityrescue.CityMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {

    // TODO: add fields (map, arrays for stations/units/incidents, counters, tick, etc.)
    
    CityMap CityMap;
    public HashMap<Integer, Station> hashMapStation = new HashMap<Integer, Station>();
    
    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // TODO: implement
        
        // - Data validation - 
        if (width > 0) {
           this.CityMap.getSize()[0] = width; 
        } else {
            throw new InvalidGridException("Invalid Size: width has to be greater than 0");
        }
        
        if (height > 0) {
           this.CityMap.getSize()[1] = height; 
        } else {
            throw new InvalidGridException("Invalid Size: height has to be greater than 0");
        }
        
        // This is quite unclear | The Type, Name and Constructor are all the same name
        CityMap CityMap = new CityMap(width, height);
        
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getGridSize() {
        // TODO: implement
        return CityMap.getSize();
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        
        // - Data validation - 
        if (x < 0 || x >= this.CityMap.getSize()[0]) {
            throw new InvalidLocationException("Trying to place an obstacle out of bounds");
        }
        
        if (y < 0 || y >= this.CityMap.getSize()[1]) {
            throw new InvalidLocationException("Trying to place an obstacle out of bounds");
        }
        // Though this may not cause an error when run without this conditional, 
        // it would make it harder to keep track of certain values when overwriting a 
        // space's value with that same value.
        if (this.CityMap.isCellObstructed(x, y)) {
            throw new InvalidLocationException("Trying to place an obstacle where there is already one");
        }
        
        // Should I change the value here or within the class via a method call?
        this.CityMap.addObstacle(x, y);
        
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        
        // - Data validation - 
        if (x < 0 || x >= this.CityMap.getSize()[0]) {
            throw new InvalidLocationException("Trying to remove an obstacle that doesn't (Couldn't) exist");
        }
        
        if (y < 0 || y >= this.CityMap.getSize()[1]) {
            throw new InvalidLocationException("Trying to remove an obstacle that doesn't (Couldn't) exist");
        }
        // Same logic here as the method above
        if (this.CityMap.isCellClear(x, y)) {
            throw new InvalidLocationException("Trying to remove an obstacle from an empty space");
        }
        
        this.CityMap.removeObstacle(x, y);
        
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        // TODO: implement
        if (name.isEmpty()) {
            throw new InvalidNameException("Name of station cannot be empty");
        }
        
        if (x < 0 || x >= this.CityMap.getSize()[0]) {
            throw new InvalidLocationException("Trying to place a station out of bounds");
        }
        
        if (y < 0 || y >= this.CityMap.getSize()[1]) {
            throw new InvalidLocationException("Trying to place a station out of bounds");
        }
        
        // TODO: code station object so that we can 
        // assign values to them.
        // may need to make a list of stations to prevent overlap
        // of names or coords.
        // Stations can take any type of Unit(Vehicle)
        
        // return station ID
        int tempId = Station.getNumberOfStations();
        hashMapStation.put(Station.getNumberOfStations(), new Station(name, x, y));
        return tempId;
        
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        
        if (!hashMapStation.containsKey(stationId)) {
            throw new IDNotRecognisedException("No such station exists");
        }
        
        Station station = hashMapStation.get(stationId);
        
        if (station.getNumberOfOwnedUnits() != 0) {
            throw new IllegalStateException("Trying to remove a station that still owns units");
        }
        
        hashMapStation.remove(stationId);
        
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        // TODO: implement
        
        if (!hashMapStation.containsKey(stationId)) {
            throw new IDNotRecognisedException("No such station exists");
        }
        
        if (maxUnits < 0) {
            throw new InvalidCapacityException("Capacity must not be less than 0");
        }
        
        Station station = hashMapStation.get(stationId);
        
        if (station.getNumberOfOwnedUnits() > maxUnits) {
            throw new InvalidCapacityException("Cannot set max capacity to a number lower than the currently owned units");
        }
        
        station.setMaxCapacity(maxUnits);
        
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getStationIds() {
        // TODO: implement
        int listLength = hashMapStation.size();
        // HashMap to int Set
        Set<Integer> idKeySet = hashMapStation.keySet();
        // int Set to int Array
        int[] idArray = idKeySet.stream().mapToInt(x -> x).toArray();
        
        return idArray;
        
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}



