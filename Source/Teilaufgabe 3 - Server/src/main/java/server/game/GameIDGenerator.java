package server.game;

import server.UniqueID.GameID;

public class GameIDGenerator {
    private int getRandomInt(int maximum){
        return (int) (Math.random() * maximum);
    }

    private String generateString(){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String digit = "0123456789";
        int length = 5;

        String completeString = alphabet.concat(digit);
        StringBuilder stb = new StringBuilder();

        for(int i = 0; i < length; i++) {
            int randomIndex = getRandomInt(completeString.length());
            char randomChar = completeString.charAt(randomIndex);

            stb.append(Character.toString(randomChar));
        }

        return stb.toString();
    }

    public GameID generateID() {
        String id = generateString();
        return new GameID(id);
    }

}
