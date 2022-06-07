package server.main;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

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
import server.uniqueID.GameID;
import server.uniqueID.GameIDGenerator;
import server.game.GameManager;
import server.network.NetworkConverter;
import server.uniqueID.PlayerID;
import server.uniqueID.PlayerIDGenerator;

@RestController
@RequestMapping(value = "/games")
public class ServerEndpoints {
	GameManager gameManager = new GameManager();
	NetworkConverter converter = new NetworkConverter();

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody UniqueGameIdentifier newGame(
			@RequestParam(required = false, defaultValue = "false", value = "enableDebugMode") boolean enableDebugMode,
			@RequestParam(required = false, defaultValue = "false", value = "enableDummyCompetition") boolean enableDummyCompetition) {

		GameIDGenerator generator = new GameIDGenerator();
		GameID gameID = generator.generateID();

		gameManager.addGame(gameID);
		return converter.convertGameID(gameID);
	}

	// example for a POST endpoint based on games/{gameID}/players
	@RequestMapping(value = "/{gameID}/players", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<UniquePlayerIdentifier> registerPlayer(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody PlayerRegistration playerRegistration) {

		PlayerIDGenerator generator = new PlayerIDGenerator();
		PlayerID playerID = generator.generateID();
		GameID ownGameID = converter.convertUniqueGameIdentifier(gameID);

		gameManager.addPlayerToGame(playerID, ownGameID);

		UniquePlayerIdentifier playerIdentifier = converter.convertPlayerID(playerID);
		ResponseEnvelope<UniquePlayerIdentifier> playerIDMessage = new ResponseEnvelope<>(playerIdentifier);
		return playerIDMessage;
	}

	/*
	 * Note, this is only the most basic way of handling exceptions in spring (but
	 * sufficient for our task) it would, for example struggle if you use multiple
	 * controllers. Add the exception types to the @ExceptionHandler which your
	 * exception handling should support the superclass catches subclasses aspect
	 * of try/catch also applies here. Hence, we recommend to simply extend your own
	 * Exceptions from the GenericExampleException. For larger projects, one would
	 * most likely want to use the HandlerExceptionResolver; see here
	 * https://www.baeldung.com/exception-handling-for-rest-with-spring
	 * 
	 * Ask yourself: Why is handling the exceptions in a different method than the
	 * endpoint methods a good solution?
	 */
	@ExceptionHandler({ GenericExampleException.class })
	public @ResponseBody ResponseEnvelope<?> handleException(GenericExampleException ex, HttpServletResponse response) {
		ResponseEnvelope<?> result = new ResponseEnvelope<>(ex.getErrorName(), ex.getMessage());

		// reply with 200 OK as defined in the network documentation
		// Side note: We only do this here for simplicity reasons. For future projects,
		// you should check out HTTP status codes and
		// what they can be used for. Note, the WebClient used during the Client implementation can react
		// to them using the .onStatus(...) method.
		response.setStatus(HttpServletResponse.SC_OK);
		return result;
	}
}
