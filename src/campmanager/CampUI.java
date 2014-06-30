/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaSkyMetallicLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
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
    
   private TableRowSorter sorter;
    private JTable jTable_insert_rooms;
    private JTable jTable_import_records;
  
   
   
    public CampUI() {
        
        initComponents();
        
        //jToolBar1.add()
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
         max_campers=(int)jSpinner_max_records.getValue();
        count=camperLL.size();
       
        //for (int i = 0; i < jTree1.getRowCount(); i++) {
         //jTree1.expandRow(i);
//}
        jLabel_count.setText(Integer.toString(count));
       jTable_records.setAutoCreateRowSorter(true);
       
       
        jTable_records.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable_camper.setModel(jTable_records.getModel());
        
       
        sorter=new TableRowSorter(jTable_camper.getModel());
        jTable_camper.setRowSorter(sorter);
        jTable_camper.setAutoCreateRowSorter(true);
        jScrollPane6.setViewportView(jTable_camper);
        
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
        if(saveResult==0){
             changesMade=false;
             saveSettings();
            
        }
        }
     // saveSettings();
      System.exit(0);          
    }
  }
             } );
   
   
    }
    
   

    public void saveDatabase(){
       
        saveSettings();
   
}

    public int confirmDialog(String string){
        String ObjButtons[] = {"Yes","No"};
        int PromptResult = JOptionPane.showOptionDialog(null, 
        string, "Frigoglass Camp Manager", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
        ObjButtons,ObjButtons[1]);
        return PromptResult;
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
          camperLL=container.getCamperLL();
         // System.out.println(camper[1]);
        
          updateTable();
         }
         else{
             container=null;
             
         }
         System.out.println("size of camperLL: "+camperLL.size());
       //System.out.println("size of camper array: "+camper.length);
               
        
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
            roomsLL=param.getRoomLL();
            
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
             roomsLL=new LinkedList();  
             param.setRoomLL(roomsLL);
            jButton_save_settingsActionPerformed(null);
            
                              
            JOptionPane.showMessageDialog(null, "Default settings loaded","Information",JOptionPane.INFORMATION_MESSAGE);
    }
    
      public void updateSettingsUI(){
          count=camperLL.size();
          
         
          
          
          jLabel_rooms_total.setText(Integer.toString(rooms_counter));
        System.out.println("rooms counter: "+rooms_counter);
//         System.out.println(availableRooms[0].getRoom_no());
        DefaultListModel lm=new DefaultListModel();
        ListIterator itr=roomsLL.listIterator();
        Room room_here=new Room();
        while(itr.hasNext()){
           room_here=(Room)itr.next();
           if(jComboBox1.getSelectedIndex()==0)
               lm.addElement(room_here);
           else if(room_here.getCamp()+1==jComboBox1.getSelectedIndex())
                 lm.addElement(room_here);
                
        }         
        this.jList_rooms.setModel(lm);
        jList_rooms_query.setModel(lm);
        jList_rooms_query.setCellRenderer( new WhiteYellowCellRenderer() );  
        jSpinner_max_records.setValue(param.getMax_records());
        jSpinner_max_rooms.setValue(param.getMaxRooms());
    }
      
        private static class WhiteYellowCellRenderer extends DefaultListCellRenderer {  
        public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {  
            Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );  
          /*  if ( index % 2 == 0 ) { 
                c.setBackground( Color.red );  
            }  
            */
            Room room=(Room)value;
            if(room.isFull()){
                c.setBackground( Color.red );  
            }
            
            return c;  
        }  
        }
      
      public void saveSettings(){
          
         String xml="";

        /*
        for(int i=0;i<count;i++){
            //serializing the persons to XML
            xml=xml+xstream.toXML(camper[i])+"\n\n";

        }
        */

        CamperContainer cont=new CamperContainer();
        cont.setCount(camperLL.size());
        cont.setCamperLL(camperLL);
        xml=xstream.toXML(cont);

        //   System.out.println(xml);
        FileOutputStream fop1=null;
        File xml_file1;
        try{
            //xml_file=new File("c:/campers.xml");
            xml_file1=new File("campers.xml");
            fop1=new FileOutputStream(xml_file1);
            if(!xml_file1.exists())
            xml_file1.createNewFile();
            byte[] contentInBytes = xml.getBytes();
            fop1.write(contentInBytes);
            fop1.flush();
            fop1.close();
            System.out.println("Written XML");

        }
        catch(Exception e){
            e.printStackTrace();
        }  
          
        Parameters param_ = new Parameters();
        param_.setRooms(availableRooms);
        param_.setRoomCount(roomsLL.size());
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
       // adjustCamperArray();
       
       count=camperLL.size();
       jLabel_rooms_total.setText(Integer.toString(rooms_counter));
       jLabel_available_rooms.setText(Integer.toString(new GetStats(camperLL,roomsLL).getAvailableRooms().size()));
       jLabel_available_beds.setText(Integer.toString(new GetStats(camperLL,roomsLL).getAvailableBeds()));
       jLabel_count.setText(Integer.toString(count));
        DefaultTableModel model=(DefaultTableModel) jTable_records.getModel();
         jLabel_count.setText( Integer.toString(count));
        //clear !!
        model.setRowCount(0);
        
        //set column width for serial # 
        ListIterator itr=camperLL.listIterator();
        
        Camper c=new Camper();
        while(itr.hasNext()){
        c=(Camper)itr.next();
              
            
            String bedding="";
            if(c.isBedding())
                bedding="Yes";
            else
                bedding="No";
                  
            try{
                // System.out.println("This"+Integer.toString(c.getPhone_no().area));
                 model.addRow(new Object[]{c.getCec_no(),c.getName(),c.getGrade(),c.getNationality(),c.getRoom().getBld_no(),c.getRoom().getRoom_no(),Integer.toString(c.getPhone_no().area)+"-"+Integer.toString(c.getPhone_no().number),c.getCampLoc()
                             ,bedding,c.getHash()});        
            }
            catch(Exception e){
                System.err.println("Model.addrow() Exceptioin");
                //System.err.println(printStackTrace());
                //e.printStackTrace();
                return;
            }
        }  
           
        
        resizeColumnWidth(jTable_records);
        
    }
    
    public void adjustCamperArray(){
        camperLL.clear();
        ListIterator itr=roomsLL.listIterator();
        Room room=new Room();
        while(itr.hasNext()){
            room=(Room)itr.next();
            LinkedList campersInRoom=room.getCampers_in_room();
            ListIterator it=campersInRoom.listIterator();
            while(it.hasNext()){
                Camper c=(Camper)it.next();
            camperLL.add(c);
            }
            
        }
        
        
        updateTable();
    }
    public void printCamperInfo(){
        
            System.out.println(count+" campers are present");
    }
    
    public void checkOut(String room_no,Camper camper){
        ListIterator itr=roomsLL.listIterator();
        Room room=new Room();
        while(itr.hasNext()){
            room=(Room)itr.next();
            if(room.getRoom_no().equals(room_no)){
                room.checkOut(camper);
                adjustCamperArray();
            }
        }
        
    }
    
    public void checkIn(String room_no,Camper camper){
        ListIterator itr=roomsLL.listIterator();
        Room room=new Room();
        while(itr.hasNext()){
            room=(Room)itr.next();
            if(room.getRoom_no().equals(room_no)){
                room.checkIn(camper);
                adjustCamperArray();
            }
        }
         
    }
     public void checkOutHash(String room_no,int hash){
        ListIterator itr=roomsLL.listIterator();
        ListIterator itrc=camperLL.listIterator();
        Camper camper=new Camper();
        boolean found=false;
        while(itrc.hasNext()){
            camper=(Camper)itrc.next();
            if(camper.getHash()==hash) {
                found=true;
                break;
            }      
            
        }
        if(!found){
            System.err.println("Camper not found : checkOutHash");
            return;
        }
        
        Room room=new Room();
        while(itr.hasNext()){
            room=(Room)itr.next();
            if(room.getRoom_no().equals(room_no)){
                room.checkOut(camper);
                System.out.println("CheckedOut!!");
                adjustCamperArray();
            }
        }
        
        
    }
    
    public void checkInHash(String room_no,int hash){
       ListIterator itr=roomsLL.listIterator();
        ListIterator itrc=camperLL.listIterator();
        Camper camper=new Camper();
        boolean found=false;
        while(itrc.hasNext()){
            camper=(Camper)itrc.next();
            if(camper.getHash()==hash) {
                found=true;
                break;
            }          
        }
        if(!found){
            System.err.println("Camper not found : checkInHash");
            return;
        }
        
        Room room=new Room();
        while(itr.hasNext()){
            room=(Room)itr.next();
            if(room.getRoom_no().equals(room_no)){
                room.checkIn(camper);
                adjustCamperArray();
            }
        }
         
    }
    
    public void clearDatabase(){
        
            
             ListIterator itr=roomsLL.listIterator();
             Room room=new Room();
          
             while(itr.hasNext()){
                 room=(Room)itr.next();
                 room.clear();
                 System.err.println("index: "+itr.nextIndex());
                 roomsLL.set(itr.nextIndex()-1, room);
             }
         saveSettings();        
        JOptionPane.showMessageDialog(null, "Database Cleared","Information",JOptionPane.INFORMATION_MESSAGE);
         adjustCamperArray();
        updateTable();
    }
    public void exportToExcel(){
        JFrame parentFrame = new JFrame();
            File fileToSave=null; ;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Export to Excel");   

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
            cell.setCellValue( dtm.getValueAt(i, j).toString());
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_records = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel_record_count = new javax.swing.JLabel();
        jLabel_count = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel_rooms_total = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel_available_rooms = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel_available_beds = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel7_queries = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList_rooms_query = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList_occupants_query = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel_capacity_queries = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel_occupancy_query = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jPanel7_queries2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_camper = new javax.swing.JTable();
        jComboBox_query_choice = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jTextField_filter = new javax.swing.JTextField();
        jPanel7_queries1 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable_room = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel_pie_nationality = new javax.swing.JLabel();
        jLabel_radar_camp = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList_rooms = new javax.swing.JList();
        jLabel_bed_cost = new javax.swing.JLabel();
        jLabel_beds = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel_capacity_status = new javax.swing.JLabel();
        jLabel_cost_status = new javax.swing.JLabel();
        jLabel_occupancy = new javax.swing.JLabel();
        jLabel_occupancy_status = new javax.swing.JLabel();
        jButton_edit_room = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel_camp = new javax.swing.JLabel();
        building = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel_room_cost = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinner_max_records = new javax.swing.JSpinner();
        jButton_save_settings = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel_max_rooms = new javax.swing.JLabel();
        jSpinner_max_rooms = new javax.swing.JSpinner();
        jTabbedPane_import = new javax.swing.JTabbedPane();
        jPanel_rooms = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel_campers = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton_save = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jButton_newRec = new javax.swing.JButton();
        jButton_checkout = new javax.swing.JButton();
        jButton_edit = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Frigoglass Camp Manager Suite v1.00");

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPane1FocusGained(evt);
            }
        });

        jTable_records.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CEC", "NAME", "GRADE", "NATIONALITY", "BUILDING", "ROOM", "PHONE ", "CAMP", "BEDDING", "hash"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Stats", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel_record_count.setText("Record Count ");

        jLabel_count.setText("-");

        jLabel3.setText("Total Rooms");

        jLabel_rooms_total.setText("-");

        jLabel8.setText("Available Rooms");

        jLabel_available_rooms.setText("-");

        jLabel15.setText("Available Beds");

        jLabel_available_beds.setText("-");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_record_count)
                    .addComponent(jLabel3))
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_count, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_rooms_total, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_available_beds)
                    .addComponent(jLabel_available_rooms, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel_available_rooms))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel_record_count)
                        .addComponent(jLabel_count, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel_rooms_total, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel_available_beds))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Filter");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("All");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("grade");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("S1");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("S2");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("T1");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("T2");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("T3");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("T4");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("T5");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("T6");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("T7");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("nationality");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("India");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Pakistan");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Sri Lanka");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Nepal");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("philippines");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Camp");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Junior New East");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Senior New East");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Labour New East");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Labour South");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Junior South");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane7.setViewportView(jTree1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                    .addComponent(jScrollPane7))
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab("Status", new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/checklist.png")), jPanel1); // NOI18N
        jTabbedPane1.setTitleAt(0, "<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=3>Status</body></html>");

        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPane2FocusGained(evt);
            }
        });

        jPanel7_queries.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Room Occupants", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jList_rooms_query.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList_rooms_queryValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(jList_rooms_query);

        jScrollPane5.setViewportView(jList_occupants_query);

        jLabel4.setText("Room");

        jLabel5.setText("Occupants");

        jLabel6.setText("Capacity");

        jLabel_capacity_queries.setText("-");

        jLabel9.setText("Occupancy");

        jLabel_occupancy_query.setText("-");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Junior New East", "Senior New East", "Labour New East", "Labour South ", "Junior South" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel13.setText("Camp");

        javax.swing.GroupLayout jPanel7_queriesLayout = new javax.swing.GroupLayout(jPanel7_queries);
        jPanel7_queries.setLayout(jPanel7_queriesLayout);
        jPanel7_queriesLayout.setHorizontalGroup(
            jPanel7_queriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7_queriesLayout.createSequentialGroup()
                .addGroup(jPanel7_queriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7_queriesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7_queriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addGroup(jPanel7_queriesLayout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel_occupancy_query, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7_queriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel7_queriesLayout.createSequentialGroup()
                                .addGroup(jPanel7_queriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel7_queriesLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel_capacity_queries)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel7_queriesLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel13)
                        .addGap(27, 27, 27)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7_queriesLayout.setVerticalGroup(
            jPanel7_queriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7_queriesLayout.createSequentialGroup()
                .addGroup(jPanel7_queriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7_queriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7_queriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7_queriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel_capacity_queries)
                    .addComponent(jLabel9)
                    .addComponent(jLabel_occupancy_query)))
        );

        jPanel7_queries2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Camper Queries", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jTable_camper.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(jTable_camper);

        jComboBox_query_choice.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAME", "CEC", "GRADE", "BUILDING", "ROOM", "NATIONALITY", "BEDDING" }));

        jLabel7.setText("Filter by :");

        jTextField_filter.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField_filterCaretUpdate(evt);
            }
        });
        jTextField_filter.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jTextField_filterCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel7_queries2Layout = new javax.swing.GroupLayout(jPanel7_queries2);
        jPanel7_queries2.setLayout(jPanel7_queries2Layout);
        jPanel7_queries2Layout.setHorizontalGroup(
            jPanel7_queries2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7_queries2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7_queries2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7_queries2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox_query_choice, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_filter))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7_queries2Layout.setVerticalGroup(
            jPanel7_queries2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7_queries2Layout.createSequentialGroup()
                .addGroup(jPanel7_queries2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox_query_choice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
        );

        jScrollPane6.getAccessibleContext().setAccessibleName("Queries");

        jPanel7_queries1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Transfer History", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jTable_room.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Name", "Transfer Date"
            }
        ));
        jScrollPane8.setViewportView(jTable_room);

        javax.swing.GroupLayout jPanel7_queries1Layout = new javax.swing.GroupLayout(jPanel7_queries1);
        jPanel7_queries1.setLayout(jPanel7_queries1Layout);
        jPanel7_queries1Layout.setHorizontalGroup(
            jPanel7_queries1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7_queries1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7_queries1Layout.setVerticalGroup(
            jPanel7_queries1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7_queries1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7_queries2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel7_queries, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7_queries1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7_queries1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7_queries, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7_queries2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Queries", new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/search.png")), jPanel5); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel6FocusGained(evt);
            }
        });
        jPanel6.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel6ComponentShown(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_pie_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel_radar_camp, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel_radar_camp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel_pie_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Analytics", new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/pie-chart.png")), jPanel6); // NOI18N

        jTabbedPane1.addTab("Statistics", new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/caculator.png")), jTabbedPane2); // NOI18N
        jTabbedPane1.setTitleAt(1, "<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=3>Statistics</body></html>");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Rooms", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jList_rooms.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
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

        jLabel_occupancy.setText("Occupancy ");

        jLabel_occupancy_status.setText("-");

        jButton_edit_room.setText("Edit Room");
        jButton_edit_room.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_edit_roomActionPerformed(evt);
            }
        });

        jLabel10.setText("Camp");

        jLabel11.setText("Building");

        jLabel_camp.setText("-");

        building.setText("-");

        jLabel12.setText("Cost p/a");

        jLabel_room_cost.setText("-");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Junior New East", "Senior New East", "Labour New East", "Labour South ", "Junior South" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel14.setText("Camp");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel_bed_cost, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel_beds))
                                    .addComponent(jLabel_occupancy)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(5, 5, 5)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_occupancy_status, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel_capacity_status, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel_cost_status, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel_camp, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(building, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(35, 35, 35))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton2)
                                    .addComponent(jButton_edit_room))
                                .addGap(28, 28, 28))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel_room_cost, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(27, 27, 27)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_beds)
                            .addComponent(jLabel_capacity_status))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_bed_cost)
                            .addComponent(jLabel_cost_status))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_occupancy)
                            .addComponent(jLabel_occupancy_status))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel_camp))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(building))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel_room_cost))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(jButton_edit_room)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane3))
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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton_save_settings)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSpinner_max_records, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel_max_rooms)
                        .addGap(18, 18, 18)
                        .addComponent(jSpinner_max_rooms, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(345, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jSpinner_max_records, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_max_rooms)
                            .addComponent(jSpinner_max_rooms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_save_settings)
                            .addComponent(jButton4)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(251, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Settings", new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/configure.png")), jPanel2); // NOI18N
        jTabbedPane1.setTitleAt(2, "<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=3>Settings</body></html>");

        jTabbedPane_import.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jButton7.setText("Prepare Table");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Add Rooms");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_roomsLayout = new javax.swing.GroupLayout(jPanel_rooms);
        jPanel_rooms.setLayout(jPanel_roomsLayout);
        jPanel_roomsLayout.setHorizontalGroup(
            jPanel_roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_roomsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9)
                    .addGroup(jPanel_roomsLayout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8)
                        .addGap(0, 643, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel_roomsLayout.setVerticalGroup(
            jPanel_roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_roomsLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel_roomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        jTabbedPane_import.addTab("Rooms", new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/ticket.png")), jPanel_rooms); // NOI18N

        jButton9.setText("Prepare Table");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Add records");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_campersLayout = new javax.swing.GroupLayout(jPanel_campers);
        jPanel_campers.setLayout(jPanel_campersLayout);
        jPanel_campersLayout.setHorizontalGroup(
            jPanel_campersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_campersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_campersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_campersLayout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addGap(18, 18, 18)
                        .addComponent(jButton10)
                        .addGap(0, 631, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel_campersLayout.setVerticalGroup(
            jPanel_campersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_campersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_campersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane_import.addTab("Campers", new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/users.png")), jPanel_campers); // NOI18N

        jTabbedPane1.addTab("Import", new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/import.png")), jTabbedPane_import); // NOI18N
        jTabbedPane1.setTitleAt(3, "<html><body leftmargin=15 topmargin=8 marginwidth=25 marginheight=3>Import</body></html>");

        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/data-add.png"))); // NOI18N
        jButton1.setToolTipText("Open Database");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/data-download.png"))); // NOI18N
        jButton_save.setToolTipText("Save Database");
        jButton_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_saveActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_save);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/data-delete.png"))); // NOI18N
        jButton6.setToolTipText("Clear Database");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jToolBar2.setRollover(true);

        jButton_newRec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/male-user-add.png"))); // NOI18N
        jButton_newRec.setToolTipText("Check In");
        jButton_newRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_newRecActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton_newRec);

        jButton_checkout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/male-user-remove.png"))); // NOI18N
        jButton_checkout.setToolTipText("Check Out");
        jButton_checkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_checkoutActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton_checkout);

        jButton_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/male-user-edit.png"))); // NOI18N
        jButton_edit.setToolTipText("Edit Record");
        jButton_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_editActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton_edit);

        jToolBar1.add(jToolBar2);

        jToolBar3.setRollover(true);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/24/Excel-icon.png"))); // NOI18N
        jButton3.setToolTipText("Export to Excel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton3);

        jToolBar1.add(jToolBar3);

        jMenu1.setText("File");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/16/data-add.png"))); // NOI18N
        jMenuItem2.setText("Open Database...");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/16/data-download.png"))); // NOI18N
        jMenuItem3.setText("Save Database..");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/16/data-delete.png"))); // NOI18N
        jMenuItem1.setText("Clear Database");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/16/import.png"))); // NOI18N
        jMenuItem9.setText("Import from Excel");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);
        jMenu1.add(jSeparator1);

        jMenuItem4.setText("Exit");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Record");

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/16/male-user-add.png"))); // NOI18N
        jMenuItem6.setText("Check In");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/16/male-user-remove.png"))); // NOI18N
        jMenuItem7.setText("Check Out");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/16/male-user-edit.png"))); // NOI18N
        jMenuItem8.setText("Edit");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Edit");

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/campmanager/resources/16/setting.png"))); // NOI18N
        jMenuItem5.setText("Preferences");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Theme");

        jMenuItem10.setText("Windows");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem11.setText("Nimbus");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuItem12.setText("Matte");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuItem13.setText("Electric Black");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);

        jMenuItem14.setText("Sky Blue");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                        checkOut(camperLocal.getRoom().getRoom_no(), camperLocal);
                        camperLL.remove(camperLocal);
                    }
                }
                adjustCamperArray();
            }
        }
    }//GEN-LAST:event_jButton_checkoutActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        int PromptResult=confirmDialog("Are you sure you want to clear the current database ?");
        if(PromptResult==0){
            changesMade=true;
            clearDatabase();
            updateSettingsUI();
        }
         adjustCamperArray();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        exportToExcel();
    }//GEN-LAST:event_jButton3ActionPerformed

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

            camperLL=((CamperContainer)xstream.fromXML(inputXML)).getCamperLL();
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_saveActionPerformed
        // TODO add your handling code here:

        if(confirmDialog("Save current database?")!=0)
        return ;

        changesMade=false;
        saveSettings();
        JOptionPane.showMessageDialog(null, "Database Saved","Information",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton_saveActionPerformed

    private void jButton_newRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_newRecActionPerformed
        // TODO add your handling code here:
        GetStats stats=new GetStats(camperLL,roomsLL);

        j=new NewCamper(this,true);
        j.setLocationRelativeTo(this);
        System.out.println(roomsLL);
        j.setAvailable_rooms(stats.getAvailableRooms());
        j.setRoomsLL(roomsLL);
        j.setCampersLL(camperLL);
        j.setVisible(true);

        // System.out.println(j);
        if(j.added)
        {
            changesMade=true;
            ListIterator itr=roomsLL.listIterator();
            Room room=new Room();
            while(itr.hasNext()){
                room=(Room)itr.next();
                if(j.camper.getRoom().getRoom_no().equals(room.getRoom_no())){
                    if(room.checkIn(j.camper)){
                        roomsLL.set(itr.nextIndex()-1, room);
                        camperLL.add(j.camper);
                        System.out.println("checked into: "+room.getRoom_no());
                        break;
                    }
                }
            }

            adjustCamperArray();
            updateTable();
            jLabel_count.setText( Integer.toString(count));
        }
    }//GEN-LAST:event_jButton_newRecActionPerformed

    private void jButton_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_editActionPerformed
        // TODO add your handling code here:
         if(jTable_records.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null, "Please select an employee from the table","Information",JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
     else{
        
        
        
        int hash=Integer.parseInt(jTable_records.getModel().getValueAt(jTable_records.getSelectedRow(),jTable_records.getColumn("hash").getModelIndex()).toString());
        changesMade=true;
        boolean found=false;
        ListIterator itr=camperLL.listIterator();
        Camper camperLocal=new Camper();
                while(itr.hasNext()){
                    camperLocal=(Camper)itr.next();
                    if(camperLocal.getHash()==hash){
                        //remove this camper from camperLL
                        //checkOut(camperLocal.getRoom().getRoom_no(), camperLocal);
                        //camperLL.remove(camperLocal);
                        found=true;
                        break;
                    }
                }
        GetStats stats=new GetStats(camperLL,roomsLL);
        EditCamper j=new EditCamper(this,true);
        j.setLocationRelativeTo(this);        
        j.setAvailable_rooms(stats.getAvailableRooms());
        j.setRoomsLL(roomsLL);
        j.setCampersLL(camperLL);
        
        
        if(!found)
        {
            System.err.print("Record to edit not found");
            return;
        }
       j.setFields(camperLocal);   
       j.setVisible(true);
       if(j.added){
           checkOut(camperLocal.getRoom().getRoom_no().toString(),camperLocal);
           
           
           int hashOld=camperLocal.getHash();
         camperLocal.setName(j.getjTextField_name().getText());
         camperLocal.setCec_no(j.getjTextField_CEC().getText());
         camperLocal.setNewEmployee(j.getjCheckBox_new_employee().isSelected());
         camperLocal.setGrade(j.getjComboBox_grade().getSelectedItem().toString());
         camperLocal.setBedding(j.getjCheckBox_bedding().isSelected());
        // camperLocal.setRoom(new Room());
         if(j.getjComboBox_nationality().getSelectedIndex()==0)
             camperLocal.setNationality(j.getjTextField_other_nationality().getText());
         else
             camperLocal.setNationality(j.getjComboBox_nationality().getSelectedItem().toString());
         camperLocal.setPhone_no(Integer.parseInt(j.getjTextField_phone_area().getText()), Integer.parseInt(j.getjTextField_phone_number().getText()));
         
         if(hashOld!=camperLocal.getHash()){
             System.err.println("HASH Fault");
             return;
         }
        
         if(j.getjCheckBox_transfer().isSelected()){
             System.out.println("tranfer in progress");
             //camper.camp_location=camper.location[jComboBox_camp.getSelectedIndex()];
             camperLocal.camp_location=camperLocal.location[j.getjComboBox_camp().getSelectedIndex()];
             camperLocal.room=new Room(j.getjComboBox_building().getSelectedItem().toString(),j.getjComboBox_room().getSelectedItem().toString());
         }
         
          // checkOutHash(j.getjComboBox_room().getSelectedItem().toString(), camperLocal.getHash());
           checkIn(camperLocal.getRoom().getRoom_no().toString(),camperLocal);
          // checkInHash(j.getjComboBox_room().getSelectedItem().toString(),camperLocal.getHash());
           
           adjustCamperArray();
       }
        
     }
        
    }//GEN-LAST:event_jButton_editActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        jButton_saveActionPerformed(null);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jTabbedPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPane1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1FocusGained

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(confirmDialog("Are you sure you want to reset settings?\n\t\tDatabase will clear !")==0)
        defaultSettings();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton_save_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_save_settingsActionPerformed
        // TODO add your handling code here:
        rooms_counter=roomsLL.size();
        saveSettings();
        updateTable();
        updateSettingsUI();

        JOptionPane.showMessageDialog(null, "Settings saved","Information",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton_save_settingsActionPerformed

    private void jButton_edit_roomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_edit_roomActionPerformed
        // TODO add your handling code here:

        String room_no=jList_rooms.getSelectedValue().toString();
        ListIterator itr=roomsLL.listIterator();
        Room r=new Room();
        int index=0;
        while(itr.hasNext()){
            index=itr.nextIndex();
            r=(Room)itr.next();
            if(r.getRoom_no().equals(room_no))
            break;
        }
        NewRoom  i=new NewRoom(this,true,r);
        i.setLocationRelativeTo(this);
        i.setVisible(true);

        if(i.success && rooms_counter<=param.maxRooms){
            r.setBld_no(i.getRoom().getBld_no());
            r.setCapacity(i.getRoom().getCapacity());
            r.setCost(i.getRoom().getCost());
            r.setRoom_no(i.getRoom().getRoom_no());
            r.setRoom_cost(i.getRoom().getRoom_cost());
            r.setCamperRoom();
            roomsLL.set(index, r);
            adjustCamperArray();
        }
    }//GEN-LAST:event_jButton_edit_roomActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        NewRoom  i=new NewRoom(this,true);
        i.setLocationRelativeTo(this);
        i.setVisible(true);

        if(i.success && rooms_counter<=param.maxRooms){
            System.out.println(rooms_counter);

            roomsLL.add(i.getRoom());
            DefaultListModel lm=new DefaultListModel();

            ListIterator itr=roomsLL.listIterator();
            Room room_here=new Room();
            while(itr.hasNext()){
                room_here=(Room)itr.next();
                lm.addElement(room_here.getRoom_no());
            }
            this.jList_rooms.setModel(lm);

        }

        /*
        if(success){
            availableRooms[rooms_counter]=j.room;
            rooms_counter++;
        }
        */
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jList_roomsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList_roomsValueChanged
        // TODO add your handling code here:
        // System.out.println("changed");
        if (!evt.getValueIsAdjusting()){
            String room_no;
            String bld;
            Room rt=new Room();
            try{
                rt=(Room)jList_rooms.getSelectedValue();
                room_no=jList_rooms.getSelectedValue().toString();
                bld=rt.getBld_no();
               // System.out.println("-->"+room_no+" "+bld+" "+rt.getCamp());
            }
            catch(NullPointerException e){
                try{
                    room_no=jList_rooms.getModel().getElementAt(0).toString();
                }
                catch(ArrayIndexOutOfBoundsException e1){
                    return;
                }
            }
            // System.out.println("Got:"+room_no+"!");
            ListIterator itr=roomsLL.listIterator();
            while(itr.hasNext()){
                Room room=(Room)itr.next();
                //System.out.println("Real:"+room.getRoom_no()+"!");
                try{
                if(room.getRoom_no().equals(rt.getRoom_no()) && room.getBld_no().equals(rt.getBld_no()) ){
                     System.out.println("Here!");
                    jLabel_capacity_status.setText(Integer.toString(room.getCapacity()));
                    jLabel_cost_status.setText(Integer.toString(room.getCost()));
                    jLabel_occupancy_status.setText(Integer.toString(room.getOccupancy()));
                    jLabel_room_cost.setText(Integer.toString(room.getRoom_cost()));
                    building.setText(room.getBld_no());
                    switch(room.getCamp()){
                        case 0: {
                            jLabel_camp.setText("Junior New-East");
                            break;
                        }
                        case 1: {
                            jLabel_camp.setText("Senior New-East");
                            break;
                        }
                        case 2: {
                            jLabel_camp.setText("Labour New-East");
                            break;
                        }
                        case 3: {
                            jLabel_camp.setText("Labour South");
                            break;
                        }
                        case 4: {
                            jLabel_camp.setText("Juniour South");
                            break;
                        }
                        default: {
                            jLabel_camp.setText("-");
                        }

                    }
                }
                }
                catch(Exception e){
                    return ;
                }
            }
        }
    }//GEN-LAST:event_jList_roomsValueChanged

    private void jTabbedPane2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPane2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane2FocusGained

    private void jPanel6ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel6ComponentShown
        // TODO add your handling code here:

        GetStats stat=new GetStats(camperLL,roomsLL);
        try {
            //jLabel_pie_nationality.setText("");
            jLabel_pie_nationality.setIcon(new ImageIcon(ImageIO.read(new URL (stat.chart_pie_nationality()))));

            //jLabel_pie_nationality.setText("");
            jLabel_radar_camp.setIcon(new ImageIcon(ImageIO.read(new URL (stat.chart_radar_camps()))));
        } catch (IOException ex) {
            Logger.getLogger(CampUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Bad network connection","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jPanel6ComponentShown

    private void jPanel6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel6FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel6FocusGained

    private void jTextField_filterCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField_filterCaretPositionChanged

    }//GEN-LAST:event_jTextField_filterCaretPositionChanged

    private void jTextField_filterCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField_filterCaretUpdate

        String target = jTextField_filter.getText();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable_camper.getModel());
        jTable_camper.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(".*"+target+".*", jTable_camper.getColumn(jComboBox_query_choice.getSelectedItem().toString()).getModelIndex()));
    }//GEN-LAST:event_jTextField_filterCaretUpdate

    private void jList_rooms_queryValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList_rooms_queryValueChanged
        // TODO add your handling code here:
        adjustCamperArray();
        if (!evt.getValueIsAdjusting()){
            String room_no;
            String bld="";
            Room rt=new Room();
            try{
                
                room_no=jList_rooms_query.getSelectedValue().toString();
                rt=(Room)jList_rooms_query.getSelectedValue();
                bld=rt.getBld_no();
                //System.out.println(room_no);
            }
            catch(NullPointerException e){
                try{
                    room_no=jList_rooms_query.getModel().getElementAt(0).toString();
                    System.err.println("NullEx");
                }
                catch(ArrayIndexOutOfBoundsException e1){
                    System.err.println("OUTofBounds");
                    return;
                }
            }
            //System.out.println("Got:"+room_no+"!");
            ListIterator itr=roomsLL.listIterator();
            LinkedList campersInRoom;
            while(itr.hasNext()){
                Room room=(Room)itr.next();
                //System.out.println("Real:"+room.getRoom_no()+"!");
                if(room.getRoom_no().equals(room_no) && room.getBld_no().equals(bld)){
                    campersInRoom=room.getCampers_in_room();
                    jList_occupants_query.removeAll();
                    jLabel_capacity_queries.setText(Integer.toString(room.getCapacity()));
                    jLabel_occupancy_query.setText(Integer.toString(room.getOccupancy()));
                    ListIterator itr1=campersInRoom.listIterator();
                    DefaultListModel lm=new DefaultListModel();
                    Camper camper=new Camper();
                    while(itr1.hasNext()){
                        camper=(Camper)itr1.next();
                        lm.addElement(camper.getName().toString());
                        // jList_occupants_query.getModel().;
                    }

                    jList_occupants_query.setModel(lm);
                }
            }
        }
    }//GEN-LAST:event_jList_rooms_queryValueChanged

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        String test1= JOptionPane.showInputDialog("Please enter number of rooms ");
        int rows=0;
        try{
            if(test1!=null)
            rows=Integer.parseInt(test1);
            else 
                return;
        }
        catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "Please enter the data correctly","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[][] data=new String[rows][7];
        String header[]= {"Building#","Room#","Room Code","Camp Location","Room Cos p/a","Bed Cost p/m","Capacity"};
        
        jTable_insert_rooms=new JTable(data,header);
        jTable_insert_rooms.setCellSelectionEnabled(true);
         //jTable_insert_rooms.setVisible(true);
        // jScrollPane9.setLayout(new BorderLayout());
        jScrollPane9.setBackground(Color.white);
        jScrollPane9.getViewport().add(jTable_insert_rooms);
         ExcelAdapter myAd = new ExcelAdapter(jTable_insert_rooms);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        //int hash=Integer.parseInt(jTable_records.getModel().getValueAt(jTable_records.getSelectedRow(),jTable_records.getColumn("hash").getModelIndex()).toString());
        
        boolean abort=false;
        int problematicRecords=0;
        int duplicateRecords=0;
        TableModel table=null;
        try{
         table= jTable_insert_rooms.getModel();
         if(table==null)
             throw new Exception("Prepare the table first");                     
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Please prepare the table first", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        GetStats stats=new GetStats(camperLL,roomsLL);
        for(int i=0;i<jTable_insert_rooms.getRowCount();i++){
                Room tableRoom=new Room();
                String room_num=table.getValueAt(i, 1).toString().trim()+" "+table.getValueAt(i, 2).toString().trim();
                String building=table.getValueAt(i, 0).toString().trim();
                if(stats.containsRoom(room_num,building)){
                    duplicateRecords++;
                    continue;
                }
                String capacity=table.getValueAt(i,6).toString().trim();
                String bed_cost=table.getValueAt(i, 5).toString().trim();
                String room_cost=table.getValueAt(i, 4).toString().trim();
                String camp_location=table.getValueAt(i, 3).toString().trim();
                
                
                try{
                    if(camp_location.equals("J-NE"))
                        tableRoom.setCamp(0);
                    else if(camp_location.equals("S-NE"))
                            tableRoom.setCamp(1);
                    else if(camp_location.equals("L-NE"))
                            tableRoom.setCamp(2);
                    else if(camp_location.equals("L-SC"))
                            tableRoom.setCamp(3);       
                    else if(camp_location.equals("J-SC"))
                            tableRoom.setCamp(4);
                    else 
                        throw new Exception("Bad Camp Location data");
                }
            
                catch (Exception e){
                    System.err.println(e.getMessage());
                    problematicRecords++;
                    continue;
                }
                 int bed_cost_int;
                int capacity_int;
                int room_cost_int;
                try{
                bed_cost_int=Integer.parseInt(bed_cost);
                capacity_int=Integer.parseInt(capacity);
                room_cost_int=Integer.parseInt(room_cost);
                }
                catch(Exception e){
                    System.err.println(e.getMessage());
                    problematicRecords++;
                    continue;
                }
                try{
                    tableRoom.setCapacity(capacity_int);
                    tableRoom.setRoom_no(room_num);
                    tableRoom.setCost(bed_cost_int);
                    tableRoom.setRoom_cost(room_cost_int);
                    tableRoom.setBld_no(building);
                }
                 catch(Exception e){
                    System.err.println(e.getMessage());
                    problematicRecords++;
                    continue;
                }
                roomsLL.add(tableRoom);
                
        }
        if(problematicRecords>0)
            JOptionPane.showMessageDialog(null,problematicRecords+" Invalid rooms ! ", "Bad Records", JOptionPane.ERROR_MESSAGE);
        else{
             JOptionPane.showMessageDialog(null,jTable_insert_rooms.getRowCount()-duplicateRecords+" roomsooms updated ! ", "Success", JOptionPane.INFORMATION_MESSAGE);
             saveSettings();
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
         String test1= JOptionPane.showInputDialog("Please enter number of campers ");
        int rows=0;
        try{
            if(test1!=null)
            rows=Integer.parseInt(test1);
            else return;
        }
        catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "Please enter the data correctly","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[][] data=new String[rows][11];
        String header[]= {"CEC#","Name","Grade","Nationality","Phone Area","Phone Number","Camp","Room#","Room Code","Building#","Bedding"};
        
        jTable_import_records=new JTable(data,header);
        jTable_import_records.setCellSelectionEnabled(true);
         //jTable_insert_rooms.setVisible(true);
        // jScrollPane9.setLayout(new BorderLayout());
        jScrollPane10.setBackground(Color.white);
        jScrollPane10.getViewport().add(jTable_import_records);
        ExcelAdapter myAd2 = new ExcelAdapter(jTable_import_records);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
         boolean abort=false;
        int problematicRecords=0;
        int duplicateRecords=0;
        int transfers=0;
        TableModel table=null;
        try{
         table= jTable_import_records.getModel();
         if(table==null)
             throw new Exception("Prepare the table first");                     
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Please prepare the table first", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        GetStats stats=new GetStats(camperLL,roomsLL);
        
        for(int i=0;i<jTable_import_records.getRowCount();i++){
            Camper camperTable=new Camper();
           //String building=table.getValueAt(i, 0).toString().trim();
            String CEC,name,grade,nationality,camp_location,room_no,room_code,building,phone_area,phone_number,bedding;
            try{
             CEC=table.getValueAt(i, 0).toString().trim();
             name=table.getValueAt(i, 1).toString().trim();
             grade=table.getValueAt(i, 2).toString().trim();
             nationality=table.getValueAt(i, 3).toString().trim();
             phone_area=table.getValueAt(i, 4).toString().trim();
             phone_number=table.getValueAt(i, 5).toString().trim();
             camp_location=table.getValueAt(i, 6).toString().trim();
             room_no=table.getValueAt(i, 7).toString().trim();
             room_code=table.getValueAt(i, 8).toString().trim();
             building=table.getValueAt(i, 9).toString().trim();
             bedding=table.getValueAt(i, 10).toString().trim();
            }
            catch(NullPointerException e){
                JOptionPane.showMessageDialog(null,"Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(stats.containsCamper(name, CEC)){
                duplicateRecords++;
                continue;
            }
            
            camperTable.setName(name);
            camperTable.setCec_no(CEC);
            
            switch(nationality){
                case "PHIL" : {
                    camperTable.setNationality("Philippines");
                    break;
                }
                case "IND" : {
                    camperTable.setNationality("India");
                    break;
                }
                case "PAK" : {
                    camperTable.setNationality("Pakistan");
                    break;
                }
                case "BAN" : {
                    camperTable.setNationality("Bangladesh");
                    break;
                }
                case "SRI" : {
                    camperTable.setNationality("Sri Lanka");
                    break;
                }
                case "NEP" : {
                    camperTable.setNationality("Nepal");
                    break;
                }
                default : {
                    JOptionPane.showMessageDialog(null,"Bad Nationality", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
            }
            Phone phone =new Phone();
            try{
                if(phone_area.contains("-") || phone_number.contains("-")){
                    phone.setArea(000);
            phone.setNumber(0000000);
                }
                else{
                 phone.setArea(Integer.parseInt(phone_area));
                  phone.setNumber(Integer.parseInt(phone_number));
                }
            camperTable.phone_no=phone;
            }
            catch(IllegalArgumentException e){
                phone.setArea(000);
            phone.setNumber(0000000);
            }
            try{
                switch (camp_location) {
                    case "J-NE":
                        camperTable.setCamp_location(camperTable.location[0]);
                        break;
                    case "S-NE":
                        camperTable.setCamp_location(camperTable.location[1]);
                        break;
                    case "L-NE":
                        camperTable.setCamp_location(camperTable.location[2]);
                        break;
                    case "L-SC":
                        camperTable.setCamp_location(camperTable.location[3]); 
                        break;
                    case "J-SC":
                        camperTable.setCamp_location(camperTable.location[4]);
                        break;
                    default:
                        throw new Exception("Bad Camp Location data");
                }
                }
            
                catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Bad Camp Location data\n Permitted values are: J-NE , S-NE , L-NE , L-SC , J-SC", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            if(camperTable.getCec_no().contains("New") || camperTable.getCec_no().contains("New Employee")|| camperTable.getCec_no().contains("new")  ){
            camperTable.setNewEmployee(true);
        }
            else
                camperTable.setNewEmployee(false);
            
            camperTable.setRoom(new Room());
            camperTable.getRoom().setBld_no(building);
            camperTable.getRoom().setRoom_no(room_no+" "+room_code);
            camperTable.setHash(camperTable.hashCode());
             Vector v = new Vector<String>();
             v.addElement("-");
             v.addElement("S1");
             v.addElement("S2");
             v.addElement("S3");
             v.addElement("S4");
             v.addElement("S5");
             v.addElement("T1");
             v.addElement("T2");
             v.addElement("T3");
             v.addElement("T4");
             v.addElement("T5");
             v.addElement("T6");
             v.addElement("T7");
            
            if(v.contains(grade)){
                camperTable.setGrade(grade);
            }
            else{
                JOptionPane.showMessageDialog(null,"Bad Grade data\n Permitted values are: "+v.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(bedding.contains("Yes")||bedding.contains("yes"))
                camperTable.setBedding(true);
            else
                camperTable.setBedding(false);
            
            ListIterator itr=roomsLL.listIterator();
            Room room2;
             room2 = new Room();
            while(itr.hasNext()){
                room2=(Room)itr.next();
                if(camperTable.getRoom().getRoom_no().equals(room2.getRoom_no())){
                    if(room2.checkIn(camperTable)){
                        roomsLL.set(itr.nextIndex()-1, room2);
                        camperLL.add(camperTable);
                        System.out.println("checked into: "+room2.getRoom_no());
                        transfers++;
                       // camperTable.display();
                        adjustCamperArray();
                        updateTable();
                        break;
                    }
                }
            
            }
            
                
  }
  JOptionPane.showMessageDialog(null,"Checked in : "+transfers+"\nDuplicates :"+duplicateRecords, "Success", JOptionPane.ERROR_MESSAGE);
      
        changesMade=true;
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        jComboBox2.setSelectedIndex(jComboBox1.getSelectedIndex());
        updateSettingsUI();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        jComboBox1.setSelectedIndex(jComboBox2.getSelectedIndex());
        updateSettingsUI();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        jButton1ActionPerformed(null);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        jButton6ActionPerformed(null);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        jButton_newRecActionPerformed(null);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        jButton_checkoutActionPerformed(null);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        jButton_editActionPerformed(null);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
         try {
         
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
           SwingUtilities.updateComponentTreeUI(this);
            this.pack();
    }
 catch (Exception e) {
    // If Nimbus is not available, fall back to cross-platform
    try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception ex) {
        // not worth my time
    }
}
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
         try {
    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
             UIManager.setLookAndFeel(info.getClassName());
             SwingUtilities.updateComponentTreeUI(this);
this.pack();
              break;
         }
         else{
                    UIManager.setLookAndFeel  ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
          }
        }
            
          
    }
 catch (Exception e) {
    // If Nimbus is not available, fall back to cross-platform
    try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception ex) {
        // not worth my time
    }
}
        
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
         try {
  
          
            UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
        //    UIManager.setLookAndFeel("synthe");
           SwingUtilities.updateComponentTreeUI(this);
            this.pack();
    }
 catch (Exception e) {
    // If Nimbus is not available, fall back to cross-platform
    try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception ex) {
        // not worth my time
    }
}
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
         try {
  
          
            UIManager.setLookAndFeel(new SyntheticaBlackMoonLookAndFeel());
        //    UIManager.setLookAndFeel("synthe");
           SwingUtilities.updateComponentTreeUI(this);
            this.pack();
    }
 catch (Exception e) {
    // If Nimbus is not available, fall back to cross-platform
    try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception ex) {
        // not worth my time
    }
}
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
         try {
  
          
              UIManager.setLookAndFeel(new SyntheticaSkyMetallicLookAndFeel());
        //    UIManager.setLookAndFeel("synthe");
           SwingUtilities.updateComponentTreeUI(this);
            this.pack();
    }
 catch (Exception e) {
    // If Nimbus is not available, fall back to cross-platform
    try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception ex) {
        // not worth my time
    }
}
    }//GEN-LAST:event_jMenuItem14ActionPerformed
  
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Seaglass look and feel */
        
  /* try {
  
//UIManager.setLookAndFeel(new SyntheticaBlackMoonLookAndFeel());
   // UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());   
UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
} catch (Exception e) {
    e.printStackTrace();
}
*/
        
        
        try {
   /* for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
            */
          
            
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
           // UIManager.setLookAndFeel("Nimbus");
    }
 catch (Exception e) {
    // If Nimbus is not available, fall back to cross-platform
    try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception ex) {
        // not worth my time
    }
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
    private javax.swing.JLabel building;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton_checkout;
    private javax.swing.JButton jButton_edit;
    private javax.swing.JButton jButton_edit_room;
    private javax.swing.JButton jButton_newRec;
    private javax.swing.JButton jButton_save;
    private javax.swing.JButton jButton_save_settings;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox_query_choice;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_available_beds;
    private javax.swing.JLabel jLabel_available_rooms;
    private javax.swing.JLabel jLabel_bed_cost;
    private javax.swing.JLabel jLabel_beds;
    private javax.swing.JLabel jLabel_camp;
    private javax.swing.JLabel jLabel_capacity_queries;
    private javax.swing.JLabel jLabel_capacity_status;
    private javax.swing.JLabel jLabel_cost_status;
    private javax.swing.JLabel jLabel_count;
    private javax.swing.JLabel jLabel_max_rooms;
    private javax.swing.JLabel jLabel_occupancy;
    private javax.swing.JLabel jLabel_occupancy_query;
    private javax.swing.JLabel jLabel_occupancy_status;
    private javax.swing.JLabel jLabel_pie_nationality;
    private javax.swing.JLabel jLabel_radar_camp;
    private javax.swing.JLabel jLabel_record_count;
    private javax.swing.JLabel jLabel_room_cost;
    private javax.swing.JLabel jLabel_rooms_total;
    private javax.swing.JList jList_occupants_query;
    private javax.swing.JList jList_rooms;
    private javax.swing.JList jList_rooms_query;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7_queries;
    private javax.swing.JPanel jPanel7_queries1;
    private javax.swing.JPanel jPanel7_queries2;
    private javax.swing.JPanel jPanel_campers;
    private javax.swing.JPanel jPanel_rooms;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSpinner jSpinner_max_records;
    private javax.swing.JSpinner jSpinner_max_rooms;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane_import;
    private javax.swing.JTable jTable_camper;
    private javax.swing.JTable jTable_records;
    private javax.swing.JTable jTable_room;
    private javax.swing.JTextField jTextField_filter;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

   static String readFile(String path, Charset encoding) 
  throws IOException 
{
  byte[] encoded = Files.readAllBytes(Paths.get(path));
  return new String(encoded, encoding);
}

       
 
         
}
