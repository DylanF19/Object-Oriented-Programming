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

    private CityMap cityMap;

    public CityMap getMap() {
        return this.cityMap;
    }

    int ticks = 0;

    // max length arrays || now set in initialize() to reset values every time a map is made
    private Station[] stations;
    private Units[] units;
    private Incident[] incidents;

    public Station[] getStations() {
        return this.stations;
    }
    public Units[] getUnits() {
        return this.units;
    }
    public Incident[] getIncidents() {
        return this.incidents;
    }

    /**
     * This method returns a sorted list of Incident Ids, cleaned of null values.
     *
     * @author (Dylan Foster, Oliver Irving)
     * @version (24/02/2026)
     * @param (Incident[] incidentList)
     */
    public int[] createIncidentIdList(Incident[] incidentList)
    {
        int notNULLcounter = 0;
        for (Incident incident : incidents) {
            if (incident != null) {
                notNULLcounter++;
            }
        }

        int[] incidentIdList = new int[notNULLcounter];
        int index = 0;
        for (Incident incident : incidentList) {
            if (incident != null) {
                incidentIdList[index] = incident.getIncidentId();
                index++;
            }
        }
        Arrays.sort(incidentIdList);
        return incidentIdList;
    }


    /**
     * This method returns a sorted list of Unit Ids, cleaned of null values.
     *
     * @author (Dylan Foster, Oliver Irving)
     * @version (24/02/2026)
     * @param (Units[] unitList)
     */
    public int[] createUnitIdList(Units[] unitList)
    {
        int notNULLcounter = 0;
        for (Units unit : unitList) {
            if (unit != null) {
                notNULLcounter++;
            }
        }

        int[] unitIdList = new int[notNULLcounter];
        int index = 0;
        for (Units unit : unitList) {
            if (unit != null) {
                unitIdList[index] = unit.getUnitId();
                index++;
            }
        }
        Arrays.sort(unitIdList);
        return unitIdList;
    }


    /**
     * This method returns a sorted list of Station Ids, cleaned of null values.
     *
     * @author (Dylan Foster, Oliver Irving)
     * @version (24/02/2026)
     * @param (Station[] stationList)
     */
    public int[] createStationIdList(Station[] stationList)
    {
        int notNULLcounter = 0;
        for (Station station : stationList) {
            if (station != null) {
                notNULLcounter++;
            }
        }

        int[] stationIdList = new int[notNULLcounter];
        int index = 0;
        for (Station station : stationList) {
            if (station != null) {
                stationIdList[index] = station.getStationId();
                index++;
            }
        }
        Arrays.sort(stationIdList);
        return stationIdList;
    }

    /**
     * This is a custom method used to make code more concise, less repetative and more readable
     *
     * @author (Dylan Foster, Oliver Irving)
     * @version (18/02/2026)
     * @param (int incidentId)
     */
    public Incident findIncidentFromGivenId(int incidentId)
    {
        // the for loops are getting annoying. we should only need one
        for (Incident incident : incidents) {
            if (incident != null && incident.getIncidentId() == incidentId) {
                return incident;
            }
        }


        return null;
        
    }

    /**
     * This is a custom method used to fetch a Station from its Id
     *
     * @author (Dylan Foster, Oliver Irving)
     * @version (18/02/2026)
     * @param (int stationId)
     */
    public Station findStationFromGivenId(int stationId)
    {
        // the for loops are getting annoying. we should only need one
        for (Station station : stations) {
            if (station != null && station.getStationId() == stationId) {
                return station;
            }
        }

        return null;
    }

    /**
     * This is a custom method used to fetch a unit from its Id
     *
     * @author (Dylan Foster, Oliver Irving)
     * @version (18/02/2026)
     * @param (int unitId)
     */
    public Units findUnitFromGivenId(int unitId)
    {
        // the for loops are getting annoying. we should only need one
        for (Units unit : units) {
            if (unit != null && unit.getUnitId() == unitId) {
                return unit;
                }
            }
        return null;
    }

    /**
     * This is a custom method used to fetch a Station from an owned unit
     *
     * @author (Dylan Foster, Oliver Irving)
     * @version (18/02/2026)
     * @param (int unitId)
     */
    public Station findStationFromGivenUnitId(int unitId)
    {
        return findStationFromGivenId(findUnitFromGivenId(unitId).getOwnerId());
    }

    /**
     * This is a custom method used to fetch a unit from an incident
     *
     * @author (Dylan Foster, Oliver Irving)
     * @version (18/02/2026)
     * @param (int unitId)
     */
    public Units findUnitFromGivenIncidentId(int incidentId)
    {
        return findUnitFromGivenId(findIncidentFromGivenId(incidentId).getOwnerId());
    }

    /**
     * I made this method to seperate the creation of a Unit from it's addition to
     * a station. Makes adding and removing Units less messy
     *
     * @author (Dylan Foster, Oliver Irving)
     * @version (18/02/2026)
     * @param (int unitId)
     */
    public Units createUnit(UnitType type)
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

        return null;
    }

    /**
     * I made this make Exception tracebacks more detailed and make sure
     * that things were going as expected. THis is coupled with a custom traceback
     * method
     * 
     * @author (Dylan Foster, Oliver Irving)
     * @version (25/02/2026)
     * @param (int stationId)
     */
    public String viewStation(int stationId) throws IDNotRecognisedException {
        Station station = findStationFromGivenId(stationId);

        if (station == null) {
            throw new IDNotRecognisedException("No such station exists");
        }

        int x = station.getCoords()[0];
        int y = station.getCoords()[1];
        int parkingSpace = station.getParkingSpace();
        int ownedUnits = station.getNumberOfOwnedUnits();

        return String.format("St#%d LOC=(%d,%d) PARKING_SPACE=%d OWNED_UNITS=%d %n",
                                stationId,
                                x,
                                y,
                                parkingSpace,
                                ownedUnits);
    }

    /* ==================================================================================================
     *
     * 
     *  Method Start
     * 
     * 
     * ==================================================================================================
    */

    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // - Data validation - 
        if (width <= 0) {
            throw new InvalidGridException("Invalid Size: width has to be greater than 0");
        }
        
        if (height <= 0) {
            throw new InvalidGridException("Invalid Size: height has to be greater than 0");
        }
        
        // Added resets so that id's canstart at one for different instances
        // and multiple tests in a row.
        Station.resetStation();
        Units.resetUnits();
        Incident.resetIncident();

        ticks = 0;
        // max length arrays
        stations = new Station[maxStationAmount];
        units = new Units[maxUnitAmount];
        incidents = new Incident[maxIncidentAmount];

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

        if (maxStationAmount - Station.getNumberOfStations() <= 0) {
            throw new CapacityExceededException("Too many stations");
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
        Station station = findStationFromGivenId(stationId);

        if (station == null) {
            throw new IDNotRecognisedException("No such station exists");
        }

        if (station.getNumberOfOwnedUnits() > 0) {
            throw new IllegalStateException("Station is not empty");
        }

        for (int i = 0; i < stations.length; i++){
            if (stations[i].getStationId() == stationId) {
                stations[i] = null;
                return;
            }
        }
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        
        if (maxUnits < 0) {
            throw new InvalidCapacityException("Capacity must not be less than 0");
        }

        if (maxUnits < getNumberOfOwnedUnits()) {
            throw new InvalidCapacityException("Cannot be lower than the number of current occupants");
        }

        Station station = findStationFromGivenId(stationId);

        if (station == null) {
            throw new IDNotRecognisedException("No such station exists");
        }

        station.setMaxCapacity(maxUnits);
    }

    @Override
    public int[] getStationIds() {
        return createStationIdList(stations);
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

        if (maxUnitAmount - Units.getNumberOfUnits() <= 0) {
            throw new CapacityExceededException("Too many units");
        }

        if (station == null) {
            throw new IDNotRecognisedException("No such station exists");
        }

        if (station.getRemainingCapacity() <= 0) {
            throw new IllegalStateException("Capacity of station full");
        }

        // make unit
        Units unit = createUnit(type);
        // add unit
        station.addUnit(unit);


        for (int i = 0; i < units.length; i++) {
            if (units[i] == null) {
                units[i] = unit;
                break;
            }
        }

        // return unit id
        return unit.getUnitId();
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {

        Units unit = findUnitFromGivenId(unitId);

        if (unit == null) {
            throw new IDNotRecognisedException("No such unit exists");
        }

        if (unit.getUnitStatus() == UnitStatus.AT_SCENE || unit.getUnitStatus() == UnitStatus.EN_ROUTE) {
            throw new IllegalStateException("Cannot remove a unit that's at or en route to an incident");
        }

        Station station = findStationFromGivenUnitId(unitId);

        if (station == null) {
            throw new IDNotRecognisedException("No such station exists (Something has gone very wrong here)");
        }

        station.removeUnit(unitId);
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // changes the ownership a Unit from one station to another
        // Possible methods:
        //  Drag and drop as apecific object from one station array to another.
        Station oldStation = findStationFromGivenUnitId(unitId);

        if (oldStation == null) {
            throw new IDNotRecognisedException("No such station exists");
        }

        Station newStation = findStationFromGivenId(newStationId);

        if (newStation == null) {
            throw new IDNotRecognisedException("No such station exists");
        }

        Units selectUnit = findUnitFromGivenId(unitId);

        if (selectUnit == null) {
            throw new IDNotRecognisedException("No such unit exists");
        }

        if (selectUnit.getUnitStatus() == UnitStatus.AT_SCENE || selectUnit.getUnitStatus() == UnitStatus.EN_ROUTE) {
            throw new IllegalStateException("Cannot transfer a unit that's at or en route to an incident");
        }

        oldStation.removeUnit(unitId);
        newStation.addUnit(selectUnit);

    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        
        Units selectUnit = findUnitFromGivenId(unitId);

        if (selectUnit == null) {
            throw new IDNotRecognisedException("No such unit exists");
        }

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
        return createUnitIdList(units);
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {

        Units unit = findUnitFromGivenId(unitId);

        if (unit == null) {
            throw new IDNotRecognisedException("No such unit exists");
        }

        Station station = findStationFromGivenUnitId(unitId);
        
        if (station == null) {
            throw new IDNotRecognisedException("Unit not affiliated with any station (very bad)");
        }

        int owner = station.getStationId();
        int x = unit.getCoordinates()[0];
        int y = unit.getCoordinates()[1];
        UnitType type = unit.getUnitType();
        UnitStatus status = unit.getUnitStatus();

        if (unit.getIncidentFocus() == null) {
            String incident = "-";
            return String.format("U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%s %n",
                                unitId,
                                type,
                                owner,
                                x,
                                y,
                                status,
                                incident);

        } else if (unit.getIncidentCountdown() == -1) {
            int incident = unit.getIncidentFocus().getIncidentId();
            return String.format("U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%d %n",
                                unitId,
                                type,
                                owner,
                                x,
                                y,
                                status,
                                incident);

        } else {
            int countdown = unit.getIncidentCountdown();
            int incident = unit.getIncidentFocus().getIncidentId();
            return String.format("U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%d WORK=%d %n",
                                unitId,
                                type,
                                owner,
                                x,
                                y,
                                status,
                                incident,
                                countdown);
        }
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
            
            if (incidents[i] == null) {

                Incident tempIncident = new Incident(x, y, type, severity);
                incidents[i] = tempIncident;
                Incident.incrementReportedCounter();
                return tempIncident.getIncidentId();

            }
        }

        throw new CapacityExceededException("Far too many incidents");

    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {

        Incident incident = findIncidentFromGivenId(incidentId);

        if (incident == null) {
            throw new IDNotRecognisedException("No such incident exists");
        }

        if (incident.getIncidentStatus() == IncidentStatus.IN_PROGRESS || incident.getIncidentStatus() == IncidentStatus.RESOLVED) {
            throw new IllegalStateException("Cannot cancel an incident that is in progress or resolved");
        }

        incident.setIncidentStatus(IncidentStatus.CANCELLED);

        Units unit = findUnitFromGivenIncidentId(incidentId);

        if (unit == null) {
            throw new IDNotRecognisedException("No unit is focused on incident");
        }

        unit.clearIncidentFocus();
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // ffs use comments
        Incident incident = findIncidentFromGivenId(incidentId);

        if (incident == null) {
            throw new IDNotRecognisedException("No such incident exists");
        }
        // get incident
        // if incident is Resolved or Cancelled: throw
        if (incident.getIncidentStatus() == IncidentStatus.RESOLVED || incident.getIncidentStatus() == IncidentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot escelate an incident not currently in progress");
        }
        // if outside range: throw
        if (newSeverity < 1 || newSeverity > 5) {
            throw new InvalidSeverityException("Severity must be in the bounds of 1 to 5");
        }
        // Incident not defined(maybe you didn't uplead all the files)
        incident.setSeverity(newSeverity);
    }

    @Override
    public int[] getIncidentIds() {
        return createIncidentIdList(incidents);
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {

        Incident incident = findIncidentFromGivenId(incidentId);

        if (incident == null) {
            throw new IDNotRecognisedException("No such incident exists");
        }

        IncidentType incidentType = incident.getIncidentType();
        int severity = incident.getSeverity();
        int x = incident.getCoordinates()[0];
        int y = incident.getCoordinates()[1];
        IncidentStatus status = incident.getIncidentStatus();
        int owner = incident.getOwnerId();
        if (owner == -1) {
            return String.format("I#%d TYPE=%s SEV=%s LOC=(%d,%d) STATUS=%s UNIT=- %n",
                                incidentId,
                                incidentType,
                                severity,
                                x,
                                y,
                                status);
        } else {
        return String.format("I#%d TYPE=%s SEV=%s LOC=(%d,%d) STATUS=%s UNIT=%d %n",
                                incidentId,
                                incidentType,
                                severity,
                                x,
                                y,
                                status,
                                owner);
            }
        }

    @Override
    public void dispatch() { 
        // get incident list
        // for each incident in list assign a unit if:
        //      the incident is REPORTED
        //      if there's an availible unit(unit that had no "focus")
        // Overview: tie REPORTED incidents to IDLE units
        // tie breakers:
        //      work up the unit ID list
        //      shortest distance first
        //      lowest unit ID
        //      ??lowest ownerStation ID?? Aren't unit IDs unique?
        // ways to find ties:
        //  get max score and check if two or more share that high score.
        //      means two passes(one for highscore and another for find others)
        //      for each tie (if any) repeat for next quality

        // ===== make reported incident list =====
        int[] incidentIdList = createIncidentIdList(incidents);
        int noReportedIncidents = 0;
        for (int id : incidentIdList) {
            if (findIncidentFromGivenId(id).getIncidentStatus() == IncidentStatus.REPORTED) {
                noReportedIncidents++;
            }
        }

        Incident[] reportedIncidents = new Incident[noReportedIncidents];
        int index = 0;
        for (int i = 0; i < noReportedIncidents; i++) {
            if (findIncidentFromGivenId(incidentIdList[i]).getIncidentStatus() == IncidentStatus.REPORTED) {
                reportedIncidents[index] = findIncidentFromGivenId(incidentIdList[i]);
                index++;
            }
        }
        // the lists are in order of Id, I think. I can't really check
        for (Incident incident : reportedIncidents) {
            // null values will need to be dealt with and discounted
            // create list of eledgable units
            int noEligibleUnits = 0;
            int[] unitIds = createUnitIdList(units);

            Units[] tempUnitList = new Units[maxUnitAmount];

            index = 0;
            for (int unitId : unitIds) {
                Units unit = findUnitFromGivenId(unitId);

                // Basically, because the enums are ordered as they are, if the locations of the 
                // types are in the same position, they are considored compatible. This is a very long line.
                if (IncidentType.valueOf(incident.getIncidentType().toString()).ordinal() == UnitType.valueOf(unit.getUnitType().toString()).ordinal() && unit.getIncidentFocus() == null) {
                    noEligibleUnits++;
                    tempUnitList[index] = unit;
                    index++;
                }
            }

            Units[] eligibleUnits = new Units[noEligibleUnits];
            index = 0;
            for (Units unit : tempUnitList) {
                // making a clean list without null values
                if (unit != null) {
                    eligibleUnits[index] = unit;
                    index++;
                }
            }

            // from list of units, find closest one
            int[] incidentPos = incident.getCoordinates();
            int smallestDistance = (cityMap.getSize()[0]+1 + cityMap.getSize()[1]+1);
            // this placeholder is made to be one above the largest possible distance that can
            // be on the map. It's more efficient and rugged than a simple large number
            int sharedSmallestDistances = 0;
            // if there are no eligible units, the loop never initiates. 
            for (Units unit : eligibleUnits) {
                int[] unitPos = unit.getCoordinates();
                int distance = unit.getManDist(unitPos, incidentPos);

                if (distance == smallestDistance) {
                    // If there is a tie, it will be recorded
                    sharedSmallestDistances++;
                } else if (distance < smallestDistance) {
                    smallestDistance = distance;
                    // otherwise the record will be overwritten and tie counter will be reset
                    sharedSmallestDistances = 0;
                    sharedSmallestDistances++;
                }
            }

            if (sharedSmallestDistances > 0) {
                // Will get the first unit that has the smallest distance.
                // The list is in Id order so it should manage the first and
                // second tie-breaker at once.
                for (Units unit : eligibleUnits) {
                    int[] unitPos = unit.getCoordinates();
                    if (unit.getManDist(unitPos, incidentPos) == smallestDistance) {
                    // I made it so that matching an incident to a unit also changed the statuses of both objects
                    // to EN_ROUTE or DISPATCHED
                        incident.setOwner(unit.getUnitId());
                        unit.setIncidentFocus(incident);
                        break;
                    }
                }
            }
            // there are no availible units if we get here and we move to the next incident.
        }
        // UnitIds are unique, why would we need a redundant 3rd step for the station IDs too?
    }

    @Override
    public void tick() {
        // update ticks
        ticks++;

        // move EN_ROUTE units (ascending order)
        // mark arrived units
        int[] unitIdList = createUnitIdList(units);
        for (int unit : unitIdList) {
            Units unitObj = findUnitFromGivenId(unit);
            if (unitObj.getUnitStatus() == UnitStatus.EN_ROUTE) {
                int[] focusCoords = unitObj.getIncidentFocus().getCoordinates();
                unitObj.move(focusCoords, cityMap);
            }

            // process on scene work
            if (unitObj.getUnitStatus() == UnitStatus.AT_SCENE) {
                unitObj.updateCountdown();

            }
        }
        //resolve completed incidents (ascending order)
        int[] incidentIdList = createIncidentIdList(incidents);
        for (int incident : incidentIdList) {
            Incident incidentObj = findIncidentFromGivenId(incident);
            if (incidentObj.getOwnerId() == -1) {
                incidentObj.setIncidentStatus(IncidentStatus.RESOLVED);
            }
        }

        // Should we be making functions to create these sorted Id lists through methods
        // may make some methods shorter.
    }

    @Override
    public String getStatus() {
        int noTicks = this.ticks;
        int noStations = Station.getNumberOfStations();
        int noUnits = Units.getNumberOfUnits();
        int noObstacles = cityMap.getNumberOfObstacles();
        int noIncidents = Incident.getNumberOfIncidents();

        int[] unitIdList = createUnitIdList(units);
        int[] incidentIdList = createIncidentIdList(incidents);

        /* TICK=(noTicks) \n
         * STATIONS=(noStations) UNITS=(noUnits) INCIDENTS=(noIncidents) OBSTACLES=(noObstacles) \n
         * INCIDENTS \n
         * I#(id1) TYPE=(getType) SEV=(getSeverity) LOC(getLocation) STATUS=(getStatus) UNIT=(getOwnerUnitId) \n
         * I#(id2) TYPE=(getType) SEV=(getSeverity) LOC(getLocation) STATUS=(getStatus) UNIT=(getOwnerUnitId) \n
         * ...
         * UNITS
         * U#(id1) TYPE=(getType) HOME=(getOwnerStation) LOC(getLocation) STATUS=(getStatus) INCIDENT(getFocus) WORK=(getIncidentCountdown)
         * U#(id2) TYPE=(getType) HOME=(getOwnerStation) LOC(getLocation) STATUS=(getStatus) INCIDENT(getFocus) WORK=()
         * ...
        */

        String baseString = String.format("TICK=%d %nSTATIONS=%d UNITS=%d INCIDENTS=%d OBSTACLES=%d%n",
                                        noTicks,
                                        noStations,
                                        noUnits,
                                        noIncidents,
                                        noObstacles);

        baseString = baseString.concat("INCIDENTS\n");

        for (int incident : incidentIdList) {
            try {
                baseString = baseString.concat(viewIncident(incident));
            } 
            catch(IDNotRecognisedException e) {
                // I'm doing this to stop the debugger from moaning
            }
        }

        baseString = baseString.concat("UNITS\n");

        for (int unit : unitIdList) {
            try {
                baseString = baseString.concat(viewUnit(unit));
            } 
            catch(IDNotRecognisedException e) {
                // I'm doing this to stop the debugger from moaning
            }
        }
        
        return baseString;
    }
}
// about 1500 lines of code in all btw

