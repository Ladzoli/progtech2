package movies.db.controllers;

import java.sql.SQLException;
import java.util.List;

public interface EntityController<E> {

    public abstract int getEntityCount() throws SQLException;
    public abstract List<E> getEntities() throws SQLException;
    public abstract E getEntityById(long id) throws SQLException;
    public abstract E getEntityByRowIndex(int rowIndex) throws SQLException;
    public abstract void addEntity(E entity) throws SQLException;
    public abstract void deleteEntity(int index) throws SQLException;
    public abstract void updateEntity(E entity, int index) throws SQLException;
}
