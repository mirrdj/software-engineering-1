package server.game;

public class GameIDGenerator {
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private final  String digit = "0123456789";
    private final int length = 5;

    private int getRandomInt(int maximum){
        return (int) (Math.random() * maximum);
    }

    private String generateString(){
        String completeString = alphabet.concat(digit);
        String output = "";

        for(int i = 0; i < length; i++) {
            int randomIndex = getRandomInt(completeString.length());
            char randomChar = completeString.charAt(randomIndex);
            output = output.concat(Character.toString(randomChar));
        }

        return output;
    }

    public GameID generateID() {
        String id = generateString();
        return new GameID(id);
    }

}
