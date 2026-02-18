package cityrescue;

import cityrescue.enums.UnitType;

public class PoliceCar extends Units {
    // constants and variable declarations
    private static final UnitType unitType = UnitType.POLICE_CAR;
    private final int unitId;
    
    PoliceCar() 
    {
        unitId = createNewId();
        super.incrementNumberOfUnits();
    }

}