package cityrescue;

import cityrescue.enums.UnitType;

public class Ambulance extends Units {
    // constants and variable declarations
    private UnitType unitType = UnitType.AMBULANCE;
    public final int unitId;


    Ambulance() 
    {
    // to identify each unti, it's own and owner id could be used to keep
    // track of them.
        unitId = createNewId();
    }
}
