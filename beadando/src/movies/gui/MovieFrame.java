package movies.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.Locale.filter;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import movies.db.DataSource;
import movies.gui.tablemodel.MovieTableModel;
import movies.gui.tablemodel.BorrowTableModel;
import static java.util.Locale.filter;
import static java.util.Locale.filter;
import static java.util.Locale.filter;
import static java.util.Locale.filter;
import static java.util.Locale.filter;
import static java.util.Locale.filter;
import static java.util.Locale.filter;
import movies.gui.tablemodel.AbstractEntityTableModel;

public class MovieFrame extends JFrame {

    private final JTable movieTable, borrowTable;
    private final MovieTableModel movieTableModel;
    private final BorrowTableModel borrowTableModel;
    private final JTabbedPane dbPane;
    private final JButton ButtonMovie;
    private final JButton BorrowMovie;
    private final JButton ButtonMovieR;
    private final JButton BorrowMovieR;
    private final JButton PanicButton;
    private final JPanel movies;
    private final JPanel borrows;
    private final JPanel buttonM;
    private final JPanel buttonR;
    private final JPanel top;
    
    
    
    public MovieFrame() {
        initFrameProperties();
        movies= new JPanel(new BorderLayout());
        borrows = new JPanel(new BorderLayout());
        
        this.dbPane = new JTabbedPane();
        dbPane.setPreferredSize(new Dimension(600,400));
        
        this.movieTableModel = new MovieTableModel();
        
        this.movieTable = new JTable(movieTableModel){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                Boolean value = (Boolean)getModel().getValueAt(row, 5);
                comp.setForeground(Color.black);
                comp.setBackground(Color.white);
                if (value == true) {
                    comp.setForeground(new Color(0, 51, 255));
                } else {
                    comp.setForeground(Color.black);
                }
                if(isRowSelected(row)){
                    comp.setBackground(Color.LIGHT_GRAY);
                }
                
                return comp;
            }
            
        };
        movieTable.setAutoCreateRowSorter(true);
        movieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movies.add(new JScrollPane(movieTable),BorderLayout.CENTER);
        
        
        dbPane.addTab("Filmek", movies);

        this.borrowTableModel = new BorrowTableModel();
        this.borrowTable = new JTable(borrowTableModel){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                Date value = (Date)getModel().getValueAt(row, 3);
                comp.setForeground(Color.black);
                comp.setBackground(Color.white);
                Date date = new Date(java.lang.System.currentTimeMillis());
                if (value.compareTo(date)<0) {
                    comp.setForeground(new Color(190, 0, 0));
                } else {
                    comp.setForeground(Color.black);
                }
                if(isRowSelected(row)){
                    comp.setBackground(Color.LIGHT_GRAY);
                }
                
                return comp;
            }
            
        };
        borrowTable.setAutoCreateRowSorter(true);
        borrowTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        borrows.add(new JScrollPane(borrowTable),BorderLayout.CENTER);
        dbPane.addTab("Kölcsönadások", borrows);
           
        ButtonMovie = new JButton("Film hozzáadása");
        ButtonMovieR = new JButton("Film törlése");
        
        PanicButton = new JButton("Pánik");
        
        
      
        
        ButtonMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MovieAdd uj = new MovieAdd(movieTableModel);
                uj.setVisible (true);
                
                
                
            }
        });
        
        BorrowMovie = new JButton("Film kölcsönadása");
        BorrowMovieR = new JButton("Kölcsönzés törlése");
        
        BorrowMovieR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 if(borrowTable.getSelectedRow()!=-1){
                     
                    String id = borrowTable.getValueAt(borrowTable.getSelectedRow(), 1).toString();
                    ArrayList<Integer> deleteB = new ArrayList<>();
                    for(int i =0; i<movieTable.getRowCount();i++){
                        if(movieTable.getValueAt(i, 0).toString().equals(id)){
                            deleteB.add(i);

                        }

                    }
                    final List<Integer> rowIndicesList2 = new ArrayList<>();
                    for (int i = 0; i < deleteB.size(); i++) {
                        int selectedRowIdx2 = deleteB.get(i);
                        rowIndicesList2.add(movieTable.convertRowIndexToModel(selectedRowIdx2));
                    }
                    Collections.sort(rowIndicesList2);
                    Collections.reverse(rowIndicesList2);
                    for (int i = 0; i < rowIndicesList2.size(); i++) {
                     Integer rowIndex = rowIndicesList2.get(i);
                        movieTableModel.setValueAt(Boolean.FALSE,rowIndex,5);
                    } 



                    int[] selectedRows = borrowTable.getSelectedRows();
                    final List<Integer> rowIndicesList = new ArrayList<>(selectedRows.length);
                    for (int i = 0; i < selectedRows.length; i++) {
                        int selectedRowIdx = selectedRows[i];
                        rowIndicesList.add(borrowTable.convertRowIndexToModel(selectedRowIdx));
                    }
                    Collections.sort(rowIndicesList);
                    Collections.reverse(rowIndicesList);
                    for (int i = 0; i < rowIndicesList.size(); i++) {
                     Integer rowIndex = rowIndicesList.get(i);
                         borrowTableModel.deleteEntity(rowIndex);
                    }  
                 }else{
                     JOptionPane.showMessageDialog(null,  "Jelölj ki egy kölcsönzést!","Hiba", JOptionPane.ERROR_MESSAGE); 
                     
                 }    
            }
        });
        
        PanicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Integer> deleteB = new ArrayList<>();
                ArrayList<Integer> deleteMovie = new ArrayList<>();
                for(int i =0; i<movieTable.getRowCount();i++){
                    if(movieTable.getValueAt(i, 6).toString().equals("false")){

                        deleteB.add(Integer.parseInt(movieTable.getValueAt(i, 0).toString()));
                        deleteMovie.add(i);
                    } 
                    
                }

                final List<Integer> rowIndicesList2 = new ArrayList<>();
                for (int i = 0; i < deleteMovie.size(); i++) {
                    int selectedRowIdx2 = deleteMovie.get(i);
                    rowIndicesList2.add(movieTable.convertRowIndexToModel(selectedRowIdx2));
                }
                Collections.sort(rowIndicesList2);
                Collections.reverse(rowIndicesList2);
                for (int i = 0; i < rowIndicesList2.size(); i++) {
                 Integer rowIndex = rowIndicesList2.get(i);
                    movieTableModel.deleteEntity(rowIndex);
                } 
                
                
                
                ArrayList<Integer> deleteB2 = new ArrayList<>();
                for(int i = 0; i<deleteB.size();i ++){
                    String id = deleteB.get(i).toString();
                    for(int j =0; j<borrowTable.getRowCount();j++){
                        if(borrowTable.getValueAt(j, 1).toString().equals(id)){
                            deleteB2.add(j);  
                        }
                    
                    }   
                    
                }
                
                final List<Integer> rowIndicesList22 = new ArrayList<>();
                for (int i = 0; i < deleteB2.size(); i++) {
                    int selectedRowIdx2 = deleteB2.get(i);
                    rowIndicesList22.add(borrowTable.convertRowIndexToModel(selectedRowIdx2));
                }
                Collections.sort(rowIndicesList22);
                Collections.reverse(rowIndicesList22);
                for (int i = 0; i < rowIndicesList22.size(); i++) {
                 Integer rowIndex = rowIndicesList22.get(i);
                    borrowTableModel.deleteEntity(rowIndex);
                }     
                PanicButton.setVisible(false);
                PanicButton.setEnabled(false);
                
                repaint();    
            }         
        });       
        
        ButtonMovieR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(movieTable.getSelectedRow()!=-1){
                    String id = movieTable.getValueAt(movieTable.getSelectedRow(), 0).toString();
                    ArrayList<Integer> deleteB = new ArrayList<>();
                    for(int i =0; i<borrowTable.getRowCount();i++){
                        if(borrowTable.getValueAt(i, 1).toString().equals(id)){
                            deleteB.add(i);

                        }

                    }


                    final List<Integer> rowIndicesList2 = new ArrayList<>();
                    for (int i = 0; i < deleteB.size(); i++) {
                        int selectedRowIdx2 = deleteB.get(i);
                        rowIndicesList2.add(borrowTable.convertRowIndexToModel(selectedRowIdx2));
                    }
                    Collections.sort(rowIndicesList2);
                    Collections.reverse(rowIndicesList2);
                    for (int i = 0; i < rowIndicesList2.size(); i++) {
                     Integer rowIndex = rowIndicesList2.get(i);
                        borrowTableModel.deleteEntity(rowIndex);
                    } 



                    int[] selectedRows = movieTable.getSelectedRows();
                    final List<Integer> rowIndicesList = new ArrayList<>(selectedRows.length);
                    for (int i = 0; i < selectedRows.length; i++) {
                        int selectedRowIdx = selectedRows[i];
                        rowIndicesList.add(movieTable.convertRowIndexToModel(selectedRowIdx));
                    }
                    Collections.sort(rowIndicesList);
                    Collections.reverse(rowIndicesList);
                    for (int i = 0; i < rowIndicesList.size(); i++) {
                     Integer rowIndex = rowIndicesList.get(i);
                        movieTableModel.deleteEntity(rowIndex);
                    }                    
                }else{
                     JOptionPane.showMessageDialog(null,  "Jelölj ki egy filmet!","Hiba", JOptionPane.ERROR_MESSAGE); 
                }              
            }         
        });

        
        BorrowMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                BorrowAdd uj = new BorrowAdd(movieTable,borrowTable,movieTableModel,borrowTableModel);
                uj.setVisible (true);
                
                
                
            }
        });
        
        buttonM = new JPanel(new BorderLayout());
        top = new JPanel(new FlowLayout());
        
        top.add(BorrowMovie);
        BorrowMovie.setPreferredSize(new Dimension(200,30));
        ButtonMovieR.setPreferredSize(new Dimension(200,30));
        ButtonMovie.setPreferredSize(new Dimension(200,30));
        //buttonM.add(ButtonMovie,BorderLayout.CENTER);
        top.add(ButtonMovieR);
        buttonM.add(top);
        
        buttonR = new JPanel(new BorderLayout());
        buttonR.add(BorrowMovieR,BorderLayout.CENTER);
        
        BorrowMovieR.setPreferredSize(new Dimension(200,30));
        
        movies.add(buttonM,BorderLayout.NORTH);
        borrows.add(buttonR,BorderLayout.NORTH);
        
        JLabel titleL = new JLabel("Cím");
        JTextField titleT = new JTextField(10);
        JLabel dateL = new JLabel("Évszám");
        JTextField dateT = new JTextField(10);
        JButton applyB = new JButton("Keresés");
        JPanel search = new JPanel();
        search.add(titleL);
        search.add(titleT);
        search.add(dateL);
        search.add(dateT);
        search.add(applyB);
        buttonM.add(search,BorderLayout.SOUTH);
        
        

        applyB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableRowSorter sorter = new TableRowSorter(movieTableModel);
                List<RowFilter<MovieTableModel,Integer>> rfs = 
                    new ArrayList<RowFilter<MovieTableModel,Integer>>(2);
                rfs.add(RowFilter.regexFilter(titleT.getText(), 1));
                rfs.add(RowFilter.regexFilter(dateT.getText(), 4));
                RowFilter<MovieTableModel,Integer> af = RowFilter.andFilter(rfs);
                sorter.setRowFilter(af);
                movieTable.setRowSorter(sorter);
                
            }
        });    
        
        
        JLabel titleL2 = new JLabel("Név");
        JTextField titleT2 = new JTextField(10);
        JLabel dateL2 = new JLabel("Dátum");
        JTextField dateT2 = new JTextField(10);
        JButton applyB2 = new JButton("Keresés");
        JPanel search2 = new JPanel();
        search2.add(titleL2);
        search2.add(titleT2);
        search2.add(dateL2);
        search2.add(dateT2);
        search2.add(applyB2);
        buttonR.add(search2,BorderLayout.SOUTH);
        
        

        applyB2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableRowSorter sorter = new TableRowSorter(borrowTableModel);
                List<RowFilter<MovieTableModel,Integer>> rfs = 
                    new ArrayList<RowFilter<MovieTableModel,Integer>>(2);
                rfs.add(RowFilter.regexFilter(titleT2.getText(), 2));
                rfs.add(RowFilter.regexFilter(dateT2.getText(), 4));
                RowFilter<MovieTableModel,Integer> af = RowFilter.andFilter(rfs);
                sorter.setRowFilter(af);
                borrowTable.setRowSorter(sorter);
                
            }
        });    
        
        
        
        JButton RefreshB = new JButton("Frissít");
        RefreshB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((AbstractEntityTableModel)movieTableModel).reloadEntities();
                ((AbstractEntityTableModel)borrowTableModel).reloadEntities();
                TableRowSorter sorter = new TableRowSorter(movieTableModel);
                movieTable.setRowSorter(sorter);	
            }
        });   
        
        getContentPane().add(dbPane, BorderLayout.CENTER);
        JPanel Bottom = new JPanel();
        Bottom.setLayout(new GridLayout(1,3));
        Bottom.add(ButtonMovie);
        Bottom.add(PanicButton);
        Bottom.add(RefreshB);
        
        getContentPane().add(Bottom, BorderLayout.SOUTH);
        pack();

    }

    private void initFrameProperties() {
        setTitle("Nyílvántartás");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

   
   
    
}
