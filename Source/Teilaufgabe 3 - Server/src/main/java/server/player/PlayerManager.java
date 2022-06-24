package server.player;

import server.exceptions.NoPlayerRegisteredException;
import server.exceptions.MaximumOfPlayersAlreadyRegisteredExeception;
import server.uniqueID.PlayerID;
import server.uniqueID.PlayerIDGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerManager {
    private Map<String, Player> players = new HashMap<>();
    private Queue<String> turnOrder = new ArrayDeque<>();

    // Return map containing
    //TODO maybe still return List??
    //TODO write better comment
    public Map<String, Player> getPlayers(String requesterID) {
        PlayerIDGenerator generator = new PlayerIDGenerator();
        Map<String, Player> result = new HashMap<>();

        Player requestPlayer = players.get(requesterID);
        result.put(requesterID, requestPlayer);

        Map<String, Player> otherPlayers = getOtherPlayers(requesterID);
        for(Player eachOtherPlayer : otherPlayers.values()){
            String eOFirstName = eachOtherPlayer.getFirstName();
            String eOLastName = eachOtherPlayer.getLastName();
            String eOUaccount = eachOtherPlayer.getUaccount();
            EnumPlayerGameState eOGameState = eachOtherPlayer.getPlayerGameState();
            boolean treasureCollected = eachOtherPlayer.hasCollectedTreasure();

            PlayerID fakePlayerID = generator.generateID();
            Player hiddenInformationPlayer = new Player(fakePlayerID, eOFirstName, eOLastName, eOUaccount, eOGameState, treasureCollected);
            result.put(fakePlayerID.toString(), hiddenInformationPlayer);
        }

        return result;
    }

    private void addPlayersToQueue(Iterator<String> receivedItr, int totalActions){
        // Save iterators to collection in order to make them reusable
        Collection<String> cache = new ArrayList<>();
        while (receivedItr.hasNext()) cache.add(receivedItr.next());

        // TODO add a comment here
        for(int i = 0; i < totalActions / players.size(); i++){
            turnOrder.addAll(cache);
        }
    }
    private void initializeTurnQueue(){
        //TODO should I move totatlActions to GameManager - initialize turn from there??
        int totalActions = 200;
        double random = Math.random();

        Iterator<String> itr;

        // Start with the first player that registered
        if(random < 0.5){
            itr = players.keySet().iterator();
            addPlayersToQueue(itr, totalActions);
        }

        // Start with the last player that registered
        else {
            LinkedList<String> playerList = new LinkedList<>(players.keySet());
            itr = playerList.descendingIterator();
            addPlayersToQueue(itr, totalActions);
        }

        //TODO check if it's correct that it removes two elements from the
        // queue before sending any action
        updateTurn();
    }



    public void addPlayer(Player player){
        int maximumPlayerNumber = 2;
        if(players.size() == maximumPlayerNumber)
            throw new MaximumOfPlayersAlreadyRegisteredExeception();

        players.put(player.getPlayerID(), player);

        if(players.size() == maximumPlayerNumber){
            initializeTurnQueue();
        }
    }

    public Player getPlayerWithID(String playerID) {
        if(players.isEmpty())
            throw new NoPlayerRegisteredException();

        return players.get(playerID);
    }

    private Map<String, Player> getOtherPlayers(String playerID){
        return players
                .entrySet()
                .stream()
                .filter(stringPlayerEntry -> !stringPlayerEntry.getKey().equals(playerID))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void updateTurn(){
        if(turnOrder.isEmpty());
            //TODO throw game has already ended exception or smth

        String nextPlayer = turnOrder.poll();
        players.get(nextPlayer).setPlayerGameState(EnumPlayerGameState.MUST_ACT);

        Map<String, Player> otherPlayers = getOtherPlayers(nextPlayer);

        for(Player eachOtherPlayer : otherPlayers.values()){
            eachOtherPlayer.setPlayerGameState(EnumPlayerGameState.MUST_WAIT);
        }
    }

    public void playerLost(String playerID){
        Player loser = players.get(playerID);
        loser.setPlayerGameState(EnumPlayerGameState.LOST);

        Map<String, Player> otherPlayers = getOtherPlayers(playerID);
        for(Player eachOtherPlayer : otherPlayers.values()){
            eachOtherPlayer.setPlayerGameState(EnumPlayerGameState.WON);
        }

        turnOrder.clear();
    }

}
