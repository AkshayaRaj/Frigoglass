/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Akshaya
 */
public class EditCamper extends javax.swing.JDialog {

    
     Camper camper;
     boolean added;
     public LinkedList roomsLL;
     public LinkedList campersLL;
    public String[] nationality;
    public LinkedList available_rooms;
    Vector<String> v ;
    Locale[] locales;
    boolean transferred;
    String from_building,from_room,from_camp;
   
    /**
     * Creates new form NewCamper
     */
    public EditCamper(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        added=false; 
        camper=new Camper();
        jComboBox_building.setEnabled(false);
        jComboBox_room.setEnabled(false);
        jComboBox_camp.setEnabled(false);
        jTextField_phone_area.setUI(new JTextFieldHintUI("Area",Color.gray));
        jTextField_phone_number.setUI(new JTextFieldHintUI("Number",Color.gray));
        jTextField_other_nationality.setEditable(false);
        transferred=false;
        v = new Vector<String>();
         locales = Locale.getAvailableLocales();
            for (Locale locale : locales) {
                 //String iso = locale.getISO3Country();
                 //String code = locale.getCountry();
                   String name = locale.getDisplayCountry();
                if(!v.contains(name)) v.addElement(name);
            }
            if(!v.contains("Pakistan")) v.addElement("Pakistan");
            if(!v.contains("Bangladesh")) v.addElement("Bangladesh");
            if(!v.contains("Sri Lanka")) v.addElement("Sri Lanka");
            if(!v.contains("Nepal")) v.addElement("Nepal");
        Collections.sort(v);
       jComboBox_nationality.setModel(new DefaultComboBoxModel(v));
        jTextField_other_nationality.setEditable(true);
        dateChooserCombo1.setEnabled(false);
        
    }

    public Camper getCamper() {
        return camper;
    }

    public void setCamper(Camper camper) {
        this.camper = camper;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public String[] getNationality() {
        return nationality;
    }

    public void setNationality(String[] nationality) {
        this.nationality = nationality;
    }

    public Vector<String> getV() {
        return v;
    }

    public void setV(Vector<String> v) {
        this.v = v;
    }

    public Locale[] getLocales() {
        return locales;
    }

    public void setLocales(Locale[] locales) {
        this.locales = locales;
    }

    public boolean isTransferred() {
        return transferred;
    }

    public void setTransferred(boolean transferred) {
        this.transferred = transferred;
    }

    public JButton getjButton_add() {
        return jButton_add;
    }

    public void setjButton_add(JButton jButton_add) {
        this.jButton_add = jButton_add;
    }

    public JCheckBox getjCheckBox_bedding() {
        return jCheckBox_bedding;
    }

    public void setjCheckBox_bedding(JCheckBox jCheckBox_bedding) {
        this.jCheckBox_bedding = jCheckBox_bedding;
    }

    public JCheckBox getjCheckBox_new_employee() {
        return jCheckBox_new_employee;
    }

    public void setjCheckBox_new_employee(JCheckBox jCheckBox_new_employee) {
        this.jCheckBox_new_employee = jCheckBox_new_employee;
    }

    public JCheckBox getjCheckBox_transfer() {
        return jCheckBox_transfer;
    }

    public void setjCheckBox_transfer(JCheckBox jCheckBox_transfer) {
        this.jCheckBox_transfer = jCheckBox_transfer;
    }

    public JComboBox getjComboBox_building() {
        return jComboBox_building;
    }

    public void setjComboBox_building(JComboBox jComboBox_building) {
        this.jComboBox_building = jComboBox_building;
    }

    public JComboBox getjComboBox_camp() {
        return jComboBox_camp;
    }

    public void setjComboBox_camp(JComboBox jComboBox_camp) {
        this.jComboBox_camp = jComboBox_camp;
    }

    public JComboBox getjComboBox_grade() {
        return jComboBox_grade;
    }

    public void setjComboBox_grade(JComboBox jComboBox_grade) {
        this.jComboBox_grade = jComboBox_grade;
    }

    public JComboBox getjComboBox_nationality() {
        return jComboBox_nationality;
    }

    public void setjComboBox_nationality(JComboBox jComboBox_nationality) {
        this.jComboBox_nationality = jComboBox_nationality;
    }

    public JComboBox getjComboBox_room() {
        return jComboBox_room;
    }

    public void setjComboBox_room(JComboBox jComboBox_room) {
        this.jComboBox_room = jComboBox_room;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public void setjLabel2(JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    public JLabel getjLabel3() {
        return jLabel3;
    }

    public void setjLabel3(JLabel jLabel3) {
        this.jLabel3 = jLabel3;
    }

    public JLabel getjLabel4() {
        return jLabel4;
    }

    public void setjLabel4(JLabel jLabel4) {
        this.jLabel4 = jLabel4;
    }

    public JLabel getjLabel5() {
        return jLabel5;
    }

    public void setjLabel5(JLabel jLabel5) {
        this.jLabel5 = jLabel5;
    }

    public JLabel getjLabel6() {
        return jLabel6;
    }

    public void setjLabel6(JLabel jLabel6) {
        this.jLabel6 = jLabel6;
    }

    public JLabel getjLabel7() {
        return jLabel7;
    }

    public void setjLabel7(JLabel jLabel7) {
        this.jLabel7 = jLabel7;
    }

    public JLabel getjLabel8() {
        return jLabel8;
    }

    public void setjLabel8(JLabel jLabel8) {
        this.jLabel8 = jLabel8;
    }

    public JLabel getjLabel_abailable_beds() {
        return jLabel_abailable_beds;
    }

    public void setjLabel_abailable_beds(JLabel jLabel_abailable_beds) {
        this.jLabel_abailable_beds = jLabel_abailable_beds;
    }

    public JLabel getjLabel_bed_cost() {
        return jLabel_bed_cost;
    }

    public void setjLabel_bed_cost(JLabel jLabel_bed_cost) {
        this.jLabel_bed_cost = jLabel_bed_cost;
    }

    public JLabel getjLabel_building() {
        return jLabel_building;
    }

    public void setjLabel_building(JLabel jLabel_building) {
        this.jLabel_building = jLabel_building;
    }

    public JLabel getjLabel_capacity() {
        return jLabel_capacity;
    }

    public void setjLabel_capacity(JLabel jLabel_capacity) {
        this.jLabel_capacity = jLabel_capacity;
    }

    public JLabel getjLabel_grade() {
        return jLabel_grade;
    }

    public void setjLabel_grade(JLabel jLabel_grade) {
        this.jLabel_grade = jLabel_grade;
    }

    public JLabel getjLabel_nationality_status_analyser() {
        return jLabel_nationality_status_analyser;
    }

    public void setjLabel_nationality_status_analyser(JLabel jLabel_nationality_status_analyser) {
        this.jLabel_nationality_status_analyser = jLabel_nationality_status_analyser;
    }

    public JLabel getjLabel_room() {
        return jLabel_room;
    }

    public void setjLabel_room(JLabel jLabel_room) {
        this.jLabel_room = jLabel_room;
    }

    public JLabel getjLabel_status() {
        return jLabel_status;
    }

    public void setjLabel_status(JLabel jLabel_status) {
        this.jLabel_status = jLabel_status;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public void setjPanel1(JPanel jPanel1) {
        this.jPanel1 = jPanel1;
    }

    public JPanel getjPanel2() {
        return jPanel2;
    }

    public void setjPanel2(JPanel jPanel2) {
        this.jPanel2 = jPanel2;
    }

    public JProgressBar getjProgressBar1() {
        return jProgressBar1;
    }

    public void setjProgressBar1(JProgressBar jProgressBar1) {
        this.jProgressBar1 = jProgressBar1;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JTabbedPane getjTabbedPane2() {
        return jTabbedPane2;
    }

    public void setjTabbedPane2(JTabbedPane jTabbedPane2) {
        this.jTabbedPane2 = jTabbedPane2;
    }

    public JTable getjTable_analyzer() {
        return jTable_analyzer;
    }

    public void setjTable_analyzer(JTable jTable_analyzer) {
        this.jTable_analyzer = jTable_analyzer;
    }

    public JTextField getjTextField_CEC() {
        return jTextField_CEC;
    }

    public void setjTextField_CEC(JTextField jTextField_CEC) {
        this.jTextField_CEC = jTextField_CEC;
    }

    public JTextField getjTextField_name() {
        return jTextField_name;
    }

    public void setjTextField_name(JTextField jTextField_name) {
        this.jTextField_name = jTextField_name;
    }

    public JTextField getjTextField_other_nationality() {
        return jTextField_other_nationality;
    }

    public void setjTextField_other_nationality(JTextField jTextField_other_nationality) {
        this.jTextField_other_nationality = jTextField_other_nationality;
    }

    public JTextField getjTextField_phone_area() {
        return jTextField_phone_area;
    }

    public void setjTextField_phone_area(JTextField jTextField_phone_area) {
        this.jTextField_phone_area = jTextField_phone_area;
    }

    public JTextField getjTextField_phone_number() {
        return jTextField_phone_number;
    }

    public void setjTextField_phone_number(JTextField jTextField_phone_number) {
        this.jTextField_phone_number = jTextField_phone_number;
    }

    public LinkedList getAvailable_rooms() {
        return available_rooms;
    }

    public void setFields(Camper person){
        from_camp=person.getCamp_location();
        from_room=person.getRoom().getRoom_no();
        from_building=person.getRoom().getBld_no();
        
        jTextField_name.setText(person.getName());
        jTextField_CEC.setText(person.getCec_no());
        jComboBox_grade.setSelectedItem(person.getGrade());
        jCheckBox_new_employee.setSelected(person.newEmployee);
        jCheckBox_bedding.setSelected(person.isBedding());
        try{
        jComboBox_nationality.setSelectedItem(person.getNationality());
        }
        catch(Exception e){
            jComboBox_nationality.setSelectedIndex(0);
        }
        jTextField_phone_area.setText(Integer.toString(person.getPhone_no().getArea()));
        jTextField_phone_number.setText(Integer.toString(person.getPhone_no().getNumber()));
        
        
            
        
    }
    public void setAvailable_rooms(LinkedList available_rooms) {
        this.available_rooms = available_rooms;
        ListIterator itr=available_rooms.listIterator();
        Room room=new Room();
        while(itr.hasNext()){
            room=(Room)itr.next();
            jComboBox_room.addItem(room);
            jComboBox_building.addItem(room.getBld_no());
        }
    }

    
    public LinkedList getRoomsLL() {
        return roomsLL;
    }

    public LinkedList getCampersLL() {
        return campersLL;
    }

    public void setCampersLL(LinkedList campersLL) {
        this.campersLL = campersLL;
    }

    public void setRoomsLL(LinkedList roomsLL) {
        this.roomsLL = roomsLL;
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField_name = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox_new_employee = new javax.swing.JCheckBox();
        jTextField_CEC = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox_nationality = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jTextField_phone_area = new javax.swing.JTextField();
        jTextField_phone_number = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox_camp = new javax.swing.JComboBox();
        jButton_add = new javax.swing.JButton();
        jLabel_status = new javax.swing.JLabel();
        jTextField_other_nationality = new javax.swing.JTextField();
        jLabel_room = new javax.swing.JLabel();
        jLabel_building = new javax.swing.JLabel();
        jComboBox_room = new javax.swing.JComboBox();
        jComboBox_building = new javax.swing.JComboBox();
        jLabel_grade = new javax.swing.JLabel();
        jComboBox_grade = new javax.swing.JComboBox();
        jCheckBox_bedding = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel_abailable_beds = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_capacity = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel_bed_cost = new javax.swing.JLabel();
        jCheckBox_transfer = new javax.swing.JCheckBox();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel_nationality_status_analyser = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_analyzer = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Record");

        jTabbedPane2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPane2FocusGained(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(405, 400));

        jLabel1.setText("Name");

        jTextField_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_nameActionPerformed(evt);
            }
        });

        jLabel2.setText("CEC #");

        jCheckBox_new_employee.setText("New Joinee");
        jCheckBox_new_employee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_new_employeeActionPerformed(evt);
            }
        });

        jLabel3.setText("Nationality");

        jComboBox_nationality.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_nationalityActionPerformed(evt);
            }
        });

        jLabel4.setText("Phone #");

        jTextField_phone_area.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_phone_areaActionPerformed(evt);
            }
        });

        jLabel5.setText("Camp");

        jComboBox_camp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Junior Accommodation (New East )", "Senior Accommodation (New East)\t\t", "Labour Accommodaion (New East)\t\t", "Labour Accommodaion (South Camp)\t\t", "Junior Accommodation(South Camp)" }));
        jComboBox_camp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_campActionPerformed(evt);
            }
        });

        jButton_add.setText("OK");
        jButton_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addActionPerformed(evt);
            }
        });

        jLabel_status.setText("Status");

        jLabel_room.setText("Room #");

        jLabel_building.setText("Building #");

        jComboBox_room.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_roomItemStateChanged(evt);
            }
        });
        jComboBox_room.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_roomActionPerformed(evt);
            }
        });

        jLabel_grade.setText("Grade");

        jComboBox_grade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "S1", "S2", "S3", "S4", "S5", "T1", "T2", "T3", "T4", "T5", "T6", "T7" }));

        jCheckBox_bedding.setText("Bedding");
        jCheckBox_bedding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_beddingActionPerformed(evt);
            }
        });

        jLabel6.setText("Available Beds :");

        jLabel_abailable_beds.setText("-");

        jLabel7.setText("Capacity:");

        jLabel_capacity.setText("-");

        jLabel8.setText("Bed Cost : ");

        jLabel_bed_cost.setText("-");

        jCheckBox_transfer.setText("Transfer");
        jCheckBox_transfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_transferActionPerformed(evt);
            }
        });

        jLabel9.setText("Transfer Date");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(257, 361, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jCheckBox_transfer)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5)
                            .addComponent(jLabel_room)
                            .addComponent(jLabel_building))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField_phone_area, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_phone_number, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBox_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_other_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBox_camp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jComboBox_building, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox_room, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel_capacity))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel_bed_cost))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel_abailable_beds, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_grade))
                                .addGap(55, 55, 55)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField_name)
                                            .addComponent(jTextField_CEC))
                                        .addGap(31, 31, 31))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jComboBox_grade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox_new_employee)
                                    .addComponent(jCheckBox_bedding)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel_status, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_add)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jCheckBox_new_employee)
                    .addComponent(jTextField_CEC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_grade)
                    .addComponent(jComboBox_grade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox_bedding))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField_other_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField_phone_area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_phone_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox_transfer)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateChooserCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox_camp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox_room, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel_capacity))
                    .addComponent(jLabel_room, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_building)
                            .addComponent(jComboBox_building, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_add)
                            .addComponent(jLabel_status, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel_abailable_beds))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel_bed_cost))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Information ", new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/briefcase.png")), jPanel1); // NOI18N

        jLabel_nationality_status_analyser.setText("Rooms with nationality");

        jTable_analyzer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room #", "Building #", "Capacity", "Occupancy", "Bed Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable_analyzer);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_nationality_status_analyser))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel_nationality_status_analyser)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(195, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Analyser", new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/rules24.png")), jPanel2); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox_new_employeeActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jCheckBox_new_employeeActionPerformed
        // TODO add your handling code here:
        if(jCheckBox_new_employee.isSelected()){
            jTextField_CEC.setText("New Joinee");
        }
        else
            jTextField_CEC.setText("");
                    
    }//GEN-LAST:event_jCheckBox_new_employeeActionPerformed

    private void jComboBox_nationalityActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBox_nationalityActionPerformed
        // TODO add your handling code here:
        //System.out.println(jComboBox_nationality.getSelectedIndex());
        if(jComboBox_nationality.getSelectedIndex()==0)
             jTextField_other_nationality.setEditable(true);
        else{
            jTextField_other_nationality.setText(null);
            jTextField_other_nationality.setEditable(false);
        }
    }//GEN-LAST:event_jComboBox_nationalityActionPerformed

    
    private void jTextField_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_nameActionPerformed

    private void jButton_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addActionPerformed
        // TODO add your handling code here:
       added=true;
       if(jCheckBox_transfer.isSelected())
           transferred=true;
       else
           transferred=false;
        try{
        camper.name=jTextField_name.getText();
        camper.cec_no=jTextField_CEC.getText();
        if(!(jComboBox_nationality.getSelectedIndex()==0))
             camper.nationality=jComboBox_nationality.getSelectedItem().toString(); // this will return the index of the selected item.. take care care to map the indexes properly
        else
             camper.nationality=jTextField_other_nationality.getText();

        camper.phone_no=new Phone(Integer.parseInt(jTextField_phone_area.getText()), Integer.parseInt(jTextField_phone_number.getText()));
       
        
        camper.camp_location=camper.location[jComboBox_camp.getSelectedIndex()];
        camper.newEmployee=(boolean)jCheckBox_new_employee.isSelected();
        
        camper.setRoom(new Room());
        camper.getRoom().setBld_no(jComboBox_building.getSelectedItem().toString());
        camper.getRoom().setRoom_no(jComboBox_room.getSelectedItem().toString());
        camper.setHash(camper.hashCode());
        camper.setGrade(jComboBox_grade.getSelectedItem().toString());
        camper.setBedding(jCheckBox_bedding.isSelected());
        if(jTextField_name.getCaretPosition()==0 || ((jTextField_CEC.getCaretPosition()==0) && !(jCheckBox_new_employee.isSelected()))){
            throw new NullPointerException();

            
         
        }
        System.out.println(camper.name);
        }
        
        catch(NullPointerException e){
            final JPanel error=new JPanel();
            e.printStackTrace();
            JOptionPane.showMessageDialog(error, "Please fill in all the details","Error",JOptionPane.ERROR_MESSAGE);
            added=false;
            
        }
        catch(NumberFormatException e){
             final JPanel error=new JPanel();
            JOptionPane.showMessageDialog(error, "Please enter the data correctly","Error",JOptionPane.ERROR_MESSAGE);
            added=false;
        }

        jLabel_status.setText(camper.name+" Checked In");
        
        
        if(added){
            final JPanel info=new JPanel();
            JOptionPane.showMessageDialog(info, jTextField_name.getText()+" checked in .. ","Information",JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
        
        if(transferred){
            Transfer transfer=new Transfer();
            transfer.date=dateChooserCombo1.getText();
            transfer.from_building=from_building;
            transfer.from_camp=from_camp;
            transfer.from_room=from_room;
            
            try{
                transfer.to_building=jComboBox_building.getSelectedItem().toString();
                transfer.to_camp=jComboBox_camp.getSelectedItem().toString();
                transfer.to_room=jComboBox_room.getSelectedItem().toString();
                camper.addTransfer(transfer);
                
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please fill all the fields","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }//GEN-LAST:event_jButton_addActionPerformed

    private void jTextField_phone_areaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_phone_areaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_phone_areaActionPerformed

    private void jTabbedPane2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPane2FocusGained
        // TODO add your handling code here:
        jLabel_nationality_status_analyser.setText("Rooms containing people from "+jComboBox_nationality.getSelectedItem().toString());
        DefaultTableModel model=(DefaultTableModel)jTable_analyzer.getModel();
        model.setRowCount(0); //clears the table
        ListIterator itr=roomsLL.listIterator();
        Room room_here=new Room();
        String nat=jComboBox_nationality.getSelectedItem().toString();
        while(itr.hasNext()){
            room_here=(Room)itr.next();
            if(room_here.containsNationality(nat)>0){
                model.addRow(new Object[]{room_here.getRoom_no(),room_here.getBld_no(),room_here.getCapacity(),room_here.getOccupancy(),room_here.getCost()});
            }
           
        }        
            
            
            
        
        //jTable_analyzer.setModel(model);
        //resizeColumnWidth(jTab)
    }//GEN-LAST:event_jTabbedPane2FocusGained

    private void jCheckBox_beddingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_beddingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox_beddingActionPerformed

    private void jComboBox_roomItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_roomItemStateChanged
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jComboBox_roomItemStateChanged

    private void jComboBox_roomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_roomActionPerformed
        // TODO add your handling code here:
       Room room=new Room();
        try{
          room=(Room)jComboBox_room.getSelectedItem();
          
        }
        catch(NullPointerException e){
                    jLabel_abailable_beds.setText("-");
                 jLabel_capacity.setText("-");
                 jLabel_bed_cost.setText("-");
            return;
        }
         //System.out.println(room_no);
         
         try{
         ListIterator itr=roomsLL.listIterator();
         
         Room room_h=new Room();
         while(itr.hasNext()){
             room_h=(Room)itr.next();
             if(room_h==room){
                 jComboBox_building.removeAllItems();
                 jComboBox_building.addItem(room_h.getBld_no());
                 jLabel_abailable_beds.setText(Integer.toString((room_h.getCapacity()-room_h.getOccupancy())));
                 jLabel_capacity.setText(Integer.toString(room_h.getCapacity()));
                 jLabel_bed_cost.setText(Integer.toString(room_h.getCost()));
             }
         }
         }
         catch(NullPointerException e){
             
         }
         
    }//GEN-LAST:event_jComboBox_roomActionPerformed

    private void jCheckBox_transferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_transferActionPerformed
        // TODO add your handling code here:
        if(jCheckBox_transfer.isSelected()){
        jComboBox_building.setEnabled(true);
        jComboBox_room.setEnabled(true);
        jComboBox_camp.setEnabled(true);
        dateChooserCombo1.setEnabled(true);
        }
        
        else{
       
        jComboBox_building.setEnabled(false);
        jComboBox_room.setEnabled(false);
        jComboBox_camp.setEnabled(false);
        dateChooserCombo1.setEnabled(false);
        }
            
    }//GEN-LAST:event_jCheckBox_transferActionPerformed

    private void jComboBox_campActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_campActionPerformed
        // TODO add your handling code here:
         ListIterator itr=available_rooms.listIterator();
        Room room=new Room();
        jComboBox_room.removeAllItems();
        while(itr.hasNext()){
            room=(Room)itr.next();
            if(jComboBox_camp.getSelectedIndex()==room.getCamp())
                jComboBox_room.addItem(room);
            //jComboBox_building.addItem(room.getBld_no());
        }
    }//GEN-LAST:event_jComboBox_campActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Synthetica look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
         
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewCamper dialog = new NewCamper(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                      
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton_add;
    private javax.swing.JCheckBox jCheckBox_bedding;
    private javax.swing.JCheckBox jCheckBox_new_employee;
    private javax.swing.JCheckBox jCheckBox_transfer;
    private javax.swing.JComboBox jComboBox_building;
    private javax.swing.JComboBox jComboBox_camp;
    private javax.swing.JComboBox jComboBox_grade;
    private javax.swing.JComboBox jComboBox_nationality;
    private javax.swing.JComboBox jComboBox_room;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_abailable_beds;
    private javax.swing.JLabel jLabel_bed_cost;
    private javax.swing.JLabel jLabel_building;
    private javax.swing.JLabel jLabel_capacity;
    private javax.swing.JLabel jLabel_grade;
    private javax.swing.JLabel jLabel_nationality_status_analyser;
    private javax.swing.JLabel jLabel_room;
    private javax.swing.JLabel jLabel_status;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable_analyzer;
    private javax.swing.JTextField jTextField_CEC;
    private javax.swing.JTextField jTextField_name;
    private javax.swing.JTextField jTextField_other_nationality;
    private javax.swing.JTextField jTextField_phone_area;
    private javax.swing.JTextField jTextField_phone_number;
    // End of variables declaration//GEN-END:variables
}


