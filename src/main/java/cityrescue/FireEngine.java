package cityrescue;

import java.nio.file.attribute.FileTime;

import cityrescue.enums.UnitType;

public class FireEngine extends Units {
    // constants and variable declarations
    private UnitType unitType = UnitType.FIRE_ENGINE;
    private int coordX;
    private int coordY;
    private int stationOwnership;
    private int unitId;
    
    FireEngine(int x, int y, int ownerId) 
    {
    // to identify each unti, it's own and owner id could be used to keep
    // track of them.
        this.coordX = x;
        this.coordY = y;
        this.stationOwnership = ownerId;
        unitId = createNewId();
    }

}