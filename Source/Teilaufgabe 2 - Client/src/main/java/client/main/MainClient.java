package client.main;


import controller.Controller;
import data.GameStateClass;
import exceptions.NetworkCommunicationArgumentException;
import network.NetworkConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.GameStateVisualization;

public class MainClient {
	private static final Logger logger = LoggerFactory.getLogger(MainClient.class);

	public static void main(String[] args)  {
		String serverBaseUrl = args[1];
		String gameId = args[2];

		try {
			NetworkConverter networkConverter = new NetworkConverter(serverBaseUrl, gameId);

			GameStateClass gameState = new GameStateClass();
			Controller controller = new Controller(networkConverter, gameState);
			GameStateVisualization view = new GameStateVisualization(gameState, controller);

			// Register player
			controller.registerPlayer();

			// Send own half of the map
			controller.sendHalfMap();

			//Checking if both half maps have been sent
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

			controller.setUpAI();
			controller.performAction();

		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
