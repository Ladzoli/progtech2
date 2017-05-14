import movies.db.DatabaseSetup;
import movies.gui.tablemodel.MovieTableModel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovieTests {

    MovieTableModel movieTableModel =  new MovieTableModel();

    @Before
    public static void setup() {
        DatabaseSetup.main(new String[]{});
    }

    @Test
    public void addMovieTest() throws Exception {
        String testMovieName = "ASr";
        String[] data = {testMovieName,"asd","asd", "1999", "true"};
        System.out.println(movieTableModel.getRowCount());

        movieTableModel.addNewEntity(data);
        System.out.println(movieTableModel.getRowCount());
        String movieName = (String) movieTableModel.getValueAt(0, 0);
        System.out.println(movieName);
        System.out.println(testMovieName);
        assertEquals("a filmnév nem egyezik meg", testMovieName, movieName);
    }
}
