package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

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

    /**
     * This is a custom method used to make code more concise, less repetative and more readable
     *
     * @author (Dylan Foster)
     * @version (18/02/2026)
     * @param (int stationId)
     */
    public Station findStationFromGivenId(int stationId) throws IDNotRecognisedException
    {
        // the for loops are getting annoying. we should only need one
        for (Station station : stations) {
            if (station.getId() == stationId) {
                return station;
            }
        }

        throw new IDNotRecognisedException("ID is not in ID list. Station doesn't exist");
    }

    /**
     * This is a custom method used to make code more concise, less repetative and more readable
     *
     * @author (Dylan Foster)
     * @version (18/02/2026)
     * @param (int unitId)
     */
    public Units findUnitFromGivenId(int unitId) throws IDNotRecognisedException
    {
        // the for loops are getting annoying. we should only need one
        for (Station station : stations) {
            for (Units unit : station.getOwnedUnitList())
                if (unit.getUnitId() == unitId) {
                    return unit;
            }
        }

        throw new IDNotRecognisedException("ID is not in ID list. Unit doesn't exist");
    }

    /**
     * This is a custom method used to make code more concise, less repetative and more readable
     *
     * @author (Dylan Foster)
     * @version (18/02/2026)
     * @param (int unitId)
     */
    public Station findStationFromGivenUnitId(int unitId) throws IDNotRecognisedException
    {
        return findStationFromGivenId(findUnitFromGivenId(unitId).getOwnerId());
    }

    /**
     * I made this method to seperate the creation of a Unit from it's addition to
     * a station. Makes adding and removing Units less messy
     *
     * @author (Dylan Foster)
     * @version (18/02/2026)
     * @param (int unitId)
     */
    public Units createUnit(UnitType type) throws InvalidUnitException
    {
        if (type == UnitType.AMBULANCE) {
            return new Ambulance();

        }

        if (type == UnitType.FIRE_ENGINE) {
            return new FireEngine();

        }

        if (type == UnitType.POLICE_CAR) {
            return new PoliceCar();
        }

        throw new InvalidUnitException("Unit type not recognised");
    }

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
    public int addStation(String name, int x, int y) throws cityrescue.exceptions.InvalidNameException, InvalidLocationException {
        // There's already a InvalidNameException in Java. I doin't know why I have to do this.
        if (name.isEmpty()) {
            throw new cityrescue.exceptions.InvalidNameException("Name of station cannot be empty");
        }
        
        if (x < 0 || x >= this.cityMap.getSize()[0]) {
            throw new InvalidLocationException("Trying to place a station out of bounds");
        }
        
        if (y < 0 || y >= this.cityMap.getSize()[1]) {
            throw new InvalidLocationException("Trying to place a station out of bounds");
        }

        // Stations can take any type of Unit(Vehicle)
        
        Station tempObj = new Station(name, x, y);
        for (int i = 0; i < stations.length; i++) {
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
        for (int i = 0; i < stations.length; i++){
            if (stations[i].getId() == stationId) {
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

        Station station = findStationFromGivenId(stationId);
        station.setMaxCapacity(maxUnits);
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

        // Use stationId to set Unit ownership
        // Unit Class needs to be made
        // Creates a Unit object which  will have to be recorded

        // Make a unit and tie it to the station given

        // Loop finds the corresponding station and checks it's values
        // Returns added unit id if found, else throws an error
        // this.unitId --> ownedUnits[i].getUnitId() --> stations[i].addUnit(type) --> unit Id

        // Create unit first then add it to station ownership array

        // get station
        Station station = findStationFromGivenId(stationId);
        // make unit
        Units unit = createUnit(type);
        // add unit
        station.addUnit(unit);
        // return unit id
        return unit.getUnitId();
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {

        Units unit = findUnitFromGivenId(unitId);

        if (unit.getUnitStatus() == UnitStatus.AT_SCENE || unit.getUnitStatus() == UnitStatus.EN_ROUTE) {
            throw new IllegalStateException("Cannot remove a unit that's at or en route to an incident");
        }

        Station station = findStationFromGivenUnitId(unitId);
        station.removeUnit(unitId);
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // changes the ownership a Unit from one station to another
        // Possible methods:
        //  Drag and drop as apecific object from one station array to another.
        Station oldStation = findStationFromGivenUnitId(unitId);
        Station newStation = findStationFromGivenId(newStationId);
        Units selectUnit = findUnitFromGivenId(unitId);

        if (selectUnit.getUnitStatus() == UnitStatus.AT_SCENE || selectUnit.getUnitStatus() == UnitStatus.EN_ROUTE) {
            throw new IllegalStateException("Cannot transfer a unit that's at or en route to an incident");
        }

        oldStation.removeUnit(unitId);
        newStation.addUnit(selectUnit);

    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        
        Units selectUnit = findUnitFromGivenId(unitId);

        if (selectUnit.getUnitStatus() == UnitStatus.AT_SCENE || selectUnit.getUnitStatus() == UnitStatus.EN_ROUTE) {
            throw new IllegalStateException("Cannot set a unit that's at or en route to an incident to out of service");
        }
        
        if (outOfService) {
            selectUnit.setUnitStatus(UnitStatus.OUT_OF_SERVICE);
        }
        // It would be very strange for the code to end up here
    }

    @Override
    public int[] getUnitIds() {

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

