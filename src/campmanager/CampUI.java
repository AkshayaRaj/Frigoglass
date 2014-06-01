/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;


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
   private Vector camps;
   private NewCamper j;
   public XStream xstream;
   public Camper camper[];
   int max_campers;
   int count;
   
   private  String inputXML;
   private String paramXML;
   

   
   
   
   
    public CampUI() {
        initComponents();
        max_campers=100;
        count=0;
       // camper=new Camper[max_campers];
        xstream =new XStream(new DomDriver());
        xstream.alias("Person", Camper.class);
        xstream.alias("Container",CamperContainer.class);
        xstream.alias("Parameters",Parameters.class);
        
        //Load the settings: 
        loadSettings();
        getRecentXML();
        jLabel_count.setText(Integer.toString(count));
        
        
        
   
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
            System.out.println(inputXML);
        } catch (IOException ex) {
            Logger.getLogger(CampUI.class.getName()).log(Level.SEVERE, null, ex);
        }
         CamperContainer container;
         if(inputXML.length()>50){
             System.out.println(inputXML.length());
          container=(CamperContainer)xstream.fromXML(inputXML);
          camper=container.getCamper();
          count=container.getCount();
          updateTable();
         }
         else{
             container=null;
             camper=new Camper[max_campers];
         }
         
       
        
    }
    public void loadSettings(){
        File file=new File("settings.xml");
        if(!file.exists())
                try {
                    file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(CampUI.class.getName()).log(Level.SEVERE, null, ex);
        }
       try {
           FileOutputStream oFile=new FileOutputStream(file,false);
       } catch (FileNotFoundException ex) {
           Logger.getLogger(CampUI.class.getName()).log(Level.SEVERE, null, ex);
       }
                
        
        
         try {
           paramXML=readFile("settings.xml",Charset.defaultCharset());
           System.out.println(paramXML);
       } catch (IOException ex) {
           Logger.getLogger(CampUI.class.getName()).log(Level.SEVERE, null, ex);
           new File("settings.xml");
           
       }
    }
    
    
    public void updateTable(){
        DefaultTableModel model=(DefaultTableModel) jTable_records.getModel();
        for(int i=0;i<count;i++){
            model.addRow(new Object[]{i+1,camper[i].getCec_no(),camper[i].getName(),camper[i].getNationality(),"Building#","Room#",Integer.toString(camper[i].getPhone_no().area)+"-"+Integer.toString(camper[i].getPhone_no().number),camper[i].getCampLoc()});
            
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
        jLabel_record_count = new javax.swing.JLabel();
        jLabel_count = new javax.swing.JLabel();
        jButton_save = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_records = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton_newRec.setText("New Record");
        jButton_newRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_newRecActionPerformed(evt);
            }
        });

        jLabel_record_count.setText("Record Count: ");

        jButton_save.setText("Save");
        jButton_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_saveActionPerformed(evt);
            }
        });

        jButton1.setText("Load");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable_records.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Sr.#", "CEC NO.", "NAME", "NAT", "BLD NO.", "ROOM NO.", "PHONE NO.", "CAMP LOC."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_record_count)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_count, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_newRec)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_save)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_record_count)
                            .addComponent(jLabel_count, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_save)
                            .addComponent(jButton1)
                            .addComponent(jButton_newRec))))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Status", jPanel1);
        jTabbedPane1.addTab("Graphs", jTabbedPane2);
        jTabbedPane1.addTab("Preferences", jTabbedPane3);

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
            .addComponent(jTabbedPane1)
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_saveActionPerformed
        // TODO add your handling code here:

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
    }//GEN-LAST:event_jButton_saveActionPerformed

    private void jButton_newRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_newRecActionPerformed
        // TODO add your handling code here:
        j=new NewCamper(this,true);
        j.setLocationRelativeTo(this);
        j.setVisible(true);

        camper[count]=j.camper;
        camper[count].display();
        count++;
        System.out.println(count);

        jLabel_count.setText( Integer.toString(count));
    }//GEN-LAST:event_jButton_newRecActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_newRec;
    private javax.swing.JButton jButton_save;
    private javax.swing.JLabel jLabel_count;
    private javax.swing.JLabel jLabel_record_count;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable_records;
    // End of variables declaration//GEN-END:variables

   static String readFile(String path, Charset encoding) 
  throws IOException 
{
  byte[] encoded = Files.readAllBytes(Paths.get(path));
  return new String(encoded, encoding);
}

  
}
