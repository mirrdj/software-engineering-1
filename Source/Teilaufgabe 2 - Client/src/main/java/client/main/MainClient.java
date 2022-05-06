package client.main;


import controller.Controller;
import data.GameStateClass;
import network.NetworkConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.GameStateVisualization;

public class MainClient {
	private static final Logger logger = LoggerFactory.getLogger(MainClient.class);

	public static void main(String[] args) {
		String serverBaseUrl = args[1];
		String gameId = args[2];

		NetworkConverter networkConverter = new NetworkConverter(serverBaseUrl, gameId);

		GameStateClass gameState = new GameStateClass();
		Controller controller= new Controller(networkConverter, gameState);
		GameStateVisualization view = new GameStateVisualization(gameState, controller);

		// Register player
		try {
			controller.registerPlayer();
			Thread.sleep(400);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		// Checking if both players have registered
//		try{
//			logger.info("Checking if both players have registered");
//			boolean bothPlayersRegistered;
//			do {
//				bothPlayersRegistered = controller.bothPlayersRegistered();
//				Thread.sleep(400);
//			} while(!bothPlayersRegistered);
//
//			logger.info("Both players have registered - registration ended");
//
//		} catch (Exception e){
//			e.printStackTrace();
//		}

		// Send own half of the map
		try{
			controller.sendHalfMap();
//			Thread.sleep(400);
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
				Thread.sleep(400);
			} while(!bothHalfMapsSent && !gameEnded);

			logger.debug("Both sent: " + bothHalfMapsSent);
			logger.debug("Game ended: " + gameEnded);

			if(!gameEnded) {
				logger.info("Both half maps sent");
				controller.setUpAI();
				controller.displayInformation();
			}
		} catch (Exception e){
			e.printStackTrace();
		}

//
////		// Find and collect treasure
//		try{
//			boolean treasureFound;
//			boolean gameEnded;
//			do {
//				treasureFound = controller.findTreasure();
//				gameEnded = controller.gameEnded();
//				Thread.sleep(400);
//				view.handleUserButtonPress();
//				Thread.sleep(400);
//			}
//			while (!treasureFound && !gameEnded);
//			if(!gameEnded)
//				logger.info("Treasure found");
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//
//
//		// Find and go to enemy fortress
//		try{
//			boolean fortressFound;
//			boolean gameEnded;
//			do{
//				fortressFound = controller.findEnemyFortress();
//				gameEnded = controller.gameEnded();
//				Thread.sleep(400);
//				view.handleUserButtonPress();
//				Thread.sleep(400);
//			}
//			while (!fortressFound && !gameEnded);
//			if(controller.playerWon())
//				logger.info("Fortress found; Player won");
//			else if(controller.playerLost())
//				logger.info("Player lost");
//		} catch (Exception e){
//			e.printStackTrace();
//		}






	}
}
