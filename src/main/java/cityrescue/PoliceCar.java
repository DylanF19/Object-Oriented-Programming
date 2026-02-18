package cityrescue;

import cityrescue.enums.UnitType;

public class PoliceCar extends Units {
    // constants and variable declarations
    private UnitType unitType = UnitType.POLICE_CAR;
    public final int unitId;
    
    PoliceCar() 
    {
    // to identify each unti, it's own and owner id could be used to keep
    // track of them.
        unitId = createNewId();
    }

}