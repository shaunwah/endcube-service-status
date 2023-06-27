package com.shaunwah.endcubeservicestatus.service;

import com.shaunwah.endcubeservicestatus.model.VelorenAuthServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class VelorenAuthServerService {
    @Value("${endcube.service.veloren.auth.server.url}")
    private String endpointUrl;

    public VelorenAuthServer ping() {
        VelorenAuthServer server = new VelorenAuthServer();
        server.setIsOnline(this.getDataFromEndpoint() != null);
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
