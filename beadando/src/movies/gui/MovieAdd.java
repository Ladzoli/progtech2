package movies.gui;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import movies.gui.tablemodel.AbstractEntityTableModel;
import movies.gui.tablemodel.MovieTableModel;


public class MovieAdd extends JFrame {
    
    
    private JPanel form;

    
    public MovieAdd(MovieTableModel movieTableModel) {
        initFrameProperties();
        
        JLabel nameL = new JLabel("Cím");
        JTextField nameT = new JTextField(20);
        JLabel directorL = new JLabel("Rendező");
        JTextField directorT = new JTextField(20);
        JLabel maincastL = new JLabel("Föszereplő");
        JTextField maincastT = new JTextField(20);
        JLabel yearL = new JLabel("Megjelenés");
        JTextField yearT = new JTextField(20);
        JLabel originalL = new JLabel("Eredetiség");
        Checkbox originalC = new Checkbox();
        JButton addto = new JButton("Hozzáadás");

        
        form = new JPanel();
        form.add(nameL);
        form.add(nameT);
        form.add(directorL);
        form.add(directorT);
        form.add(maincastL);
        form.add(maincastT);
        form.add(yearL);
        form.add(yearT);
        form.add(originalL);
        form.add(originalC);
        form.add(addto);
        
        addto.addActionListener(e -> {
            try{
                Boolean ori = originalC.getState();
                String orig = ori.toString();
                String[] data = {nameT.getText(),directorT.getText(),maincastT.getText(),yearT.getText(),orig};
                movieTableModel.addNewEntity(data);


            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,  "Rossz/hiányzó bemeneti adat","Hiba", JOptionPane.ERROR_MESSAGE);

            }
        });
        
        this.setLayout(new BorderLayout());
        this.add(form, BorderLayout.CENTER);

    }

    private void initFrameProperties() {
        setTitle("Film hozzáadása");
        setSize(320, 250);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}    

 