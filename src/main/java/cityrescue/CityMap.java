package java.cityrescue;


/**
 * Write a description of class CityMap here.
 *
 * @author (Dylan Foster)
 * @version (08/02/2026)
 */
public class CityMap
{
    // instance variables - replace the example below with your own
    private int[][] Map;
    private int width;
    private int height;
    
    
    /**
     * Constructor for objects of class CityMap
     */
    public CityMap(int width, int height) 
    {
        this.width = width;
        this.height = height;
        Map = new int[width][height];
        
        for (int i = 0; i < width; i++) {

            for (int j = 0; j < height; j++) {
                // 0 is clear, 1 is blocked
                Map[i][j] = 0;
                
            }
        }
    }
    
    public int[] getSize() 
    {
        int[] dimensions = new int[1];
        dimensions[0] = this.width;
        dimensions[1] = this.height;
        return dimensions;
    }
    
    public boolean isCellClear(int x, int y) 
    {
        if (Map[x][y] == 0) {
            return true;
        } else {
            return false;
        }
    }
    
        public boolean isCellObstructed(int x, int y) 
    {
        if (Map[x][y] == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * addObstacle Method
     * 
     * - I don't know if the cell properties of the map should be
     * - altered here. 
     */
    public void addObstacle(int x, int y) 
    {
        // 1 for a blocked cell
        this.Map[x][y] = 1;
    }
}