/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Akshaya
 */
public class GetStats {
    LinkedList camperLL;
    LinkedList roomLL;
    
    public GetStats(LinkedList camperLL, LinkedList roomLL){
        this.camperLL=camperLL;
        this.roomLL=roomLL;
        
    }
    
    LinkedList getAvailableRooms(){        
        ListIterator itr_room=roomLL.listIterator();
        LinkedList<Room> available_rooms=new LinkedList<Room>();
        
        Room room=new Room();
        while(itr_room.hasNext()){
            room=(Room)itr_room.next();
            if(!room.isFull()){
                available_rooms.add(room);
            }
            
        }
        
        return available_rooms;
    }

  
    public LinkedList getCamperLL() {
        return camperLL;
    }

    public void setCamperLL(LinkedList camperLL) {
        this.camperLL = camperLL;
    }
    
    
    
}
