package cityrescue;

import java.util.Arrays;

import cityrescue.enums.*;

/**
* Class for the Units object
* 
* @author (Dylan Foster)
* @version (0.0)
*/
abstract class Units {

    // abstract means no constructor, just values and methods for subclasses
    // constant declarations, if any
    protected int coordX;
    protected int coordY;
    protected static int unitIdCounter = 0; // The ID is tied to each unit and not each type so the incrementer is put here.
    protected int unitId;
    protected UnitType unitType;
    protected UnitStatus state = UnitStatus.IDLE;
    protected int ownerStationId;
    protected int incidentCountdown = -1;
    private static int numberOfUnits = 0;
    // -1 for no incident
    private Incident currentIncidentFocus = null;
    // method signatures

    public int[] getCoordinates() 
    {
        int[] dimensions = new int[2];
        dimensions[0] = this.coordX;
        dimensions[1] = this.coordY;
        return dimensions;
    }

    public void updateCountdown() 
    {
        this.incidentCountdown -= 1;
        if (this.incidentCountdown == 0) {
            getIncidentFocus().clearOwner();
            clearIncidentFocus();
        }
    }

    public int getIncidentCountdown() 
    {
        return this.incidentCountdown;
    }

    public void setCountdown(IncidentType type)
    {
        switch (type) {
            case MEDICAL:
                this.incidentCountdown = 2 ;
                break;
            case FIRE:
                this.incidentCountdown = 4;
                break;
            case CRIME:
                this.incidentCountdown = 3;
                break;
            default:
                break;
        }
        
    }

    public int getManDist(int[] currentLocation, int[] nextLocation)
    {
        return (Math.abs(currentLocation[0] - nextLocation[0]) + Math.abs(currentLocation[1] - nextLocation[1]));
    }

    public void move(int[] destination, CityMap map) 
    {
        if (getUnitStatus() == UnitStatus.OUT_OF_SERVICE || getUnitStatus() == UnitStatus.IDLE || currentIncidentFocus == null) {
            return;
        }

        // north then east then south then west
        int xMod = getCoordinates()[0];
        int yMod = getCoordinates()[1];

        int[][] nextPosList = {{xMod, yMod+1}, {xMod+1, yMod}, {xMod, yMod-1}, {xMod-1, yMod}};

        int[] placeholderMove = {2*map.getSize()[0]+1, 2*map.getSize()[1]+1};
        int[] chosenMove = placeholderMove;

        for (int[] move : nextPosList) {
            if (map.isInBounds(move[0], move[1]) && map.isCellClear(move[0],move[1]) && (getManDist(move, destination) < getManDist(chosenMove, destination) || chosenMove == placeholderMove)) {
                chosenMove = move;
            }
        }

        if (chosenMove != placeholderMove) {
            setCoords(chosenMove[0], chosenMove[1]);    
        } else { 
            //If false, the unit is likely completely blocked 
            setCoords(getCoordinates()[0], getCoordinates()[1]);
        }

        if (Arrays.equals(getCoordinates(), destination)) {
            setUnitStatus(UnitStatus.AT_SCENE);
            getIncidentFocus().setIncidentStatus(IncidentStatus.IN_PROGRESS);
            setCountdown(getIncidentFocus().getIncidentType());
        }
        
    }

    public Incident getIncidentFocus() 
    {
        return this.currentIncidentFocus;
    }

    public void setIncidentFocus(Incident incident)
    {
        this.currentIncidentFocus = incident;
        setUnitStatus(UnitStatus.EN_ROUTE);
    }

    public void clearIncidentFocus()
    {
        this.currentIncidentFocus = null;
        this.incidentCountdown = -1;
        setUnitStatus(UnitStatus.IDLE);
    }

    public void setCoords(int x, int y)
    {
        this.coordX = x;
        this.coordY = y;
    }

    public void setOwner(int ownerId)
    {
        this.ownerStationId = ownerId;
    }

    public int getOwnerId()
    {
        return ownerStationId;
    }

    public UnitStatus getUnitStatus()
    {
        return this.state;
    }

    public void setUnitStatus(UnitStatus status)
    {
        this.state = status;
    }

    public UnitType getUnitType() 
    {
        return this.unitType;
    }

    protected static int createNewId() 
    {
        unitIdCounter += 1;
        return unitIdCounter;
    }

    public int getUnitId()
    {
        return unitId;
    }

    public static int getNumberOfUnits()
    {
        return numberOfUnits;
    }

    protected static void incrementNumberOfUnits()
    {
        numberOfUnits++;
    }

    protected static void decrementNumberOfUnits()
    {
        numberOfUnits--;
    }
}
