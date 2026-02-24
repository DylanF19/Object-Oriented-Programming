package cityrescue;

import cityrescue.enums.UnitType;

public class Ambulance extends Units {
    // constants and variable declarations

    Ambulance() 
    {
        this.unitId = createNewId();
        incrementNumberOfUnits();
        this.unitType = UnitType.AMBULANCE;
    }
}
