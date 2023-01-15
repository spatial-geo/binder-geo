package org.ilisi.geobinder.core.controllers;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.log4j.Log4j2;
import org.ilisi.geobinder.core.controllers.dtos.PointDTO;
import org.ilisi.geobinder.core.services.IProfilesService;
import org.ilisi.geobinder.core.services.models.GeoCatalogue;
import org.ilisi.geobinder.core.services.models.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SocketIOController {

  @Autowired private SocketIOServer socketIOServer;
  private IProfilesService profilesService;

  public SocketIOController(SocketIOServer socketIOServer, IProfilesService profilesService) {
    this.socketIOServer = socketIOServer;
    this.profilesService = profilesService;

    this.socketIOServer.addConnectListener(onUserConnectListener);
    this.socketIOServer.addDisconnectListener(onUserDisconnectListener);

    this.socketIOServer.addEventListener("pointSent", PointDTO.class, onLocationReceived);
  }

  ConnectListener onUserConnectListener =
      socketIOClient -> {
        log.info(
            "Perform operation on user connect in controller : " + socketIOClient.getSessionId());
        GeoCatalogue.getCatalogueInstance()
            .addGeoSession(socketIOClient.getSessionId().toString(), null);

        System.out.println(GeoCatalogue.getCatalogueInstance());
      };

  DisconnectListener onUserDisconnectListener =
      socketIOClient -> {
        log.info("Perform operation on user disconnect in controller");
        GeoCatalogue.getCatalogueInstance()
            .destroySession(socketIOClient.getSessionId().toString());
      };

  // here we can add our special events and add them to the constructor
  DataListener<PointDTO> onLocationReceived =
      (client, pointDTO, acknowledge) -> {
        log.info("Received point data from : " + client.getSessionId().toString(), pointDTO);

        GeoPoint currentClientPosition = new GeoPoint();
        currentClientPosition.setLon(pointDTO.lon());
        currentClientPosition.setLat(pointDTO.lat());

        GeoCatalogue.getCatalogueInstance()
            .updateCurrentPosition(
                client.getSessionId().toString(), pointDTO.idUser(), currentClientPosition);
        profilesService.insertPoint(pointDTO.lon(), pointDTO.lat(), pointDTO.idUser());

        // TODO: Send point to the connected users in Catalogue by session ID and with running the
        // ST_INTERSECTS function
        this.socketIOServer.getBroadcastOperations().sendEvent("pointSent", client, pointDTO);

        acknowledge.sendAckData("Message sent to all subscribers succefully");
      };
}
