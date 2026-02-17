package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;
import cityrescue.CityMap;  //sonarqube says this is imported implicitly. IDK what to trust
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
    int maxStationAmount = 20;
    int maxUnitAmount = 40;
    // Initialise the map of the city for the constructor
    CityMap cityMap;
    // It's an array now with a max size of 20
    Station[] stations = new Station[maxStationAmount];
    Units[] units = new Units[maxUnitAmount];
    
    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // - Data validation - 
        if (width <= 0) {
            throw new InvalidGridException("Invalid Size: width has to be greater than 0");
        }
        
        if (height <= 0) {
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
        // There's already a InvalidNameException in Java. I doin't know why I have to do this.
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
        
        Station tempObj = new Station(name, x, y);
        for (int i; i < stations.length; i++) {
            if (stations[i] == null) {
                stations[i] = tempObj;
                break;
            }
        }

        return tempObj.getId();
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
    
        // got through the list, check each id. If found, set to null, else continue
        // if no Id found, raise error
        boolean found = false;
        for (int i = 0; i < stations.length; i++) {
            if (stations[i].getId() == stationId) {
                if (stations[i].getNumberOfOwnedUnits() != 0) {
                    throw new IllegalStateException("Trying to remove a station that still owns units");
                }

                stations[i] = null;
                found = true;
            }
        }

        if (!found) { // if Id not found
            throw new IDNotRecognisedException("No such station exists");
        }
        
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        
        if (maxUnits < 0) {
            throw new InvalidCapacityException("Capacity must not be less than 0");
        }

        boolean found = false;
        for (int i = 0; i < stations.length; i++) {
            if (stations[i].getId() == stationId) {
                stations[i].setMaxCapacity(maxUnits);
                }

                stations[i] = null;
                found = true;
            }

        if (!found) { // if Id not found
            throw new IDNotRecognisedException("No such station exists");
        }
    }

    @Override
    public int[] getStationIds() {
        int[] idList = new int[maxStationAmount];
        for (int i = 0; i < stations.length; i++) {
            idList[i] = stations[i].getId();
        }
        return idList;
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

