package cityrescue;

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
    private int unitId = 1; // The ID is tied to each unit and not each type so the incrementer is put here.
    protected UnitType unitType;
    protected UnitStatus state = UnitStatus.IDLE;
    protected int ownerStationId;
    protected int incidentCountdown = -1;
    private static int numberOfUnits = 0;
    // -1 for no incident
    private int currentIncidentFocus = -1;
    // method signatures
    public int[] getCoordinates() 
    {
        int[] dimensions = new int[2];
        dimensions[0] = this.coordX;
        dimensions[1] = this.coordY;
        return dimensions;
    }

    public void updateCountdown(Incident incident) 
    {
        this.incidentCountdown--;
        if (this.incidentCountdown == 0) {
            incident.setOwner(-1);
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
                this.incidentCountdown = 2;
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
        if (getUnitStatus() != UnitStatus.EN_ROUTE || currentIncidentFocus == -1) {
            return;
        }
        // north then east then south then west
        int xMod = getCoordinates()[0];
        int yMod = getCoordinates()[1];

        int[][] nextPosList = {{xMod, yMod+1}, {xMod+1, yMod}, {xMod, yMod-1}, {xMod-1, yMod}};

        int[] chosenMove = {-1, -1};

        for (int i = 0; i < nextPosList.length; i++) {
            // if found a move that closes the distance more and is valid
            if (map.isCellClear(nextPosList[i][0], nextPosList[i][1]) && getManDist(getCoordinates(), destination) < getManDist(chosenMove, destination)) {

                chosenMove = nextPosList[i];
                setCoords(chosenMove[0], chosenMove[1]);
                break;
                
            }
        }

        if (chosenMove[0] == -1) {
            for (int i = 0; i < nextPosList.length; i++) {
            // if found a move that closes the distance more and is valid
                if (map.isCellClear(nextPosList[i][0], nextPosList[i][1])) {

                    chosenMove = nextPosList[i];
                    setCoords(chosenMove[0], chosenMove[1]);
                    break;
                }
            }
        }

        if (chosenMove[0] == -1) { //If false, the unit is likely completely blocked 
            setCoords(getCoordinates()[0], getCoordinates()[1]);
        }

        if (getCoordinates() == destination) {
            setUnitStatus(UnitStatus.AT_SCENE);
        }
        
    }

    public int getIncidentFocus() 
    {
        return this.currentIncidentFocus;
    }

    public void setIncidentFocus(int incidentId)
    {
        this.currentIncidentFocus = incidentId;
        setUnitStatus(UnitStatus.EN_ROUTE);
    }

    public void clearIncidentFocus()
    {
        this.currentIncidentFocus = -1;
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

    protected int createNewId() 
    {
        unitId += 1;
        return unitId;
    }

    public int getUnitId()
    {
        return this.unitId;
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
