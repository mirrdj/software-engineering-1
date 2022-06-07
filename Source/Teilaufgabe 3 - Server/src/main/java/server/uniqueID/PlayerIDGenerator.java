package server.uniqueID;

public class PlayerIDGenerator extends AbstractUniqueIDGenerator<PlayerID> {
    @Override
    public PlayerID generateID() {
        int idLength = 12;
        String id = super.generateString(idLength);

        return new PlayerID(id);
    }
}
