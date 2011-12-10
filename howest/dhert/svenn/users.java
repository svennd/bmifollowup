package howest.dhert.svenn;

public class users {
    private int id;
    private String name;
    private boolean logged_in;
    
    public users() {
        this.id             = 0;
        this.name           = "";
        this.logged_in      = false;
    }

    public users(int id, String name, boolean logged_in) {
        this.id             = id;
        this.name           = name;
        this.logged_in      = logged_in;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }
    
}
