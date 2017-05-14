package movies;

import java.sql.SQLException;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import movies.db.DataSource;
import movies.gui.MovieFrame;

public class Boot {

    public static void main(String[] args) {
        applyNimbusLookAndFeel();

        try {
            DataSource.getInstance().getConnection().close();
            java.awt.EventQueue.invokeLater(() -> {
                new MovieFrame().setVisible(true);
            });
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
