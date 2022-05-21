package client.main;


import controller.Controller;
import data.GameStateClass;
import network.NetworkConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.GameStateVisualization;

public class MainClient {
	private static final Logger logger = LoggerFactory.getLogger(MainClient.class);

	public static void main(String[] args)  {
		String firstName = "Miruna-Diana";
		String lastName = "Jarda";
		String uaccount = "jardam99";


		String serverBaseUrl = args[1];
		String gameId = args[2];

		NetworkConverter networkConverter = new NetworkConverter(serverBaseUrl, gameId);

		GameStateClass gameState = new GameStateClass();
		Controller controller = new Controller(networkConverter, gameState);
		GameStateVisualization view = new GameStateVisualization(gameState, controller);

		// Register player
		try {
			controller.registerPlayer(firstName, lastName, uaccount);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		// Send own half of the map
		try{
			controller.sendHalfMap();
		} catch(Exception e){
			e.printStackTrace();
		}


		//Checking if both half maps have been sent
		try{
			logger.info("Checking if both halfs were sent");
			boolean bothHalfMapsSent = false;
			boolean gameEnded = false;
			do {
				bothHalfMapsSent = controller.checkBothHalfMapsSent();
				gameEnded = controller.gameEnded();
			} while(!bothHalfMapsSent && !gameEnded);

			logger.debug("Both sent: " + bothHalfMapsSent);
			logger.debug("Game ended: " + gameEnded);

			if(!gameEnded) {
				logger.info("Both half maps sent");
				controller.setUpAI();
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		// Do stuff, get crazy wOoOoOooooooo
		try {
			boolean gameEnded = false;
			do {
				controller.performAction();
				gameEnded = controller.gameEnded();
			} while (!gameEnded);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
