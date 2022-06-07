package server.uniqueID;

public abstract class AbstractUniqueIDGenerator<UniqueID> {
    private int getRandomInt(int maximum){
        return (int) (Math.random() * maximum);
    }

    protected String generateString(int length){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String digit = "0123456789";

        String completeString = alphabet.concat(digit);
        StringBuilder stb = new StringBuilder();

        for(int i = 0; i < length; i++) {
            int randomIndex = getRandomInt(completeString.length());
            char randomChar = completeString.charAt(randomIndex);

            stb.append(randomChar);
        }

        return stb.toString();
    }
    public abstract UniqueID generateID();
}
