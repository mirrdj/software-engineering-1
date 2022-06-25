package server.main;

import javax.servlet.http.HttpServletResponse;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromClient.PlayerMove;
import MessagesBase.MessagesFromServer.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import MessagesBase.ResponseEnvelope;
import MessagesBase.UniqueGameIdentifier;
import MessagesBase.UniquePlayerIdentifier;
import MessagesBase.MessagesFromClient.PlayerRegistration;
import server.exceptions.*;
import server.game.GameClass;
import server.map.MapClass;
import server.player.PlayerInformation;
import server.rules.*;
import server.uniqueID.GameID;
import server.uniqueID.GameIDGenerator;
import server.game.GameManager;
import server.network.NetworkConverter;
import server.uniqueID.PlayerID;
import server.uniqueID.PlayerIDGenerator;
import java.util.List;

@RestController
@RequestMapping(value = "/games")
public class ServerEndpoints {
	private static Logger logger = LoggerFactory.getLogger(ServerEndpoints.class);
	private GameManager gameManager = new GameManager();
	private NetworkConverter converter = new NetworkConverter();
	private List<IRule> rules = List.of(
			new RGameExists(gameManager),
			new RPlayerExists(gameManager),
			new RMapHasCorrectSize(),
			new RMapHasEnoughTerrainsOfEachType(),
			new RMapHasOneFort()
	);

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody UniqueGameIdentifier newGame(
			@RequestParam(required = false, defaultValue = "false", value = "enableDebugMode") boolean enableDebugMode,
			@RequestParam(required = false, defaultValue = "false", value = "enableDummyCompetition") boolean enableDummyCompetition) {

		GameIDGenerator generator = new GameIDGenerator();
		GameID gameID = generator.generateID();

		gameManager.addGame(gameID);
		return converter.convertGameID(gameID);
	}

	@RequestMapping(value = "/{gameID}/players", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<UniquePlayerIdentifier> registerPlayer(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody PlayerRegistration playerRegistration) {

		try {
			for (IRule eachRule : rules)
				eachRule.validateRegisterPlayer(gameID, playerRegistration);
		} catch (NoSuchGameException e){
			logger.error("Error while checking gameID {} {}", gameID.toString(), e.getMessage());
			throw e;
		}


		PlayerInformation playerInformation = converter.convertPlayerRegistration(playerRegistration);

		PlayerIDGenerator generator = new PlayerIDGenerator();
		PlayerID playerID = generator.generateID();

		GameID convertedGameID = converter.convertUniqueGameIdentifier(gameID);

		gameManager.addPlayerToGame(convertedGameID, playerID, playerInformation);

		UniquePlayerIdentifier playerIdentifier = converter.convertPlayerID(playerID);
		ResponseEnvelope<UniquePlayerIdentifier> playerIDMessage = new ResponseEnvelope<>(playerIdentifier);
		return playerIDMessage;
	}



	@RequestMapping(value = "/{gameID}/halfmaps", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<HalfMap> receiveHalfMap(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody HalfMap halfMap) {

		try {
			for (IRule eachRule : rules)
				eachRule.validateHalfMap(gameID, halfMap);
		} catch (NoSuchGameException e){
			logger.error("Error while checking gameID {} {}", gameID.toString(), e.getMessage());
			throw e;
		} catch (NoSuchPlayerException e){
			logger.error("Error while checking playerID {} {}", halfMap.getUniquePlayerID(), e.getMessage());
			throw e;
		} catch (WrongFieldNumberException e){
			logger.error("Error while checking field numbers {}", e.getMessage());
			gameManager.playerLost(gameID.getUniqueGameID(), halfMap.getUniquePlayerID());
			throw e;
		} catch(WrongFortNumberException e) {
			logger.error("Error while checking fort numbers {}", e.getMessage());
			gameManager.playerLost(gameID.getUniqueGameID(), halfMap.getUniquePlayerID());
			throw e;
		} catch(MapHasWrongSizeException e) {
			logger.error("Error while checking size of the map {}", e.getMessage());
			gameManager.playerLost(gameID.getUniqueGameID(), halfMap.getUniquePlayerID());
			throw e;
		}



		MapClass map = converter.convertHalfMap(halfMap);
		GameID convertedGameID = converter.convertUniqueGameIdentifier(gameID);
		String playerID = halfMap.getUniquePlayerID();

		gameManager.addHalfMapToGame(convertedGameID, playerID, map);
		ResponseEnvelope<HalfMap> response = new ResponseEnvelope();

		return response;
	}

	@RequestMapping(value = "{gameID}/states/{playerID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<GameState> sendState(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @PathVariable UniquePlayerIdentifier playerID) {

		for(IRule eachRule : rules)
			eachRule.validateGetState(gameID, playerID);

		GameID convertedGameID = converter.convertUniqueGameIdentifier(gameID);
		PlayerID convertedPlayerID = converter.convertUniquePlayerIdentifier(playerID);
		GameClass game = gameManager.getGameWithID(convertedGameID.getID()).get();

		GameState gameState = converter.convertGameClass(game, convertedPlayerID.getID());
		ResponseEnvelope<GameState> gameStateMessage = new ResponseEnvelope<>(gameState);
		return gameStateMessage;
	}

	@RequestMapping(value = "/{gameID}/moves", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope receiveMove(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody PlayerMove playerMove) {

		for(IRule eachRule : rules)
			eachRule.validateMove(gameID, playerMove);

		return new ResponseEnvelope();
	}


		@ExceptionHandler({ GenericExampleException.class })
	public @ResponseBody ResponseEnvelope<?> handleException(GenericExampleException ex, HttpServletResponse response) {
		ResponseEnvelope<?> result = new ResponseEnvelope<>(ex.getErrorName(), ex.getMessage());

		response.setStatus(HttpServletResponse.SC_OK);
		return result;
	}
}
