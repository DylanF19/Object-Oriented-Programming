package cityrescue;

import cityrescue.enums.UnitType;

public class Ambulance extends Units {
    // constants and variable declarations
    private static final UnitType unitType = UnitType.AMBULANCE;
    public final int unitId;


    Ambulance() 
    {
        unitId = createNewId();
        super.incrementNumberOfUnits();
    }
}
