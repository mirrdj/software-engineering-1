package network;

import MessagesBase.MessagesFromClient.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import MessagesBase.ResponseEnvelope;
import MessagesBase.UniquePlayerIdentifier;
import MessagesBase.MessagesFromServer.GameState;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NetworkMessenger {
    private static final Logger logger = LoggerFactory.getLogger(NetworkMessenger.class);
    private final WebClient baseWebClient;
    private final String gameID;


    public NetworkMessenger(String serverBaseUrl, String gameId) {
        this.gameID = gameId;
        this.baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE) // the network protocol uses
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();
    }

    public String postPlayerRegistration() throws Exception {
        String firstName = "Miruna-Diana";
        String lastName = "Jarda";
        String uaccount = "jardam99";

        logger.info("Registering the player");
        PlayerRegistration playerRegistration = new PlayerRegistration(firstName, lastName, uaccount);
        Mono<ResponseEnvelope> webAccess = this.baseWebClient.method(HttpMethod.POST)
                .uri("/" + this.gameID + "/players")
                .body(BodyInserters.fromValue(playerRegistration))
                .retrieve()
                .bodyToMono(ResponseEnvelope.class);

        ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

        if(resultReg.getState() == ERequestState.Error) {
            logger.error("Client error, errormessage: " + resultReg.getExceptionMessage());
        }
        else {
            UniquePlayerIdentifier uniqueID = resultReg.getData().get();
            logger.info("My Player ID: " + uniqueID.getUniquePlayerID());

            return uniqueID.getUniquePlayerID();
        }

        return null;
    }

    public void postHalfMap(HalfMap halfMap) throws Exception {
        Mono<ResponseEnvelope> webAccess = this.baseWebClient.method(HttpMethod.POST)
                .uri("/" + this.gameID + "/halfmaps")
                .body(BodyInserters.fromValue(halfMap))
                .retrieve()
                .bodyToMono(ResponseEnvelope.class);


        ResponseEnvelope<HalfMap> resultReg = webAccess.block();

        assert resultReg != null;
        if(resultReg.getState() == ERequestState.Error) {
            logger.error("Client error, errormessage: " + resultReg.getExceptionMessage());
        }
    }

    public void postMove(String uniquePlayerID, EMove move) throws Exception {
        Mono<ResponseEnvelope> webAccess = this.baseWebClient.method(HttpMethod.POST)
                .uri("/" + this.gameID + "/moves")
                .header("accept = application/xml")
                .body(BodyInserters.fromValue(PlayerMove.of(uniquePlayerID, move)))
                .retrieve()
                .bodyToMono(ResponseEnvelope.class);

        ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

        assert resultReg != null;
        if(resultReg.getState() == ERequestState.Error) {
            logger.error("Client error, errormessage: " + resultReg.getExceptionMessage());
        }
    }


    public GameState getGameState(String uniquePlayerID) throws Exception	{
        Mono<ResponseEnvelope> webAccess = this.baseWebClient.method(HttpMethod.GET)
                .uri("/" + this.gameID + "/states/" + uniquePlayerID)
                .header("accept = application/xml")
                .retrieve()
                .bodyToMono(ResponseEnvelope.class);

        ResponseEnvelope<GameState> requestResult = webAccess.block();

        assert requestResult != null;
        if(requestResult.getState() == ERequestState.Error) {
            logger.error("Client error, errormessage: " + requestResult.getExceptionMessage());
            return null;
        }
        else {
            return requestResult.getData().orElse(null);
        }
    }

}
