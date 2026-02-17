package cityrescue;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class for the Station object
 * 
 * @author (Dylan Foster)
 * @version (0.0)
 */
public class Station
{
    // instance variables - replace the example below with your own
    private int stationId;
    private String name;
    private int coordX;
    private int coordY;
    private int parkingCapacity = 2; //This int is abitrary
    private int numberOfOwnedUnits = 0;
    
    private static int numberOfStations = 0;
    
    /**
     * Constructor for objects of class Station
     * Params of Station:
     *      x coordinate
     *      y coordinate
     *      Station ID
     *      Station Parking Capacity
     */
    public Station(String name, int x, int y)
    {
        // initialise instance variables
        this.coordX = x;
        this.coordY = y;
        this.name = name;
        
        // increment ID number
        this.stationId = numberOfStations;
        numberOfStations++;
    }
    
    public int getId() 
    {
        return this.stationId;
    }

    public static int getNumberOfStations()
    {
        //TODO; find a way to group Station objects together
        // so that individual objects can be referenced independently
        // through methods.
        return numberOfStations;
    }
    
    public int getNumberOfOwnedUnits() 
    {
        return this.numberOfOwnedUnits;
    }
    
    public void setMaxCapacity(int maxUnits) 
    {
        this.parkingCapacity = maxUnits;
    }
}






