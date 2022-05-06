package path;

import controller.EnumGamePhase;
import map.EnumTerrain;
import map.Position;
import map.full_map.EnumLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// A class for deciding what would be the best field to visit next
public class TargetChooser {
    private static final Logger logger = LoggerFactory.getLogger(TargetChooser.class);
    private static HashMap<Position, EnumTerrain> terrainNodes;
    private static Position initialPosition;
    private static EnumLayout layout;

    private EnumGamePhase gamePhase;

    // TODO check this tho
    private static List<Position> unvisitedPositionsOwnHalf; // you do not want to reinitialize this during the execution
    private static List<Position> unvisitedPositionsEnemyHalf; // you do not want to reinitialize this during the execution


    public TargetChooser(HashMap<Position, EnumTerrain> terrainNodes, Position initialPosition, EnumLayout layout){
        TargetChooser.terrainNodes = terrainNodes;
        TargetChooser.initialPosition = initialPosition;
        TargetChooser.layout = layout;

        initializeUnvisitedNodes();
    }

    public void setGamePhase(EnumGamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    // TODO: maybe move to own class
    private static void initializeUnvisitedNodes(){
        // Get the position of all unvisited mountain and grass fields
        List<Position> unvisited = terrainNodes
                .entrySet()
                .stream()
                .filter(e -> e.getValue() != EnumTerrain.WATER)
                .filter(e -> e.getValue() != EnumTerrain.MOUNTAIN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if(layout == EnumLayout.HORIZONTAL){
            List<Position> firstHalf = unvisited
                    .stream()
                    .filter(p -> p.getX() < 8)
                    .collect(Collectors.toList());

            List<Position> secondHalf = unvisited
                    .stream()
                    .filter(p -> p.getX() >= 8)
                    .collect(Collectors.toList());

            if(initialPosition.getX() < 8){
                unvisitedPositionsOwnHalf = firstHalf;
                unvisitedPositionsEnemyHalf = secondHalf;
            }

            else if(initialPosition.getX() >= 8){
                unvisitedPositionsOwnHalf = secondHalf;
                unvisitedPositionsEnemyHalf = firstHalf;
            }
        }

        else if(layout == EnumLayout.VERTICAL){
            List<Position> firstHalf = unvisited
                    .stream()
                    .filter(p -> p.getY() < 4)
                    .collect(Collectors.toList());

            List<Position> secondHalf = unvisited
                    .stream()
                    .filter(p -> p.getY() >= 4)
                    .collect(Collectors.toList());

            if(initialPosition.getY() < 4){
                unvisitedPositionsOwnHalf = firstHalf;
                unvisitedPositionsEnemyHalf = secondHalf;
            }

            else if(initialPosition.getY() >= 4){
                unvisitedPositionsOwnHalf = secondHalf;
                unvisitedPositionsEnemyHalf = firstHalf;
            }
        }

        // TODO: either both .toString() or none
        logger.debug("My own half is " + unvisitedPositionsOwnHalf);
        logger.debug("Enemy half is " + unvisitedPositionsEnemyHalf.toString());
    }

    private Position findClosestUnvisited(List<Position> unvisited, Position currentPosition, DijkstraResult dijkstraResult){
        if(currentPosition == null)
            throw new NullPointerException("Current position has not been intialized");

        return unvisited
                .stream()
                .filter(p -> !p.equals(currentPosition))
                .filter(p -> dijkstraResult.getDistance(p) != null)
                .min(Comparator.comparing(dijkstraResult::getDistance))
                .stream()
                .findFirst()
                .get();

    }

    public Position chooseTarget(DijkstraResult dijkstraResult) {
        Position from = dijkstraResult.getFrom();
        Position target = null;
        switch (gamePhase){
            case FIND_TREASURE:
                target = findClosestUnvisited(unvisitedPositionsOwnHalf, from, dijkstraResult);
                if(terrainNodes.get(target) == EnumTerrain.GRASS)
                    unvisitedPositionsOwnHalf.remove(target);
                break;

            case FIND_ENEMY_FORTRESS:
                target = findClosestUnvisited(unvisitedPositionsEnemyHalf, from, dijkstraResult);
              if(terrainNodes.get(target) == EnumTerrain.GRASS)
                    unvisitedPositionsEnemyHalf.remove(target);
            break;
        }

        logger.debug("Calculated target is " + target.toString());

        return target;
    }


}



