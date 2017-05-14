package movies.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import movies.db.controllers.MovieController;
import movies.db.controllers.BorrowController;

public class DataSource {
    
    private final String connectionUrl = "jdbc:derby://localhost:1527/movies.db";
    private final String userName = "root";
    private final String password = "root";
    
    private final BorrowController borrowController;
    private final MovieController movieController;
    
    private DataSource(){
        this.borrowController = new BorrowController();
        this.movieController = new MovieController();
    }
    
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(connectionUrl, userName, password);
    }
    
    public static DataSource getInstance(){
        return DataSourceHolder.INSTANCE;
    }
    
    private static class DataSourceHolder{
        private static final DataSource INSTANCE = new DataSource();
    }
    
    public long obtainNewId() throws SQLException {
        long id;
        try (final Connection connection = getConnection();
                final Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
                final ResultSet rs = stmt.executeQuery("SELECT VAL FROM IDSEQUENCE")) {
            rs.next();
            id = rs.getLong("VAL") + 1L;
            rs.updateLong("VAL", id);
            rs.updateRow();
        }
        return id;
    }    

    public BorrowController getPublisherController() {
        return borrowController;
    }

    public MovieController getMovieController() {
        return movieController;
    }
    
}
