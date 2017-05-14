package movies.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseSetup {

    public static void main(String[] args) {
        final File file = new File("data.sql");
        System.out.println("Az SQL fájl feldolgozása elindult!");
        try (final Scanner scanner = new Scanner(file);
                final Connection conn = DataSource.getInstance().getConnection();
            final Statement statement = conn.createStatement()) {

                scanner.useDelimiter(";");
            System.out.println("Az SQL fájl feldolgozása folyamatban van:");
            while (scanner.hasNext()) {
                final String sqlQuery = scanner.next().trim();

                if ("".equals(sqlQuery)) {
                    continue;
                }

                if (!sqlQuery.toUpperCase().startsWith("SELECT")) {
                    try {
                        statement.execute(sqlQuery);
                        System.out.println(
                                sqlQuery.replaceAll("\\s+", " ") + " : SIKERES "
                        );
                    } catch (SQLException ex) {
                        System.err.println(
                                sqlQuery.replaceAll("\\s+", " ") + " : SIKERTELEN \n"
                                + ex.getMessage()
                        );
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            System.err.println("A data.sql nem található!");
        } catch (SQLException ex) {
            System.err.println("Adatbázissal kapcsolatos kivétel keletkezett! \n"
                    + ex.getMessage()
            );
        } finally {
            System.out.println("Az SQL fájl feldolgozása befejeződött!");
        }
    }
}
