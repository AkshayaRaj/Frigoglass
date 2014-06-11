/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

import java.util.LinkedList;

/**
 *
 * @author Akshaya
 */
public class CamperContainer {
    Camper camper[];
    private int count;
    LinkedList camperLL;
    CamperContainer(Camper camper[]){
        this.camper=new Camper[camper.length];
        this.camper=camper;
    }
    CamperContainer(){
        
    }

    public LinkedList getCamperLL() {
        return camperLL;
    }

    public void setCamperLL(LinkedList camperLL) {
        this.camperLL = camperLL;
    }
    
   Camper[] getCamper(){
        return camper;
    }
   int getCount(){
       return count;
   }
   
   void setCount(int count){
       this.count=count;
   }
}
