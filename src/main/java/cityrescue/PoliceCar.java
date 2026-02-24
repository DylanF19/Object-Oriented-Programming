package cityrescue;

import cityrescue.enums.UnitType;

public class PoliceCar extends Units {
    // constants and variable declarations

    PoliceCar() 
    {
        this.unitId = createNewId();
        incrementNumberOfUnits();
        this.unitType = UnitType.POLICE_CAR;
    }


}