/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
 

/**
 *
 * @author Akshaya
 */
public class CampUI extends javax.swing.JFrame {

    /**
     * Creates new form CampUI
     */
    
    // Members: 
   public  Parameters param;
   
   private NewCamper j;
   public XStream xstream;
   public Camper camper[];
   int max_campers;
   int count;
   
   private  String inputXML;
   private String paramXML;
   private Room[] availableRooms;
   private int rooms_counter;
   //settings 
   
   public LinkedList roomsLL;
   public LinkedList camperLL;
   
   public boolean changesMade;
   
  
   
   
    public CampUI() {
        initComponents();
        max_campers=100;
        count=0;
       // camper=new Camper[max_campers];
        xstream =new XStream(new DomDriver());
        xstream.alias("Person", Camper.class);
        xstream.alias("Container",CamperContainer.class);
        xstream.alias("Parameters",Parameters.class);
        camperLL=new LinkedList();
        
        //Load the settings: 
        loadSettings();
        getRecentXML();
        updateSettingsUI();
        updateTable();
        
        jLabel_count.setText(Integer.toString(count));
       jTable_records.setAutoCreateRowSorter(true);
        
       
        addWindowListener(new WindowAdapter() {

  @Override
  public void windowClosing(WindowEvent we)
  { 
    String ObjButtons[] = {"Yes","No"};
    int PromptResult = JOptionPane.showOptionDialog(null, 
        "Are you sure you want to exit?", "Frigoglass Camp Manager", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
        ObjButtons,ObjButtons[1]);
    if(PromptResult==0 )
    {
        if(changesMade==true){
        int saveResult = JOptionPane.showOptionDialog(null, 
        "Save database before closing?", "Frigoglass Camp Manager", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
        ObjButtons,ObjButtons[1]);
        if(saveResult==0)
            jButton_saveActionPerformed(null);
        }
      saveSettings(); //save settings
      System.exit(0);          
    }
  }
             } );
   
   
    }
    
    public void getRecentXML(){
    
         try{
            //xml_file=new File("c:/campers.xml");
            File xml_file=new File("campers.xml");
            if(!xml_file.exists())
                 xml_file.createNewFile();
            

        }
        catch(Exception e){
            e.printStackTrace();
        }
         try {
            inputXML=readFile("campers.xml",Charset.defaultCharset());
           // System.out.println(inputXML);
        } catch (IOException ex) {
            Logger.getLogger(CampUI.class.getName()).log(Level.SEVERE, null, ex);
        }
         CamperContainer container;
         if(inputXML.length()>50){
            // System.out.println(inputXML.length());
          container=(CamperContainer)xstream.fromXML(inputXML);
          camper=container.getCamper();
          count=container.getCount();
         // System.out.println(camper[1]);
          for(int i=0;i<count;i++)
              camperLL.add(camper[i]);
          updateTable();
         }
         else{
             container=null;
             camper=new Camper[max_campers];
         }
         System.out.println("size of camperLL: "+camperLL.size());
       System.out.println("size of camper array: "+camper.length);
               
        
    }
    public void loadSettings(){
        changesMade=false;
    try {
        File file=new File("settings.xml");
        if(!file.exists())
                    file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        
        
         try {
           paramXML=readFile("settings.xml",Charset.defaultCharset());
           System.out.println(paramXML);
       } catch (IOException ex) {
           Logger.getLogger(CampUI.class.getName()).log(Level.SEVERE, null, ex);
           new File("settings.xml");
           
       }
         
         System.out.println("Length of paramXML: "+paramXML.length());
         if(paramXML.length()>50){
             param= (Parameters) xstream.fromXML(paramXML);
             rooms_counter=param.getRoomCount();
             availableRooms=new Room[param.getMaxRooms()];
            availableRooms=param.getRooms();
            roomsLL=new LinkedList();
            for(int i=0;i<param.getRoomCount();i++)
                roomsLL.add(availableRooms[i]);
                
             System.out.println("Size of roomsLL: "+roomsLL.size());
         }
         else{
            defaultSettings();
              
         }
         System.out.println("count: "+count);
    }
    
    public void defaultSettings(){
             param=new Parameters();
             rooms_counter=0;
             availableRooms=new Room[50];
             param.setMaxRooms(50);
             param.setMax_records(500);
            jButton_save_settingsActionPerformed(null);
            
            roomsLL=new LinkedList();                    
            JOptionPane.showMessageDialog(null, "Default settings loaded","Information",JOptionPane.INFORMATION_MESSAGE);
    }
    
      public void updateSettingsUI(){
          jLabel_rooms_available.setText(Integer.toString(rooms_counter));
        System.out.println("rooms counter: "+rooms_counter);
//         System.out.println(availableRooms[0].getRoom_no());
        DefaultListModel lm=new DefaultListModel();
            for(int t=0;t<rooms_counter;t++)
                 lm.addElement(availableRooms[t].getRoom_no());
        this.jList_rooms.setModel(lm);
        jSpinner_max_records.setValue(param.getMax_records());
        jSpinner_max_rooms.setValue(param.getMaxRooms());
    }
      
      public void saveSettings(){
           Parameters param_ = new Parameters();
        param_.setRooms(availableRooms);
        param_.setRoomCount(rooms_counter);
        param_.setRoomLL(roomsLL);
        if(rooms_counter<Integer.parseInt(jSpinner_max_rooms.getValue().toString()))
                param_.setMaxRooms(Integer.parseInt(jSpinner_max_rooms.getValue().toString()));
        if(count<Integer.parseInt(jSpinner_max_records.getValue().toString()))
                param_.setMax_records(Integer.parseInt(jSpinner_max_records.getValue().toString()));
        //saving code goes here 
        paramXML=xstream.toXML(param_);
        
        FileOutputStream fop=null;
        File xml_file;
        try{
            //xml_file=new File("c:/campers.xml");
            xml_file=new File("settings.xml");
            fop=new FileOutputStream(xml_file);
            if(!xml_file.exists())
            xml_file.createNewFile();
            byte[] contentInBytes = paramXML.getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
            System.out.println("Written Settings");

        }
        catch(Exception e){
            e.printStackTrace();
        }
      }
    public void updateTable(){
        DefaultTableModel model=(DefaultTableModel) jTable_records.getModel();
         jLabel_count.setText( Integer.toString(count));
        //clear !!
        model.setRowCount(0);
        
        //set column width for serial # 
        if(count>0){
        for(int i=0;i<count;i++){      
            String serial=Integer.toString(i+1);
               model.addRow(new Object[]{serial,camper[i].getCec_no(),camper[i].getName(),camper[i].getNationality(),camper[i].getRoom().getBld_no(),camper[i].getRoom().getRoom_no(),Integer.toString(camper[i].getPhone_no().area)+"-"+Integer.toString(camper[i].getPhone_no().number),camper[i].getCampLoc()
                             ,camper[i].getHash()});        
        }
               
           
        
        resizeColumnWidth(jTable_records);
        }
    }
    
    public void adjustCamperArray(){
        camper=(Camper[]) camperLL.toArray(new Camper[0]);
        count=camper.length;
        updateTable();
    }
    public void printCamperInfo(){
        
            System.out.println(count+" campers are present");
    }
    
    public void clearDatabase(){
        
             camper=new Camper[max_campers];
             count=0;
        JOptionPane.showMessageDialog(null, "Database Cleared","Information",JOptionPane.INFORMATION_MESSAGE);
        updateTable();
    }
    public void exportToExcel(){
        JFrame parentFrame = new JFrame();
            File fileToSave=null; ;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save File");   

            int userSelection = fileChooser.showSaveDialog(parentFrame);
 
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                              fileToSave = fileChooser.getSelectedFile();
                  // System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }
                if(fileToSave!=null){
                    
                
        String fileName=fileToSave.getAbsolutePath();
        if(!fileName.endsWith(".xls"))
            fileName+=".xls";
        try {
         DefaultTableModel dtm=(DefaultTableModel) jTable_records.getModel();
    Workbook wb = new HSSFWorkbook();
    CreationHelper createhelper = wb.getCreationHelper();
    Sheet sheet = wb.createSheet("new sheet");
    Row row = null;
    Cell cell = null;
    for (int i=0;i<dtm.getRowCount();i++) {
        row = sheet.createRow(i);
        for (int j=0;j<dtm.getColumnCount();j++) {
             
            cell = row.createCell(j);
            cell.setCellValue((String) dtm.getValueAt(i, j));
        }
    }
     
    
    FileOutputStream out = new FileOutputStream(fileName);
    wb.write(out);
    out.close();
} catch (FileNotFoundException ex) {
    ex.printStackTrace();
} catch (IOException ex) {
   ex.printStackTrace();
}
    }
    
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton_newRec = new javax.swing.JButton();
        jButton_save = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_records = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel_record_count = new javax.swing.JLabel();
        jLabel_count = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel_rooms_available = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton_checkout = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList_rooms = new javax.swing.JList();
        jLabel_bed_cost = new javax.swing.JLabel();
        jLabel_beds = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel_capacity_status = new javax.swing.JLabel();
        jLabel_cost_status = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinner_max_records = new javax.swing.JSpinner();
        jButton_save_settings = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel_max_rooms = new javax.swing.JLabel();
        jSpinner_max_rooms = new javax.swing.JSpinner();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Frigoglass Camp Manager Suite v1.00");

        jTabbedPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPane1FocusGained(evt);
            }
        });

        jButton_newRec.setText("Check In");
        jButton_newRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_newRecActionPerformed(evt);
            }
        });

        jButton_save.setText("Save");
        jButton_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_saveActionPerformed(evt);
            }
        });

        jButton1.setText("Load Database");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable_records.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sr.#", "CEC NO.", "NAME", "NAT", "BLD NO.", "ROOM NO.", "PHONE NO.", "CAMP LOC.", "hash"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable_records);

        jScrollPane2.setViewportView(jScrollPane1);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/frigoLogo.png"))); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Stats", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel_record_count.setText("Record Count ");

        jLabel3.setText("Total Rooms");

        jLabel_rooms_available.setText("  ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel_record_count)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_count, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_rooms_available, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_count, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_record_count))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel_rooms_available))
                .addContainerGap())
        );

        jButton3.setText("Save as Excel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("Save As..");

        jButton6.setText("Clear Database");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton_checkout.setText("Check Out");
        jButton_checkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_checkoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 919, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton_newRec)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_save)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton_checkout)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_save)
                            .addComponent(jButton1)
                            .addComponent(jButton_newRec)
                            .addComponent(jButton3)
                            .addComponent(jButton5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6)
                            .addComponent(jButton_checkout))))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Status", jPanel1);
        jTabbedPane1.addTab("Graphs", jTabbedPane2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Rooms", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jList_rooms.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList_roomsValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jList_rooms);

        jLabel_bed_cost.setText("Bed Cost");

        jLabel_beds.setText("Capacity");

        jButton2.setText("Add New Room");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel_capacity_status.setText("-");

        jLabel_cost_status.setText("-");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel_bed_cost)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_cost_status))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel_beds)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_capacity_status))
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_beds)
                            .addComponent(jLabel_capacity_status))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_bed_cost)
                            .addComponent(jLabel_cost_status))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel2.setText("Maximum Records");

        jSpinner_max_records.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 1));

        jButton_save_settings.setText("Save");
        jButton_save_settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_save_settingsActionPerformed(evt);
            }
        });

        jButton4.setText("Reset to Default");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel_max_rooms.setText("Maximum Rooms");

        jSpinner_max_rooms.setModel(new javax.swing.SpinnerNumberModel(0, 0, 500, 1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_save_settings)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addGap(17, 17, 17))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(391, 391, 391)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSpinner_max_records, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel_max_rooms)
                        .addGap(18, 18, 18)
                        .addComponent(jSpinner_max_rooms)))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jSpinner_max_records, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_max_rooms)
                            .addComponent(jSpinner_max_rooms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_save_settings)
                    .addComponent(jButton4))
                .addGap(19, 19, 19))
        );

        jTabbedPane1.addTab("Settings", jPanel2);

        jMenu1.setText("File");

        jMenuItem1.setText("Add Record");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        jButton_newRecActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        if(f!=null){
        String filename = f.getAbsolutePath();
        try {
            inputXML=readFile(filename,Charset.defaultCharset());
            System.out.println(inputXML);
        } catch (IOException ex) {
            Logger.getLogger(CampUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        camper=((CamperContainer)xstream.fromXML(inputXML)).getCamper();
        for(int i=0;i<2;i++){ //assume that there are 3 records .. use the count variable here

            camper[i].display();
        }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_saveActionPerformed
            // TODO add your handling code here:
        changesMade=false;
        String xml="";

        /*
        for(int i=0;i<count;i++){
            //serializing the persons to XML
            xml=xml+xstream.toXML(camper[i])+"\n\n";

        }
        */

        CamperContainer cont=new CamperContainer(camper);
        cont.setCount(count);
        xml=xstream.toXML(cont);

        //   System.out.println(xml);
        FileOutputStream fop=null;
        File xml_file;
        try{
            //xml_file=new File("c:/campers.xml");
            xml_file=new File("campers.xml");
            fop=new FileOutputStream(xml_file);
            if(!xml_file.exists())
            xml_file.createNewFile();
            byte[] contentInBytes = xml.getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
            System.out.println("Written XML");

        }
        catch(Exception e){
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Database Saved","Information",JOptionPane.INFORMATION_MESSAGE);
        
    }//GEN-LAST:event_jButton_saveActionPerformed

    private void jButton_newRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_newRecActionPerformed
        // TODO add your handling code here:
        j=new NewCamper(this,true);
        j.setLocationRelativeTo(this);
        System.out.println(roomsLL);
        j.setRoomsLL(roomsLL);
        j.setVisible(true);
        
       // System.out.println(j);
        if(j.added)
        {
            changesMade=true;
           // System.out.println("debug "+count);
       // camper[count]=j.camper;
       // camper[count].display();
       camperLL.add(j.camper);
       adjustCamperArray();
        System.out.println(count);
        updateTable();

        jLabel_count.setText( Integer.toString(count));
        }
    }//GEN-LAST:event_jButton_newRecActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        NewRoom  i=new NewRoom(this,true);
        i.setLocationRelativeTo(this);
        i.setVisible(true);
       
      
        
        
        
        if(i.success && rooms_counter<=param.maxRooms){
            System.out.println(rooms_counter);
            availableRooms[rooms_counter]=i.getRoom();  
            
            DefaultListModel lm=new DefaultListModel();
           
            for(int t=0;t<=rooms_counter;t++)
                 lm.addElement(availableRooms[t].getRoom_no());
         this.jList_rooms.setModel(lm);
            rooms_counter++;
            
            roomsLL.add(i.room);
        }
        
        
 /*
       if(success){
        availableRooms[rooms_counter]=j.room;
        rooms_counter++;
       }
   */        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    
  
    
    private void jButton_save_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_save_settingsActionPerformed
        // TODO add your handling code here:
        saveSettings();
       
        updateSettingsUI();
        
        JOptionPane.showMessageDialog(null, "Settings saved","Information",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton_save_settingsActionPerformed

    
    
    
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        defaultSettings();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
      
        exportToExcel();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTabbedPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPane1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1FocusGained

    private void jList_roomsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList_roomsValueChanged
        // TODO add your handling code here:
       // System.out.println("changed");
        if (!evt.getValueIsAdjusting()){
            String room_no;
            try{
                 room_no=jList_rooms.getSelectedValue().toString();
            }
            catch(NullPointerException e){
                 room_no=jList_rooms.getModel().getElementAt(0).toString();
            }
           // System.out.println("Got:"+room_no+"!");
            ListIterator itr=roomsLL.listIterator();
            while(itr.hasNext()){
                Room room=(Room)itr.next();
               // System.out.println("Real:"+room.getRoom_no()+"!");
                if(room.getRoom_no().equals(room_no)){
                   // System.out.println("Here!");
                    jLabel_capacity_status.setText(Integer.toString(room.getCapacity()));
                    jLabel_cost_status.setText(Integer.toString(room.getCost()));
                }
            }
        }
    }//GEN-LAST:event_jList_roomsValueChanged

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String ObjButtons[] = {"Yes","No"};
          int PromptResult = JOptionPane.showOptionDialog(null, 
        "Are you sure you want to Clear Database?", "Warning", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
        ObjButtons,ObjButtons[1]);
        if(PromptResult==0){
        changesMade=true;
        clearDatabase();
        updateSettingsUI();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton_checkoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_checkoutActionPerformed
        // TODO add your handling code here:
        //Remove an employee
        
          
        
        if(jTable_records.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null, "Please select an employee from the table","Information",JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
        else{
            String name=jTable_records.getModel().getValueAt(jTable_records.getSelectedRow(), jTable_records.getColumn("NAME").getModelIndex()).toString();
        String ObjButtons[] = {"Yes","No"};
          int PromptResult = JOptionPane.showOptionDialog(null, 
        "Checkout "+name+" ?", "Warning", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
        ObjButtons,ObjButtons[1]);
          if(PromptResult==0){
            changesMade=true;
            ListIterator itr=camperLL.listIterator();
            int hash=Integer.parseInt(jTable_records.getModel().getValueAt(jTable_records.getSelectedRow(),jTable_records.getColumn("hash").getModelIndex()).toString());
            System.out.println(hash);
             Camper camperLocal=new Camper();
            while(itr.hasNext()){
                camperLocal=(Camper)itr.next();
                if(camperLocal.getHash()==hash){
                    //remove this camper from camperLL
                    camperLL.remove(camperLocal);
                }
            }
            adjustCamperArray();
          }
        }
    }//GEN-LAST:event_jButton_checkoutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CampUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CampUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CampUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CampUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       

try {
    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
} catch (UnsupportedLookAndFeelException e) {
    // handle exception
} catch (ClassNotFoundException e) {
    // handle exception
} catch (InstantiationException e) {
    // handle exception
} catch (IllegalAccessException e) {
    // handle exception
}

      
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CampUI().setVisible(true);
            }
        });
    }
    
    public void resizeColumnWidth(JTable table) {
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 50; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width, width);
        }
        columnModel.getColumn(column).setPreferredWidth(width);
        
    }
    DefaultTableCellRenderer  leftRenderer =new DefaultTableCellRenderer();
    leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
    columnModel.getColumn(0).setCellRenderer(leftRenderer);
    
    columnModel.getColumn(0).setPreferredWidth(1);
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton_checkout;
    private javax.swing.JButton jButton_newRec;
    private javax.swing.JButton jButton_save;
    private javax.swing.JButton jButton_save_settings;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_bed_cost;
    private javax.swing.JLabel jLabel_beds;
    private javax.swing.JLabel jLabel_capacity_status;
    private javax.swing.JLabel jLabel_cost_status;
    private javax.swing.JLabel jLabel_count;
    private javax.swing.JLabel jLabel_max_rooms;
    private javax.swing.JLabel jLabel_record_count;
    private javax.swing.JLabel jLabel_rooms_available;
    private javax.swing.JList jList_rooms;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinner_max_records;
    private javax.swing.JSpinner jSpinner_max_rooms;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable_records;
    // End of variables declaration//GEN-END:variables

   static String readFile(String path, Charset encoding) 
  throws IOException 
{
  byte[] encoded = Files.readAllBytes(Paths.get(path));
  return new String(encoded, encoding);
}

       
 
         
}
