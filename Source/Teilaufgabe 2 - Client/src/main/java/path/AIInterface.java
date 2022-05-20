package path;

import map.Position;
import java.util.Queue;

public interface AIInterface {
    Queue<EnumMove> generateAction(boolean treasureFound, boolean treasureCollected, boolean fortFound, Position myPosition);
}

