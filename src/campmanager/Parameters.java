/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Akshaya
 */
public class Parameters {

   
    int maxRooms;
    int max_records;
    public Room [] rooms;
    private int roomCount;
    private LinkedList roomLL;
    
    
    
     public int getMaxRooms() {
        return maxRooms;
    }

    public void setMaxRooms(int maxRooms) {
        this.maxRooms = maxRooms;
    }

    public int getMax_records() {
        return max_records;
    }

    public void setMax_records(int max_records) {
        this.max_records = max_records;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = new Room[rooms.length];
        this.rooms=rooms;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public LinkedList getRoomLL() {
        return roomLL;
    }

    public void setRoomLL(LinkedList roomLL) {
        this.roomLL = roomLL;
    }

   
    
    
    Parameters(){
        
    }
    
    
}

