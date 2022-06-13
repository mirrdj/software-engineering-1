package server.main;

import javax.servlet.http.HttpServletResponse;

import MessagesBase.MessagesFromClient.HalfMap;
import org.springframework.beans.factory.annotation.Autowired;
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
import server.map.MapClass;
import server.player.Player;
import server.rules.IRule;
import server.rules.RGameExists;
import server.rules.RPlayerExists;
import server.uniqueID.GameID;
import server.uniqueID.GameIDGenerator;
import server.game.GameManager;
import server.network.NetworkConverter;
import server.uniqueID.PlayerID;
import server.uniqueID.PlayerIDGenerator;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/games")
public class ServerEndpoints {
	private GameManager gameManager = new GameManager();
	private NetworkConverter converter = new NetworkConverter();
	private List<IRule> rules = List.of(
			new RGameExists(gameManager),
			new RPlayerExists(gameManager)
	);

//	@Autowired
//	public ServerEndpoints(GameManager gameManager) {
//		this.gameManager = gameManager;
//
//		rules = List.of(new RGameExists(gameManager));
//	}

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

		PlayerIDGenerator generator = new PlayerIDGenerator();
		PlayerID playerID = generator.generateID();
		GameID ownGameID = converter.convertUniqueGameIdentifier(gameID);

		for(IRule rule : rules)
			rule.validateRegisterPlayer(ownGameID);

		gameManager.addPlayerToGame(playerID, ownGameID);

		UniquePlayerIdentifier playerIdentifier = converter.convertPlayerID(playerID);
		ResponseEnvelope<UniquePlayerIdentifier> playerIDMessage = new ResponseEnvelope<>(playerIdentifier);
		return playerIDMessage;
	}



	@RequestMapping(value = "/{gameID}/halfmaps", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<HalfMap> sendHalfMap(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody HalfMap halfMap) {

		MapClass map = converter.convertHalfMap(halfMap);
		GameID convertedGameID = converter.convertUniqueGameIdentifier(gameID);
		String playerID = halfMap.getUniquePlayerID();

		for(IRule rule : rules)
			rule.validateHalfMap(convertedGameID, map, playerID);

		gameManager.addHalfMapToGame(map, playerID, convertedGameID);
		ResponseEnvelope<HalfMap> response = new ResponseEnvelope();

		return response;
	}


	@ExceptionHandler({ GenericExampleException.class })
	public @ResponseBody ResponseEnvelope<?> handleException(GenericExampleException ex, HttpServletResponse response) {
		ResponseEnvelope<?> result = new ResponseEnvelope<>(ex.getErrorName(), ex.getMessage());

		response.setStatus(HttpServletResponse.SC_OK);
		return result;
	}
}
