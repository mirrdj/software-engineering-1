package server.uniqueID;

public abstract class AbstractUniqueID {
    private final String id;
    public AbstractUniqueID(String id){
        this.id = id;
    }
    public String getID(){return id;}
}
