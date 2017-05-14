package movies.db.entities;

public class Movie extends AbstractEntity{
    
    private String name;
    private String director;
    private String maincast;
    private int year;
    private int kolcsondb;

    public int getKolcsondb() {
        return kolcsondb;
    }

    public void setKolcsondb(int kolcsondb) {
        this.kolcsondb = kolcsondb;
    }
    private Boolean kolcson;
    private Boolean original;

    public void setKolcson(Boolean kolcson) {
        this.kolcson = kolcson;
    }

    public void setOriginal(Boolean original) {
        this.original = original;
    }

    public Boolean getKolcson() {
        return kolcson;
    }

    public Boolean getOriginal() {
        return original;
    }
    public static final String[] fieldNames = {"ID", "Cím", "Rendező","Főszereplők","Megjelenés","Kölcsönadva","Eredetiség","Kölcsönadások"};
    
    public Movie() {}
    
    public Movie(Long id){
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
     public String getDirector() {
        return director;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    
    public void setDirector(String director) {
        this.director= director;
    }
    
    public void setMaincast(String maincast) {
        this.maincast= maincast;
    }
    
    public String getMaincast() {
        return maincast;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
