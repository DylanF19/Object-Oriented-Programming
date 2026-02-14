package main.java.cityrescue;

/**
 * Class for the Units object
 * 
 * @author (Dylan Foster)
 * @version (0.0)
 */
abstract class Units {
   // constant declarations, if any
    int coordX;
    int coordY;


   // method signatures
   public int[] getCoordinates() 
   {
        int[] dimensions = new int[1];
        dimensions[0] = this.coordX;
        dimensions[1] = this.coordY;
        return dimensions;
   }
   // An enum with values RIGHT, LEFT
}
