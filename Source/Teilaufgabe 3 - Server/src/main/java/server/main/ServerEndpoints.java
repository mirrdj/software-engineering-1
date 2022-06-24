package server.main;

import javax.servlet.http.HttpServletResponse;

import MessagesBase.MessagesFromClient.HalfMap;
import MessagesBase.MessagesFromServer.GameState;
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
import server.exceptions.GenericExampleException;
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

		for(IRule eachRule : rules)
			eachRule.validateRegisterPlayer(gameID, playerRegistration);

		PlayerInformation playerInformation = converter.convertPlayerRegistration(playerRegistration);

		PlayerIDGenerator generator = new PlayerIDGenerator();
		PlayerID playerID = generator.generateID();

		GameID convertedGameID = converter.convertUniqueGameIdentifier(gameID);

		gameManager.addPlayerToGame(playerID, playerInformation, convertedGameID);

		UniquePlayerIdentifier playerIdentifier = converter.convertPlayerID(playerID);
		ResponseEnvelope<UniquePlayerIdentifier> playerIDMessage = new ResponseEnvelope<>(playerIdentifier);
		return playerIDMessage;
	}



	@RequestMapping(value = "/{gameID}/halfmaps", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<HalfMap> sendHalfMap(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody HalfMap halfMap) {

		for(IRule eachRule : rules)
			eachRule.validateHalfMap(gameID, halfMap);

		MapClass map = converter.convertHalfMap(halfMap);
		GameID convertedGameID = converter.convertUniqueGameIdentifier(gameID);
		String playerID = halfMap.getUniquePlayerID();


		gameManager.addHalfMapToGame(map, playerID, convertedGameID);
		ResponseEnvelope<HalfMap> response = new ResponseEnvelope();

		return response;
	}

	@RequestMapping(value = "{gameID}/states/{playerID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<GameState> getState(
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


	@ExceptionHandler({ GenericExampleException.class })
	public @ResponseBody ResponseEnvelope<?> handleException(GenericExampleException ex, HttpServletResponse response) {
		ResponseEnvelope<?> result = new ResponseEnvelope<>(ex.getErrorName(), ex.getMessage());

		response.setStatus(HttpServletResponse.SC_OK);
		return result;
	}
}
