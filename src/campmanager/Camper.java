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

class Phone{

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    int area;
     int number;
    Phone(int area,int number){
        this.area=area;
        this.number=number;
    }
    Phone(){
        
    }
};

public class Camper {
    
    
    public String name;
    public  String cec_no;
    public String nationality;
    public  Phone phone_no;

    Camper() {
       
    }
    public enum location {JuniorNE,SeniorNE,LabourNE,LabourS,JuniorS};
    public static String location[]=new String []{"JuniorNE","SeniorNE","LabourNE","LabourS","JuniorS"};
    public boolean newEmployee;
    location loc;
    String camp_location;
    public Room room;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCec_no(String cec_no) {
        this.cec_no = cec_no;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setPhone_no(int area,int number) {
        this.phone_no = phone_no;
    }

    public void setLoc(location loc) {
        this.loc = loc;
    }

    public String getName() {
        return name;
    }

    public String getCec_no() {
        return cec_no;
    }

    public String getNationality() {
        return nationality;
    }

    public Phone getPhone_no() {
        return phone_no;
    }

    public location getLoc() {
        return loc;
    }
    
    public void display(){
        System.out.println("Name: "+name);
        System.out.println("CEC: "+cec_no);
        System.out.println("Nationality: "+nationality);
        System.out.println("Phone: "+phone_no.getArea()+"-"+phone_no.getNumber());
        System.out.println("Camp Location: "+camp_location);
     //   System.out.println("Room Number: "+room.getRoom_no());
    //    System.out.println("Building Number: "+room.getBld_no());
    }
    
    Camper(String name,String cec_no,String nationality,Phone phone_no,location loc){
        this.name=name;
        this.cec_no=cec_no;
        this.nationality=nationality;
        this.phone_no=phone_no;
        this.loc=loc;
                       
    }
}

