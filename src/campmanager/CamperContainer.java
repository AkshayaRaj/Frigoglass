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
public class CamperContainer {
    Camper camper[];
    private int count;
    CamperContainer(Camper camper[]){
        this.camper=new Camper[camper.length];
        this.camper=camper;
    }
    CamperContainer(){
        
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
