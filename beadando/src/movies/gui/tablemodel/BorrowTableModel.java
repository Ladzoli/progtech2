package movies.gui.tablemodel;

import java.sql.Date;
import movies.db.DataSource;
import movies.db.entities.Borrow;

public class BorrowTableModel extends MoviesEntityTableModel<Borrow>{

    public BorrowTableModel() {
        super(Borrow.fieldNames, DataSource.getInstance().getPublisherController());
    }

    /*@Override
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
                return Long.class;    
            case 2:
                return String.class;
            case 3:
                return Date.class;  
            case 4:
                return Date.class;     
            default:
                return null;
        }
    }    
    
    @Override
    protected Object getAttributeOfEntity(Borrow entity, int columnIndex) {
      switch(columnIndex){
          case 0:
              return entity.getId();
          case 1:
              return entity.getFilmid();    
          case 2:
              return entity.getName();
          case 3:
              return entity.getExpire();
          case 4:
              return entity.getDate();    
          default:
              return null;
      }  
    }

    @Override
    protected void setEntityAttributes(int columnIndex, Borrow entity, Object aValue) {
        switch (columnIndex) {
            case 0:
                entity.setId((Long) aValue);
                break;
            case 1:
                entity.setFilmid((Long) aValue);
                break;    
            case 2:
                entity.setName((String) aValue);
                break;
            case 3:
                entity.setExpire((Date) aValue);    
                break;
            case 4:
                entity.setDate((Date) aValue);    
                break;    
        }
    }


    @Override
    public void addNewEntity(String[] tomb) {
        Borrow publisher = new Borrow();
        
        publisher.setFilmid(Long.parseLong(tomb[0]));
        String[] year = tomb[2].split("/"); 
        String[] year2 = tomb[3].split("/"); 
        publisher.setExpire(new Date(Integer.parseInt(year[0])-1900,Integer.parseInt(year[1])-1,Integer.parseInt(year[2])));
        publisher.setDate(new Date(Integer.parseInt(year2[0])-1900,Integer.parseInt(year2[1])-1,Integer.parseInt(year2[2])));
        publisher.setName(tomb[1]);
        addNewEntity(publisher);
    }
    
}
