package movies;

import java.sql.SQLException;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableRowSorter;

import movies.db.DataSource;
import movies.gui.MovieFrame;

public class Boot {

    private static MovieFrame movieFrame;

    public static void main(String[] args) {
        applyNimbusLookAndFeel();

        try {
            DataSource.getInstance().getConnection().close();
            java.awt.EventQueue.invokeLater(() -> {
                movieFrame = new MovieFrame();
            });

            new Thread(() -> {
                while(true) {
                    try {
                        Thread.sleep(500);
                        System.out.println("Auto refresh");
                        movieFrame.getBorrowTableModel().reloadEntities();
                        movieFrame.getMovieTableModel().reloadEntities();
                        movieFrame.getMovieTable().setRowSorter(new TableRowSorter(movieFrame.getMovieTableModel()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (SQLException ex) {
            MovieFrame.showError("Az adatbázis nem elérhető!");
            System.exit(1);
        }
    }

    private static void applyNimbusLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println("Nem lehetett betölteni a Nimbus témát! : " + ex.getMessage());
        }
    }
}
