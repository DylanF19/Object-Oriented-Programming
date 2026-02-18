package cityrescue;

import java.nio.file.attribute.FileTime;

import cityrescue.enums.UnitType;

public class FireEngine extends Units {
    // constants and variable declarations
    private static final UnitType unitType = UnitType.FIRE_ENGINE;
    public final int unitId;
    
    FireEngine() 
    {
        unitId = createNewId();
        super.incrementNumberOfUnits();
    }

}