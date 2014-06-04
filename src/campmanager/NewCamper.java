/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package campmanager;

import com.sun.management.jmx.Trace;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Akshaya
 */
public class NewCamper extends javax.swing.JDialog {

    
     Camper camper;
     boolean added;
    
    public String[] nationality;
    
    /**
     * Creates new form NewCamper
     */
    public NewCamper(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        added=false;
        camper=new Camper();
        jTextField_phone_area.setUI(new JTextFieldHintUI("Area",Color.gray));
        jTextField_phone_number.setUI(new JTextFieldHintUI("Number",Color.gray));
        jTextField_other_nationality.setEditable(false);
        
        nationality =new String[] {"BAN","IND","NEP","PAK","PHIL","SRI"};
       
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jTabbedPane3 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(405, 400));

        jLabel1.setText("Name");

        jTextField_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_nameActionPerformed(evt);
            }
        });

        jLabel2.setText("CEC No.");

        jCheckBox_new_employee.setText("New Joinee");
        jCheckBox_new_employee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_new_employeeActionPerformed(evt);
            }
        });

        jLabel3.setText("Nationality");

        jComboBox_nationality.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BAN", "IND", "NEP", "PAK", "PHIL", "SRI", "OTHER" }));
        jComboBox_nationality.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_nationalityActionPerformed(evt);
            }
        });

        jLabel4.setText("Phone No.");

        jTextField_phone_area.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_phone_areaActionPerformed(evt);
            }
        });

        jLabel5.setText("Camp");

        jComboBox_camp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Junior Accommodation (New East )", "Senior Accommodation (New East)\t\t", "Labour Accommodaion (New East)\t\t", "Labour Accommodaion (South Camp)\t\t", "Junior Accommodation(South Camp)" }));

        jButton_add.setText("Add");
        jButton_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addActionPerformed(evt);
            }
        });

        jLabel_status.setText("Status");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(357, 357, 357))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel5)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addGap(55, 55, 55)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTextField_name)
                                        .addComponent(jTextField_CEC)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jComboBox_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTextField_other_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jTextField_phone_area, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTextField_phone_number)))
                                    .addGap(31, 31, 31)
                                    .addComponent(jCheckBox_new_employee))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jComboBox_camp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addContainerGap())
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel_status, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_add)
                            .addGap(10, 10, 10)))))
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
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox_camp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton_add)
                    .addComponent(jLabel_status, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Single ", jPanel1);
        jTabbedPane2.addTab("Multiple", jTabbedPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
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
        if(jComboBox_nationality.getSelectedItem().toString().equals("OTHER"))
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
        try{
        camper.name=jTextField_name.getText();
        camper.cec_no=jTextField_CEC.getText();
        if(!(jComboBox_nationality.getSelectedItem().toString().equals("OTHER")))
             camper.nationality=nationality[jComboBox_nationality.getSelectedIndex()]; // this will return the index of the selected item.. take care care to map the indexes properly
        else
             camper.nationality=jTextField_other_nationality.getText();

        camper.phone_no=new Phone(Integer.parseInt(jTextField_phone_area.getText()), Integer.parseInt(jTextField_phone_number.getText()));
       
        
        camper.camp_location=camper.location[jComboBox_camp.getSelectedIndex()];
        camper.newEmployee=(boolean)jCheckBox_new_employee.isSelected();
        
        
       
        if(jTextField_name.getCaretPosition()==0 || ((jTextField_CEC.getCaretPosition()==0) && !(jCheckBox_new_employee.isSelected()))){
            throw new NullPointerException();

            
         
        }
        System.out.println(camper.name);
        }
        
        catch(NullPointerException e){
            final JPanel error=new JPanel();
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
        
    }//GEN-LAST:event_jButton_addActionPerformed

    private void jTextField_phone_areaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_phone_areaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_phone_areaActionPerformed

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
            java.util.logging.Logger.getLogger(NewCamper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewCamper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewCamper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewCamper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    private javax.swing.JButton jButton_add;
    private javax.swing.JCheckBox jCheckBox_new_employee;
    private javax.swing.JComboBox jComboBox_camp;
    private javax.swing.JComboBox jComboBox_nationality;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel_status;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField jTextField_CEC;
    private javax.swing.JTextField jTextField_name;
    private javax.swing.JTextField jTextField_other_nationality;
    private javax.swing.JTextField jTextField_phone_area;
    private javax.swing.JTextField jTextField_phone_number;
    // End of variables declaration//GEN-END:variables
}


