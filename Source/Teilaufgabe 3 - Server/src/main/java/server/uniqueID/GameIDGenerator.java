package server.uniqueID;

public class GameIDGenerator extends AbstractUniqueIDGenerator<GameID> {
    @Override
    public GameID generateID() {
        int idLength = 5;
        String id = super.generateString(idLength);

        return new GameID(id);
    }

}
