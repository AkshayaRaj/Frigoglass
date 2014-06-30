/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Akshaya
 */
public class Room {
        private String bld_no;
        private String room_no;
        int capacity;
        int cost;
        int occupancy;
        private LinkedList<Camper> campers_in_room=new LinkedList<Camper>();
        boolean full;
        private int camp;
        private int room_cost;
        
        public boolean checkIn(Camper camper){
            
            
            if(occupancy<capacity){
                campers_in_room.add(camper);
                occupancy++;
                if(occupancy>=capacity)
                    full=true;
                return true;
            }
            
            return false;
        }

    public int getCamp() {
        return camp;
    }

    public void setCamp(int camp) {
        this.camp = camp;
    }

    public int getRoom_cost() {
        return room_cost;
    }

    public void setRoom_cost(int room_cost) {
        this.room_cost = room_cost;
    }
       
    
        
        public void setCamperRoom(){
            ListIterator itr=campers_in_room.listIterator();
            Camper c=new Camper();
            while(itr.hasNext()){
                c=(Camper)itr.next();
                c.setRoom(this);
                campers_in_room.set(itr.nextIndex()-1, c);
            }
        }
        
        public void clear(){
            campers_in_room.clear();
            occupancy=0;
            full=false;
            
        }
        public void edit(Camper camper){
            ListIterator itr=campers_in_room.listIterator();
            Camper c=new Camper();
            boolean found=false;
            while(itr.hasNext()){
                c=(Camper)itr.next();
                if(c.getHash()==camper.getHash()){
                    found=true;
                    break;
                }
            }
            if(found){
                
                
            }
            
        }
        public int containsNationality(String nation){
            ListIterator itr=campers_in_room.listIterator();
            int number=0;
            Camper camper_here=new Camper();
            while(itr.hasNext()){
                camper_here=(Camper)itr.next();
                if(camper_here.getNationality().equals(nation)){
                    number++;
                }
            }
            return number;        
        }
        
        
        
        
        public boolean checkOut(Camper camper){
            if(occupancy>0){
                Camper camper_local=new Camper();
            ListIterator itr=campers_in_room.listIterator();
            while(itr.hasNext()){
                camper_local=(Camper)itr.next();
                if(camper_local.getName().equals(camper.getName())){
                    System.out.println("yo");
                    campers_in_room.remove(itr.nextIndex()-1);
                }
            }
              //  System.out.println(campers_in_room.remove(camper));
                System.out.println(campers_in_room);
                occupancy--;
                if(occupancy<capacity)
                    full=false;
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return this.room_no.toString();
        }
        
        
        
    public boolean isFull() {
        return full;
    }

    public boolean equals(Room roomToCompare){
        if(this.getRoom_no().equals(roomToCompare.getRoom_no()) && this.getBld_no().equals(roomToCompare.getBld_no()))
            return true;
        return false;
    }
    public void setFull(boolean full) {
        this.full = full;
    }
  
        public LinkedList<Camper> getCampers_in_room(){
            return campers_in_room;
        }
        
        
    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }
        
        

    public String getBld_no() {
        return bld_no;
    }

    public void setBld_no(String bld_no) {
        this.bld_no = bld_no;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        if(this.occupancy>=this.capacity)
            this.full=true;
        else 
            this.full=false;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
        
    
        Room(String bld_no,String room_no)  {
            this.bld_no=bld_no;
            this.room_no=room_no;
            this.occupancy=0;
            this.full=false;
            
}
        Room(){
            
        }
        
       
}
