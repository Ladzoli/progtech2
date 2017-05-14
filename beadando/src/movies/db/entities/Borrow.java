package movies.db.entities;

import java.sql.Date;

public class Borrow extends AbstractEntity{
    
    public static final String[] fieldNames = {"ID","FILMID", "Név","Lejárat","Dátum"};
    
    public Borrow() {}
    
    public Borrow(Long id){
        this.id = id;
    }
    
    private String name;
    private long filmid;
    private Date date;

    public long getFilmid() {
        return filmid;
    }

    public void setFilmid(long filmid) {
        this.filmid = filmid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    private Date expire;

    public String getName() {
        return name;
    }
    
    public Date getExpire(){
        return expire;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setExpire(Date date) {
        this.expire = date;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
}
