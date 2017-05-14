package movies.db.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import movies.db.DataSource;
import movies.db.entities.Movie;

public class MovieController extends AbstractController<Movie>{

    public MovieController() {
        super("FILM");
    }

    @Override
    protected Movie newEntity() {
        return new Movie();
    }

    @Override
    protected void setEntityAttributes(Movie entity, ResultSet resultSet) throws SQLException {
        entity.setName(resultSet.getString(2));
        entity.setDirector(resultSet.getString(3));
        entity.setMaincast(resultSet.getString(4));
        entity.setYear(resultSet.getInt(5));
        entity.setKolcson(resultSet.getBoolean(6));
        entity.setOriginal(resultSet.getBoolean(7));
        entity.setKolcsondb(resultSet.getInt(8));
    }

    @Override
    protected void getEntityAttributes(ResultSet resultSet, Movie entity) throws SQLException {
        resultSet.updateString("NAME", entity.getName());
        resultSet.updateString("DIRECTOR", entity.getDirector());
        resultSet.updateString("MAINCAST", entity.getMaincast());
        resultSet.updateInt("YEAR_OF_RELEASE", entity.getYear());
        resultSet.updateBoolean("KOLCSON", entity.getKolcson());
        resultSet.updateBoolean("ORIGINAL", entity.getOriginal());
        resultSet.updateInt("KOLCSONDB", entity.getKolcsondb());
        
    }
    
}
