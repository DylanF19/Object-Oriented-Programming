package cityrescue;

import cityrescue.exceptions.CapacityExceededException;

/**
 * Class for the Station object
 * 
 * @author (Dylan Foster)
 * @version (0.0)
 */
public class Station
{
    // instance variables - replace the example below with your own
    private int stationId;
    private String name;
    private int coordX;
    private int coordY;
    private int parkingCapacity = 15; //This int is abitrary
    
    private int numberOfOwnedUnits = 0;
    private Units[] ownedUnits = new Units[parkingCapacity];

    private static int numberOfStations = 0;
    
    public Station(String name, int x, int y)
    {
        // initialise instance variables
        this.coordX = x;
        this.coordY = y;
        this.name = name;
        
        // increment ID number
        numberOfStations++;
        this.stationId = numberOfStations;
        
    }
    
    public Units[] getOwnedUnitList() 
    {
        return ownedUnits;
    }

    public void removeUnit(int unitId) 
    {
        for (int i = 0; i < ownedUnits.length; i++) {
            if (ownedUnits[i].getUnitId() == unitId) {
                Units.decrementNumberOfUnits(); 
                this.numberOfOwnedUnits--;
                ownedUnits[i] = null;
                // This is so amaturish and the result of having each station store the 
                // Unit references. Maybe there's a different method to recording the
                // units and number there of in seperate threads.
            }
        }
    }

    public void addUnit(Units unit) throws CapacityExceededException
    // I would love to know an alternative to conditionals
    // I don't think we can call the object from the type directly.

    // Update: I've decided to create the object first then add it to the list
    //      to make array manipulation easier
    {
        unit.setCoords(this.coordX, this.coordY);
        unit.setOwner(this.stationId);

        int emptySpaces = 0;
        for (Units ownedUnit : ownedUnits) {
            if (ownedUnit == null || !ownedUnit.toString().isEmpty()) {
                emptySpaces++;
            }
        }

        if (emptySpaces == 0) {
            throw new CapacityExceededException("Not enough capacity to add another unit");
        }

        for (int i = 0; i < ownedUnits.length; i++) {
            if (ownedUnits[i] == null || !ownedUnits[i].toString().isEmpty()) {
                this.numberOfOwnedUnits++;
                ownedUnits[i] = unit;
                return;
            }
        }
    }

    public int getParkingSpace() 
    {
        return this.parkingCapacity;
    }

    public int[] getCoords()
    {
        int[] coordinates = new int[2];
        coordinates[0] = this.coordX;
        coordinates[1] = this.coordY;
        return coordinates;
    }

    public int getStationId() 
    {
        return this.stationId;
    }

    public static int getNumberOfStations()
    {
        // so that individual objects can be referenced independently
        // through methods.
        return numberOfStations;
    }
    
    public int getNumberOfOwnedUnits() 
    {
        return this.numberOfOwnedUnits;
    }
    
    public void setMaxCapacity(int maxUnits) 
    {
        this.parkingCapacity = maxUnits;
    }

    public String getName()
    {
        return this.name;
    }
}






