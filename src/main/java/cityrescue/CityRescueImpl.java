package main.java.cityrescue;

import main.java.cityrescue.enums.*;
import main.java.cityrescue.exceptions.*;
import main.java.cityrescue.CityMap;  //sonarqube says this is imported implicitly. IDK what to trust
import java.util.HashMap;
import java.util.Set;

import javax.naming.InvalidNameException;

import java.util.Map;
/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {

    // add fields (map, arrays for stations/units/incidents, counters, tick, etc.) as they are needed
    // Do check if it is necessary to put things here. Globals and objects; sure.
    // individual variables; maybe not.

    // Initialise the map of the city for the constructor
    CityMap cityMap;
    // It's an array now with a max size of 20
    Station[] stations = new Station[20];

    
    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // - Data validation - 
        if (width > 0) {
            throw new InvalidGridException("Invalid Size: width has to be greater than 0");
        }
        
        if (height > 0) {
            throw new InvalidGridException("Invalid Size: height has to be greater than 0");
        }
        
        cityMap = new CityMap(width, height);

    }

    @Override
    public int[] getGridSize() {
        return cityMap.getSize();
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        // - Data validation - 
        if (x < 0 || x >= this.cityMap.getSize()[0]) {
            throw new InvalidLocationException("Trying to place an obstacle out of bounds");
        }
        
        if (y < 0 || y >= this.cityMap.getSize()[1]) {
            throw new InvalidLocationException("Trying to place an obstacle out of bounds");
        }
        // Though this may not cause an error when run without this conditional, 
        // it would make it harder to keep track of certain values when overwriting a 
        // space's value with that same value.
        if (this.cityMap.isCellObstructed(x, y)) {
            throw new InvalidLocationException("Trying to place an obstacle where there is already one");
        }
        
        // Should I change the value here or within the class via a method call?
        this.cityMap.addObstacle(x, y);

    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        // - Data validation - 
        if (x < 0 || x >= this.cityMap.getSize()[0]) {
            throw new InvalidLocationException("Trying to remove an obstacle that doesn't (Couldn't) exist");
        }
        
        if (y < 0 || y >= this.cityMap.getSize()[1]) {
            throw new InvalidLocationException("Trying to remove an obstacle that doesn't (Couldn't) exist");
        }
        // Same logic here as the method above
        if (this.cityMap.isCellClear(x, y)) {
            throw new InvalidLocationException("Trying to remove an obstacle from an empty space");
        }
        
        this.cityMap.removeObstacle(x, y);
    }

    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {

        if (name.isEmpty()) {
            throw new InvalidNameException("Name of station cannot be empty");
        }
        
        if (x < 0 || x >= this.cityMap.getSize()[0]) {
            throw new InvalidLocationException("Trying to place a station out of bounds");
        }
        
        if (y < 0 || y >= this.cityMap.getSize()[1]) {
            throw new InvalidLocationException("Trying to place a station out of bounds");
        }

        // Stations can take any type of Unit(Vehicle)
        
        tempObj = new Station(name, x, y);
        for (int i; i < stations.length; i++) {
            if (stations[i] == null) {
                stations[i] = tempObj;
            }
        }

        return tempObj.getId();
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        
        if (!hashMapStation.containsKey(stationId)) {
            throw new IDNotRecognisedException("No such station exists");
        }
        
        if (station.getNumberOfOwnedUnits() != 0) {
            throw new IllegalStateException("Trying to remove a station that still owns units");
        }
        // need to check if the stationID matches the number of stations at the point it was set
        for (int i; i < stations.length; i++) {
            if (stations[i].getId() == stationId) {
                stations[i] = null;
            }
        }
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        
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
    }

    @Override
    public int[] getStationIds() {
        // HashMap to int Set
        Set<Integer> idKeySet = hashMapStation.keySet();
        // int Set to int Array
        return idKeySet.stream().mapToInt(x -> x).toArray();
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        // Use stationId to set Unit ownership
        // Unit Class needs to be made
        // Creates a Unit object which  will have to be recorded
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        // removes the corresponding Unit.
        // removing the Unit by removing it's entry from a list 
        // could be counted as a data leak.
        // I wonder if an object will have to be explicitly removed
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        // changes the ownership a Unit from one station to another
        // Likely be as simple as an overwrite in the object's variables.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        // A state change using the given enums.
        // Other states will have to be disabled.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        // Similar approach to getting the station ids
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

