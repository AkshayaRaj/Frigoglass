/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Akshaya
 */
public class Parameters {

    public int getMaxRooms() {
        return maxRooms;
    }

    public void setMaxRooms(int maxRooms) {
        this.maxRooms = maxRooms;
    }
    int maxRooms;
    int count;
    Map room_costs;
    
    Parameters(){
        
    }
    
    
}
