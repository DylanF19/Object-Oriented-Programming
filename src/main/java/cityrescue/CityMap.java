package cityrescue;

public class CityMap
{
    // instance variables - replace the example below with your own
    private int[][] map;
    private int width;
    private int height;
    
    
    /**
     * Constructor for objects of class CityMap
     */
    public CityMap(int width, int height) 
    {
        this.width = width;
        this.height = height;
        map = new int[width][height];
        
        for (int i = 0; i < width; i++) {

            for (int j = 0; j < height; j++) {
                // 0 is clear, 1 is blocked
                map[i][j] = 0;
                
            }
        }
    }
    
    public int getNumberOfObstacles() 
    {
        int noObstacles = 0;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (map[j][i] == 1) {
                    noObstacles++;
                }
            }
        }

        return noObstacles;
    }

    public int[] getSize() 
    {
        int[] dimensions = new int[2];
        dimensions[0] = this.width;
        dimensions[1] = this.height;
        return dimensions;
    }
    
    public boolean isInBounds(int x, int y) 
    {
        return !(x < 0 || x >= this.getSize()[0] || y < 0 || y >= this.getSize()[1]);
    }

    public boolean isCellClear(int x, int y) 
    {
        return (map[x][y] == 0);
    }
    
    public boolean isCellObstructed(int x, int y) 
    {
        return (map[x][y] == 1);
    }

    public void addObstacle(int x, int y) 
    {
        // 1 for a blocked cell
        this.map[x][y] = 1;
    }

    public void removeObstacle(int x, int y) 
    {
        // 1 for a blocked cell
        this.map[x][y] = 0;
    }
}
