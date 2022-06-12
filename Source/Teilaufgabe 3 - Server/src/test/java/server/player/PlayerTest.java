package server.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.uniqueID.PlayerID;
import server.uniqueID.PlayerIDGenerator;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void getPlayerID_playerWithCertainID_returnsTheRightID() {
        PlayerIDGenerator generator = new PlayerIDGenerator();
        PlayerID playerID = generator.generateID();
        Player player = new Player(playerID);

        String expectedID = playerID.getID();
        String actualID = player.getPlayerID();

        Assertions.assertEquals(expectedID, actualID);
    }
}