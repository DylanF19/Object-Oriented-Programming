package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

/**
 * Class for the Incident object
 * 
 * @author (Dylan Foster)
 * @version (0.0)
 */
public class Incident {
    
    private static int incidentId = 1;
    private final IncidentType type;
    private IncidentStatus status;
    private int severity;
    private int coordX;
    private int coordY;

    private static int numberOfIncidents = 0;

    private int ownerUnitId;

    Incident(int x, int y, IncidentType type, int severity)
    {
        this.coordX = x;
        this.coordY = y;
        this.type = type;
        this.severity = severity;
        this.status = IncidentStatus.REPORTED;

        incidentId++;
        incrementNumberOfIncidents();
    }

    public IncidentType getIncidentType() 
    {
        return this.type;
    }

    public int[] getCoordinates()
    {
        int[] dimensions = new int[1];
        dimensions[0] = this.coordX;
        dimensions[1] = this.coordY;
        return dimensions;
    }

    public void setIncidentStatus(IncidentStatus status)
    {
        this.status = status;
    }

    public IncidentStatus getIncidentStatus() 
    {
        return this.status;
    }

    public int getIncidentId()
    {
        return incidentId;
    }

    public void setOwner(int ownerId)
    {
      this.ownerUnitId = ownerId;
    }

    public int getOwnerId()
    {
      return ownerUnitId;
    }

    private static void incrementNumberOfIncidents()
    {
        numberOfIncidents++;
    }
}
