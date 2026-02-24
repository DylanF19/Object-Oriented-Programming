package cityrescue;

import cityrescue.enums.UnitType;

public class FireEngine extends Units {
    // constants and variable declarations
    
    FireEngine() 
    {
        this.unitId = createNewId();
        incrementNumberOfUnits();
        this.unitType = UnitType.FIRE_ENGINE;
    }

}