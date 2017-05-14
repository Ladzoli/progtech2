package movies.gui.tablemodel;

import java.sql.SQLException;
import movies.db.DataSource;
import movies.db.entities.Movie;
//import movies.movies.db.entities.Publisher;

public class MovieTableModel extends MoviesEntityTableModel<Movie> {

    public MovieTableModel() {
        super(Movie.fieldNames, DataSource.getInstance().getMovieController());
    }

    @Override
    protected Object getAttributeOfEntity(Movie entity, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return entity.getId();
            case 1:
                return entity.getName();
            case 2:
                return entity.getDirector();
            case 3:
                return entity.getMaincast();
            case 4:
                return entity.getYear();
            case 5:
                 return entity.getKolcson();
            case 6:
                 return entity.getOriginal(); 
            case 7:
                 return entity.getKolcsondb();
            default:
                return null;
        }
    }
/*
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 0;
    }
*/
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Long.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Integer.class;
            case 5:
                return Boolean.class;
            case 6:
                return Boolean.class;    
            case 7:
                return Integer.class;
            default:
                return null;
        }
    }

    @Override
    protected void setEntityAttributes(int columnIndex, Movie entity, Object aValue) {
        switch (columnIndex) {
            case 0:
                entity.setId((Long) aValue);
                break;
            case 1:
                entity.setName((String) aValue);
                break;
            case 2:
                entity.setDirector((String) aValue);
                break;
            case 3:
                entity.setMaincast((String) aValue);
                break;
            case 4:
                entity.setYear((Integer) aValue);
                break;
            case 5:
                entity.setKolcson((Boolean) aValue);
                break;
            case 6:
                entity.setOriginal((Boolean) aValue);    
                break;
            case 7:
                entity.setKolcsondb((Integer) aValue);    
                break;    
        }
    }

   
    @Override 
    public void addNewEntity(String[] tomb) {
    
            Movie movie = new Movie();
            movie.setName(tomb[0]);
            movie.setDirector(tomb[1]);
            movie.setYear(Integer.parseInt(tomb[3]));
            movie.setMaincast(tomb[2]);
            movie.setKolcson(Boolean.FALSE);
            if(tomb[4].equals("true")){
                movie.setOriginal(Boolean.TRUE);
                
            }else{
                
                movie.setOriginal(Boolean.FALSE);
            }
           
            
            super.addNewEntity(movie);
    }

}
