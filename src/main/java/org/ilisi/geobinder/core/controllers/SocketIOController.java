package org.ilisi.geobinder.core.controllers;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.log4j.Log4j2;
import org.ilisi.geobinder.core.controllers.dtos.PointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SocketIOController {

  @Autowired private SocketIOServer socketIOServer;

  public SocketIOController(SocketIOServer socketIOServer) {
    this.socketIOServer = socketIOServer;

    this.socketIOServer.addConnectListener(onUserConnectListener);
    this.socketIOServer.addDisconnectListener(onUserDisconnectListener);

    this.socketIOServer.addEventListener("pointSent", PointDTO.class, onLocationReceived);
  }

  public ConnectListener onUserConnectListener =
      socketIOClient -> log.info("Perform operation on user connect in controller");

  public DisconnectListener onUserDisconnectListener =
      socketIOClient -> log.info("Perform operation on user disconnect in controller");

  // here we can add our special events and add them to the constructor
  public DataListener<PointDTO> onLocationReceived =
      (client, pointDTO, acknowledge) -> {
        log.info("Received point data from : " + client.getSessionId().toString(), pointDTO);
        this.socketIOServer.getBroadcastOperations().sendEvent("pointSent", client, pointDTO);

        acknowledge.sendAckData("Message sent to all subscribers succefully");
      };
}
