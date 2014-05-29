/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

/**
 *
 * @author Akshaya
 */
public class Room {
        private String bld_no;
        private String room_no;
        int capacity;
        int cost;
        

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
        
        Room(String bld_no,String room_no)  {
            this.bld_no=bld_no;
            this.room_no=room_no;
            
}
        
       
}
