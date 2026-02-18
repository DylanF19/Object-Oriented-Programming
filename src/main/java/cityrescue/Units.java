package cityrescue;

import java.lang.Thread.State;

import cityrescue.enums.*;

/**
 * Class for the Units object
 * 
 * @author (Dylan Foster)
 * @version (0.0)
 */
abstract class Units {
   // abstract means no constructor, just values and methods for subclasses
   // constant declarations, if any
   protected int coordX;
   protected int coordY;
   private int unitId = 1; // The ID is tied to each unit and not each type so the incrementer is put here.
   protected UnitType unitType;
   protected UnitStatus state = UnitStatus.IDLE;
   protected int ownerStationId;

   // method signatures
   public int[] getCoordinates() 
   {
        int[] dimensions = new int[1];
        dimensions[0] = this.coordX;
        dimensions[1] = this.coordY;
        return dimensions;
   }

   public void setCoords(int x, int y)
   {
      this.coordX = x;
      this.coordY = y;
   }

   public void setOwner(int ownerId)
   {
      this.ownerStationId = ownerId;
   }

   public int getOwnerId()
   {
      return ownerStationId;
   }

   public UnitStatus getUnitStatus()
   {
      return this.state;
   }

   public void setUnitStatus(UnitStatus status)
   {
      this.state = status;
   }

   public UnitType getUnitType() 
   {
      return this.unitType;
   }

   protected int createNewId() 
   {
      unitId += 1;
      return unitId;
   }

   public int getUnitId()
   {
      return this.unitId;
   }
   // An enum with values RIGHT, LEFT
}
