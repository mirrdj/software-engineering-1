package server.UniqueID;

public abstract class UniqueID {
    private final String id;
    public UniqueID(String id){
        this.id = id;
    }

    public String getID(){return id;}
}
