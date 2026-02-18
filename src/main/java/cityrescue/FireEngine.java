package cityrescue;

import java.nio.file.attribute.FileTime;

import cityrescue.enums.UnitType;

public class FireEngine extends Units {
    // constants and variable declarations
    private UnitType unitType = UnitType.FIRE_ENGINE;
    public final int unitId;
    
    FireEngine() 
    {
    // to identify each unti, it's own and owner id could be used to keep
    // track of them.
        unitId = createNewId();
    }

}