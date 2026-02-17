package cityrescue;

import cityrescue.enums.UnitType;

/**
 * Class for the Units object
 * 
 * @author (Dylan Foster)
 * @version (0.0)
 */
abstract class Units {
   // constant declarations, if any
   private int coordX;
   private int coordY;
   private UnitType unitType;

   // method signatures
   public int[] getCoordinates() 
   {
        int[] dimensions = new int[1];
        dimensions[0] = this.coordX;
        dimensions[1] = this.coordY;
        return dimensions;
   }

   public UnitType getUnitType() 
   {
      return this.unitType;
   }
   // An enum with values RIGHT, LEFT
}
