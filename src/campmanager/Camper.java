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
    private int hash;
    private boolean bedding;
    private String religion;
    private String grade;
    
    Camper() {
       
    }
    public enum location {JuniorNE,SeniorNE,LabourNE,LabourS,JuniorS};
    public static String location[]=new String []{"Junior-NE","Senior-NE","Labour-NE","Labour-S","Junior-S"};
    public boolean newEmployee;
    location loc;
    String camp_location;
    public Room room;

    public Room getRoom() {
        return room;
    }

    public boolean isBedding() {
        return bedding;
    }

    public void setBedding(boolean bedding) {
        this.bedding = bedding;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isNewEmployee() {
        return newEmployee;
    }

    public void setNewEmployee(boolean newEmployee) {
        this.newEmployee = newEmployee;
    }

    public String getCamp_location() {
        return camp_location;
    }

    public void setCamp_location(String camp_location) {
        this.camp_location = camp_location;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
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
    public String getCampLoc(){
        return camp_location;
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

