package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

import java.util.Arrays;

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
    int maxUnitAmount = 50;
    int maxIncidentAmount = 200;
    // Initialise the map of the city for the constructor
    CityMap cityMap;

    // max length arrays
    Station[] stations = new Station[maxStationAmount];
    Units[] units = new Units[maxUnitAmount];
    Incident[] incidents = new Incident[maxIncidentAmount];


    /**
     * This is a custom method used to make code more concise, less repetative and more readable
     *
     * @author (Dylan Foster)
     * @version (18/02/2026)
     * @param (int incidentId)
     */
    public Incident findIncidentFromGivenId(int incidentId) throws IDNotRecognisedException
    {
        // the for loops are getting annoying. we should only need one
        for (Incident incident : incidents) {
            if (incident.getIncidentId() == incidentId) {
                return incident;
            }
        }

        throw new IDNotRecognisedException("Incident ID not recognised");
    }

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
            if (station.getStationId() == stationId) {
                return station;
            }
        }

        throw new IDNotRecognisedException("Station ID not recognised");
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

        throw new IDNotRecognisedException("Unit ID not recognised");
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
     * This is a custom method used to make code more concise, less repetative and more readable
     *
     * @author (Dylan Foster)
     * @version (18/02/2026)
     * @param (int unitId)
     */
    public Units findUnitFromGivenIncidentId(int incidentId) throws IDNotRecognisedException
    {
        return findUnitFromGivenId(findIncidentFromGivenId(incidentId).getOwnerId());
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
        
        Station tempStation = new Station(name, x, y);
        for (int i = 0; i < stations.length; i++) {
            if (stations[i] == null) {
                stations[i] = tempStation;
                break;
            }
        }

        return tempStation.getStationId();
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
    
        // got through the list, check each id. If found, set to null, else continue
        // if no Id found, raise error
        boolean found = false;
        for (int i = 0; i < stations.length; i++){
            if (stations[i].getStationId() == stationId) {
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
            idList[i] = stations[i].getStationId();
        }
        Arrays.sort(idList);
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
    // This is such a mess

        int[] idList = new int[Units.getNumberOfUnits()];
        int j = 0;

        for (Station station : stations) {

            Units[] stationUnitList = station.getOwnedUnitList();

            for(int i = 0; i < stationUnitList.length; i++) {

                if (stationUnitList[i] == null) {

                    idList[j] = stationUnitList[i].getUnitId();

                    j++;
                }
            }
        }
        Arrays.sort(idList);
        return idList;
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // Info needed:
        //  id, owner, coordinates, type, state
        Units unit = findUnitFromGivenId(unitId);
        Station station = findStationFromGivenUnitId(unitId);

        String owner = station.getName();
        int x = unit.getCoordinates()[0];
        int y = unit.getCoordinates()[1];
        UnitType type = unit.getUnitType();
        UnitStatus status = unit.getUnitStatus();

        String header = "===========================\n";
        return String.format("%7 -- Unit details -- %n Unit id: %1 %n Unit owner: Station %2 %n Unit location: %3,%4 %n Unit type: %5 %n Unit status %6 %n %7",
                            unitId,
                            owner,
                            x,
                            y,
                            type,
                            status,
                            header);

    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        
        if (severity < 1 || severity > 5) {
            throw new InvalidSeverityException("Severity must be in the bounds of 1 to 5");
        }

        if (cityMap.isCellObstructed(x, y)) {
            throw new InvalidLocationException("Incident cannot be created on a blocked cell");
        }
        
        for (int i = 0; i < incidents.length; i++) {
            
            if (incidents[i] != null) {

                Incident tempIncident = new Incident(x, y, type, severity);
                incidents[i] = tempIncident;
                return tempIncident.getIncidentId();

            }
        }

        throw new CapacityExceededException("Far too many incidents");

    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {

        Incident incident = findIncidentFromGivenId(incidentId);

        if (incident.getIncidentStatus() != IncidentStatus.REPORTED || incident.getIncidentStatus() != IncidentStatus.DISPATCHED) {
            throw new IllegalStateException("Cannot remove a unit that's at or en route to an incident");
        }

        incident.setIncidentStatus(IncidentStatus.CANCELLED);

        Units unit = findUnitFromGivenIncidentId(incidentId);
        unit.clearIncidentFocus();
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        Incident incident = findIncidentFromGivenId(incidentId);
        if (incident.getIncidentStatus() == IncidentStatus.RESOLVED || incident.getIncidentStatus() == IncidentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot escelate an incident not currently in progress");
        }
        if (newSeverity < 1 || newSeverity > 5) {
            throw new InvalidSeverityException("Severity must be in the bounds of 1 to 5");
        }
        incident.setSeverity(newSeverity);
    }

    @Override
    public int[] getIncidentIds() {
        int[] IncidentIdList = new int[Incident.getNumberOfIncidents()];
        int j = 0;
        for(int i = 0; i < incidents.length; i++) {
            if (incidents[i] != null) {
                IncidentIdList[j] = incidents[i].getIncidentId();
                j++;
            }
        }
        Arrays.sort(IncidentIdList);
        return IncidentIdList;
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        Incident incident = findIncidentFromGivenId(incidentId);
        if (incident != null){
            String type = (incident.getIncidentType()).toString();
            String status = (incident.getIncidentStatus()).toString();
            String severity = Integer.toString(incident.getSeverity());
            String formatedString = ("Type: " + type + "Status: " + status + "Severity: " + severity);
            return formatedString;
        }
        else{
            throw new IDNotRecognisedException("ID not recognised");
        }
    }

    @Override
    public void dispatch() {
    int[] ReportedIncidentList = new int[maxIncidentAmount];
        Incident[] ReportedIncidents = new Incident[maxIncidentAmount];
        int j = 0;
        for (int i = 0; i < incidents.length; i++) {
            IncidentStatus status = incidents[i].getIncidentStatus();
            if (status.equals("REPORTED"))
            {
                int tempID = incidents[i].getIncidentId();
                ReportedIncidentList[j] = tempID;
                ReportedIncidents[j] = incidents[i];
                j+=1;
            }
        }
        Arrays.sort(ReportedIncidentList);
        Incident[] OrderedReportedIncidents = new Incident[ReportedIncidentList.length];
        for (int k = 0; k < ReportedIncidentList.length; k++)
        {
            for (Incident incident : ReportedIncidents)
            {
                if (incident.getIncidentId() == ReportedIncidentList[k])
                {
                    OrderedReportedIncidents[k] = incident;
                }
            }
        }
        for (Incident incident : OrderedReportedIncidents)
        {
            UnitType responseType = UnitType.AMBULANCE;
            
            if (incident.getIncidentType().equals(IncidentType.MEDICAL))
            {
                responseType = UnitType.AMBULANCE;
            }
            if (incident.getIncidentType().equals(IncidentType.FIRE))
            {
                responseType = UnitType.FIRE_ENGINE;
            }
            if (incident.getIncidentType().equals(IncidentType.CRIME))
            {
                responseType = UnitType.POLICE_CAR;
            }

            Units[] ElegibleUnits = new Units[maxUnitAmount];
            int x = 0;
            for (Units unit : units)
            {
                if ((unit.getUnitType().equals(responseType)) && (unit.getUnitStatus().equals(UnitStatus.IDLE)))
                {
                    ElegibleUnits[x] = unit;
                    x+=1;
                }
            }

            Units[] ShortestDistanceUnit = new Units[1];

            int[] incidentCoordinates = incident.getCoordinates();
            int lowestDistance = 999999;

            for (Units unit : ElegibleUnits)
            {
                int[] unitCoordinates = unit.getCoordinates();
                int distance = Math.abs(incidentCoordinates[0] - unitCoordinates[0]) - Math.abs(incidentCoordinates[1] - unitCoordinates[1]);
                if (distance < lowestDistance)
                {
                    lowestDistance = distance;
                    ShortestDistanceUnit[0] = unit;
                }
                if (distance == lowestDistance)
                {
                    if (ShortestDistanceUnit[0].getUnitId() > unit.getUnitId())
                    {
                        lowestDistance = distance;
                        ShortestDistanceUnit[0] = unit;
                    }
                    if (ShortestDistanceUnit[0].getUnitId() == unit.getUnitId())
                    {
                        if (ShortestDistanceUnit[0].getOwnerId() > unit.getOwnerId())
                    {
                        lowestDistance = distance;
                        ShortestDistanceUnit[0] = unit;
                    }
                    }
                }
            }
            incident.setIncidentStatus(IncidentStatus.DISPATCHED);
            ShortestDistanceUnit[0].setUnitStatus(UnitStatus.EN_ROUTE);
            ShortestDistanceUnit[0].setIncidentFocus(incident.getIncidentId());
        }
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


