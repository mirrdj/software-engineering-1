package map.half_map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import exceptions.InsufficientNumberOfGrassFieldsException;
import exceptions.InsufficientNumberOfMountainFieldsException;
import exceptions.InsufficientNumberOfWaterFieldsException;
import map.EnumTerrain;
import map.Position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HalfMapGenerator {
	private static final Logger logger = LoggerFactory.getLogger(HalfMapGenerator.class);
	private HashMap<Position, EnumTerrain> nodes = new HashMap<>();
	private Position fortPosition;
	private int minWaterNumber = 4;
	private int minMountainNumber = 3;

	public HalfMapGenerator() {}

//	TODO: island created
	public HalfMapGenerator(int minWaterNumber, int minMountainNumber) {
		if(minWaterNumber < 4)
			throw new InsufficientNumberOfWaterFieldsException("Not enough water fields");
		if(minMountainNumber < 3)
			throw new InsufficientNumberOfMountainFieldsException("Not enough mountain fields");
		if(minWaterNumber + minMountainNumber > 32 - 15)
			throw new InsufficientNumberOfGrassFieldsException("Not enough grass fields");

		this.minWaterNumber = minWaterNumber;
		this.minMountainNumber = minMountainNumber;
	}

	private void placeFortress(int x, int y) {
		logger.warn("The fortress tried to be place on" + x + " " + y);

		if(x > 0 && nodes.get(new Position(x - 1, y)) == EnumTerrain.GRASS)
			fortPosition = new Position(x - 1, y);

		else if(x < 7 && nodes.get(new Position(x + 1, y)) == EnumTerrain.GRASS)
			fortPosition = new Position(x + 1, y);

		else if(y > 0 && nodes.get(new Position(x, y - 1)) == EnumTerrain.GRASS)
			fortPosition = new Position(x, y - 1);

		else if(y < 2 && nodes.get(new Position(x, y + 1)) == EnumTerrain.GRASS)
			fortPosition = new Position(x, y + 1);

		else if(x < 7)
			placeFortress(x + 1, y);
		
		else if(y > 0)
			placeFortress(x, y - 1);
		
		else if(x > 0)
			placeFortress(x - 1, y);
		
		else if(y < 2)
			placeFortress(x, y + 1);

		logger.info("The fortress was actually placed at" + x + " " + y);
	}

	private void placeMountain(int mountainNumberNeeded) {
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

	private void placeWater(int waterNumberNeeded) {
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

	private void placeGrass(){
		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				Position position = new Position(i, j);
				nodes.putIfAbsent(position, EnumTerrain.GRASS);
			}
		}
	}

	private List<Position> getMountainPosition () {
		return nodes
				.entrySet()
				.stream()
				.filter(e -> e.getValue().equals(EnumTerrain.MOUNTAIN))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	private Position getRandomMountain() {
		List<Position> positionList = getMountainPosition();
		logger.debug("Mountains are at " + positionList);
		int randomIndex = (int) (Math.random() * positionList.size());
		logger.debug("Random position is " + randomIndex);
		return positionList.get(randomIndex);
	}


	public HalfMapClass generateHalfMap() throws Exception{
		List<HalfMapNodeClass> nodeList = new ArrayList<>();

		placeMountain(minMountainNumber);
		placeWater(minWaterNumber);
		placeGrass();
		Position mountain = getRandomMountain();
		placeFortress(mountain.getX(), mountain.getY());

		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				Position position = new Position(i, j);
				boolean fortPresent = position.equals(fortPosition);
				EnumTerrain terrain = nodes.get(position);
				logger.debug("The terrain at " + i + " " + j + " is " + terrain);

				nodeList.add(new HalfMapNodeClass(i, j, fortPresent, terrain));
			}
		}

		return new HalfMapClass(nodeList);
	}

}
