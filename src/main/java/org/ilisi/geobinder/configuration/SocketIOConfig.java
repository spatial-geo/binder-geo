package org.ilisi.geobinder.configuration;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Component
@Log4j2
public class SocketIOConfig {

  //    @Value("${socket.host}")
  private String SOCKETHOST = "0.0.0.0";

  //    @Value("${socket.port}")
  private int SOCKETPORT = 8878;

  private SocketIOServer server;

  @Bean
  public SocketIOServer socketIOServer() {
    Configuration configuration = new Configuration();
    configuration.setHostname(SOCKETHOST);
    configuration.setPort(SOCKETPORT);

    server = new SocketIOServer(configuration);
    server.start();
    server.addConnectListener(
        new ConnectListener() {
          @Override
          public void onConnect(SocketIOClient socketIOClient) {
            log.info("new user connected with socket " + socketIOClient.getSessionId());
          }
        });

    server.addDisconnectListener(
        new DisconnectListener() {
          @Override
          public void onDisconnect(SocketIOClient socketIOClient) {
            socketIOClient.getNamespace().getAllClients().stream()
                .forEach(
                    data -> {
                      log.info("user disconnected " + data.getSessionId().toString());
                    });
          }
        });

    return server;
  }

  @PreDestroy
  public void stopSocketIOServer() {
    this.server.stop();
  }
}
