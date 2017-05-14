package movies.gui.tablemodel;

import java.sql.SQLException;
import movies.db.controllers.EntityController;
import movies.db.entities.EntityWithId;
import movies.gui.MovieFrame;

public abstract class MoviesEntityTableModel<E extends EntityWithId> extends AbstractEntityTableModel<E>{

    public MoviesEntityTableModel(String[] columnNames, EntityController<E> controller) {
        super(columnNames, controller);
    }

    @Override
    protected void displayError(SQLException sqlException){
        MovieFrame.showError(sqlException.getMessage());
    }

    
}
