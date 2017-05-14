package movies.db.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import movies.db.entities.Borrow;

public class BorrowController extends AbstractController<Borrow>{

    public BorrowController() {
        super("KOLCSONZES");
    }

    @Override
    protected Borrow newEntity() {
        return new Borrow();
    }

    @Override
    protected void setEntityAttributes(Borrow entity, ResultSet resultSet) throws SQLException {
        entity.setFilmid(resultSet.getInt(2));
        entity.setName(resultSet.getString(3));
        entity.setExpire(resultSet.getDate(4));
        entity.setDate(resultSet.getDate(5));
    }

    @Override
    protected void getEntityAttributes(ResultSet resultSet, Borrow entity) throws SQLException {
        resultSet.updateString("NAME", entity.getName());
        resultSet.updateLong("FILMID", entity.getFilmid());
        resultSet.updateDate("EXPIRE", entity.getExpire());
        resultSet.updateDate("DATE2", entity.getDate());
    }
    
}
