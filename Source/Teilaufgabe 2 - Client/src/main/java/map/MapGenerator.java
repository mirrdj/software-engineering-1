package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import exceptions.WrongNumberOfGrassFieldsException;
import exceptions.WrongNumberOfMountainFieldsException;
import exceptions.WrongNumberOfWaterFieldsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapGenerator {
	private static final Logger logger = LoggerFactory.getLogger(MapGenerator.class);
	private final int minWaterNumber;
	private final int minMountainNumber;


	public MapGenerator(int minWaterNumber, int minMountainNumber) {
		if(minWaterNumber < 4 || minWaterNumber > 14)
			throw new WrongNumberOfWaterFieldsException("Not enough or too many water fields");
		if(minMountainNumber < 3 || minMountainNumber > 13)
			throw new WrongNumberOfMountainFieldsException("Not enough or too many mountain fields");
		if(minWaterNumber + minMountainNumber > 32 - 15)
			throw new WrongNumberOfGrassFieldsException("Not enough or too many grass fields");

		this.minWaterNumber = minWaterNumber;
		this.minMountainNumber = minMountainNumber;
	}

	private Position findFortressPosition( int x, int y, HashMap<Position, EnumTerrain> nodes) {
		logger.warn("The fortress tried to be place on" + x + " " + y);

		if(x > 0 && nodes.get(new Position(x - 1, y)) == EnumTerrain.GRASS)
			return new Position(x - 1, y);

		else if(x < 7 && nodes.get(new Position(x + 1, y)) == EnumTerrain.GRASS)
			return new Position(x + 1, y);

		else if(y > 0 && nodes.get(new Position(x, y - 1)) == EnumTerrain.GRASS)
			return new Position(x, y - 1);

		else if(y < 3 && nodes.get(new Position(x, y + 1)) == EnumTerrain.GRASS)
			return new Position(x, y + 1);

		else if(x < 7)
			findFortressPosition(x + 1, y, nodes);
		
		else if(y > 0)
			findFortressPosition(x, y - 1, nodes);
		
		else if(x > 0)
			findFortressPosition(x - 1, y, nodes);
		
		else if(y < 3)
			findFortressPosition(x, y + 1, nodes);

		logger.info("The fortress was actually placed at" + x + " " + y);

		return null;
	}

	private void placeMountain(int mountainNumberNeeded, HashMap<Position, EnumTerrain> nodes) {
		logger.debug("Placing mountain");
		int index = 0;

		while(index < mountainNumberNeeded) {
			int randomX = (int) (Math.random() * 8);
			int randomY = (int) (Math.random() * 4);
			Position position = new Position(randomX, randomY);

			if(nodes.get(position) == null){
				nodes.put(position, EnumTerrain.MOUNTAIN);
				index++;
			}
		}
	}

	private void placeWater(int waterNumberNeeded, HashMap<Position, EnumTerrain> nodes) {
		logger.debug("Placing water");
		int index = 0;
		while(index < waterNumberNeeded) {
			int randomX = (int) (Math.random() * 8);
			int randomY = (int) (Math.random() * 4);
			Position position = new Position(randomX, randomY);

			if(nodes.get(position) == null){
				nodes.put(position, EnumTerrain.WATER);
				index++;
			}
		}
	}

	private void placeGrass(HashMap<Position, EnumTerrain> nodes){
		logger.debug("Placing grass");
		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				Position position = new Position(i, j);
				nodes.putIfAbsent(position, EnumTerrain.GRASS);
			}
		}
	}

	private List<Position> getMountainPosition (HashMap<Position, EnumTerrain> nodes) {
		return nodes
				.entrySet()
				.stream()
				.filter(e -> e.getValue().equals(EnumTerrain.MOUNTAIN))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	private Position getRandomMountain(HashMap<Position, EnumTerrain> nodes) {
		List<Position> positionList = getMountainPosition(nodes);
		logger.debug("Mountains are at " + positionList);
		int randomIndex = (int) (Math.random() * positionList.size());
		logger.debug("Random position is " + randomIndex);
		return positionList.get(randomIndex);
	}


	public MapClass generateHalfMap() {
		HashMap<Position, EnumTerrain> nodes = new HashMap<>();
		List<MapNodeClass> nodeList = new ArrayList<>();

		placeMountain(minMountainNumber, nodes);
		placeWater(minWaterNumber, nodes);
		placeGrass(nodes);

		Position mountain = getRandomMountain(nodes);
		Position fortPosition = findFortressPosition(mountain.getX(), mountain.getY(), nodes);

		while (fortPosition == null){
			mountain = getRandomMountain(nodes);
			fortPosition = findFortressPosition(mountain.getX(), mountain.getY(), nodes);
		}

		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				Position position = new Position(i, j);
				boolean fortPresent = position.equals(fortPosition);
				EnumTerrain terrain = nodes.get(position);
				logger.debug("The terrain at " + i + " " + j + " is " + terrain);

				nodeList.add(new MapNodeClass(i, j, terrain, fortPresent));
			}
		}

		return new MapClass(nodeList);
	}

}
