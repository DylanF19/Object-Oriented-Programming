package java.cityrescue;

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
    public int stationId;
    public String name;
    public int coordX;
    public int coordY;
    public int parkingCapacity;
    // A cheap easy way to get the IDs they want.
    // It never goes down so the name isn't that accurate
    // Shame I couldn't care less.
    public static int numberOfStations = 0;
    
    public HashMap<Integer, List<Station>> hashMap = new HashMap<Integer, List<Station>>();
    /**
     * Constructor for objects of class Station
     * Params of Station:
     *      x coordinate
     *      y coordinate
     *      Station name
     *      Station ID
     *      Station Parking Capacity
     */
    public Station(String name, int x, int y)
    {
        // initialise instance variables
        this.coordX = x;
        this.coordY = y;
        this.name = name;
        this.parkingCapacity = 2; //This int is abitrary
        // increment ID number
        this.stationId = ++numberOfStations;
    }
    
    public void createStation(String name, int x, int y)
    {
        //TODO; find a way to group Station objects together
        // so that individual objects can be referenced independently
        // through methods.
    }
}






