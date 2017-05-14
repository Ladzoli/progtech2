package movies.gui;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import movies.gui.tablemodel.AbstractEntityTableModel;
import movies.gui.tablemodel.MovieTableModel;
import movies.gui.tablemodel.BorrowTableModel;


public class BorrowAdd extends JFrame {
    
    
    private JPanel form;

    
    public BorrowAdd(JTable tableM,JTable tableP,MovieTableModel movieTableModel, BorrowTableModel publisherTableModel) {
        initFrameProperties();
        
        JLabel nameL = new JLabel("Név");
        JTextField nameT = new JTextField(15);
      
        JLabel yearL = new JLabel("Lejárat(eeee/hh/nn)");
        JTextField yearT = new JTextField(10);
        
        JLabel year2L = new JLabel("Dátum(eeee/hh/nn)");
        JTextField year2T = new JTextField(10);
        
        JButton addto = new JButton("Kölcsönadás");
        

        
        form = new JPanel();
        form.add(nameL);
        form.add(nameT);
        form.add(yearL);
        form.add(yearT);
        form.add(year2L);
        form.add(year2T);

        form.add(addto);
        
        addto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                
                boolean confirm = false;
                if(tableM.getSelectedRow()!=-1){
                 
                 boolean original = (Boolean)tableM.getValueAt(tableM.getSelectedRow(), 6);
                 if(!original){
                     int reply = JOptionPane.showConfirmDialog(null, "Valóban kölcsön akarja adni(a film nem eredeti)?", "Megerősítés", JOptionPane.YES_NO_OPTION);
                     if (reply == JOptionPane.YES_OPTION)
                        {
                            confirm = true;
                        } 
                 }
                 
                 if(confirm==true || original ){
                    try{
                    if(tableM.getValueAt(tableM.getSelectedRow(), 5).toString().equals("false")){
                        String id = tableM.getValueAt(tableM.getSelectedRow(), 0).toString();
                        String[] data = {id,nameT.getText(),yearT.getText(),year2T.getText()};
                        publisherTableModel.addNewEntity(data);


                        int update = tableM.convertRowIndexToModel(tableM.getSelectedRow());
                        movieTableModel.setValueAt(Boolean.TRUE,update,5);
                        Integer borrows = (Integer)movieTableModel.getValueAt(update,7);
                        borrows++;
                        movieTableModel.setValueAt(borrows,update,7);   
                        
                    }else{
                       JOptionPane.showMessageDialog(null,  "A film már kölcsön van adva!","Hiba", JOptionPane.ERROR_MESSAGE);
                        
                    }
                    
                  }catch(Exception ex){
                      JOptionPane.showMessageDialog(null,  "Rossz/hiányzó bemeneti adat!","Hiba", JOptionPane.ERROR_MESSAGE); 

                  }   
                 }
                 
                }else{
                  JOptionPane.showMessageDialog(null,  "Jelölj ki egy filmet!","Hiba", JOptionPane.ERROR_MESSAGE);   
                    
                }
                
                
                
                
            }
        });
        
        this.setLayout(new BorderLayout());
        this.add(form, BorderLayout.CENTER);

    }

    private void initFrameProperties() {
        setTitle("Film kölcsönadása");
        setSize(320, 200);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}    

 