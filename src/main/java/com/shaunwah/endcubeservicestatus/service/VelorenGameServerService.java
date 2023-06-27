package com.shaunwah.endcubeservicestatus.service;

import com.shaunwah.endcubeservicestatus.model.VelorenGameServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class VelorenGameServerService {
    @Value("${endcube.service.veloren.game.server.url}")
    private String endpointUrl;

    public VelorenGameServer ping() {
        VelorenGameServer server = new VelorenGameServer();
        String data = this.getDataFromEndpoint();

        if (data == null) {
            return server;
        }

        this.getDataFromEndpoint()
                .lines()
                .forEach(v -> {
                    if (v.startsWith("participants_connected_total")) {
                        server.setParticipantsConnected(Integer.valueOf(v.replace("participants_connected_total ", "")));
                    }

                    if (v.startsWith("participants_disconnected_total")) {
                        server.setParticipantsDisconnected(Integer.valueOf(v.replace("participants_disconnected_total ", "")));
                    }

                    if (v.startsWith("veloren_build_info")) {
                        server.setBuildInfo(v.replaceAll(".+=\"(.{8}).+", "$1"));
                    }
                });
        server.setIsOnline(true);
        return server;
    }

    private String getDataFromEndpoint() {
        try {
            WebClient webClient = WebClient.create(endpointUrl);
            return webClient.get()
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }
}
