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
    private IncidentType type;
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

    public int getIncidentId()
    {
        return incidentId;
    }

    private static void incrementNumberOfIncidents()
    {
        numberOfIncidents++;
    }
}
