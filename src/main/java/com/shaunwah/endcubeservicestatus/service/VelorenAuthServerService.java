package com.shaunwah.endcubeservicestatus.service;

import com.shaunwah.endcubeservicestatus.model.VelorenAuthServer;
import com.shaunwah.endcubeservicestatus.repository.VelorenAuthServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class VelorenAuthServerService {
    @Autowired
    VelorenAuthServerRepository repository;

    @Value("${endcube.service.veloren.auth.server.url}")
    private String endpointUrl;

    public VelorenAuthServer ping() {
        VelorenAuthServer server = new VelorenAuthServer();
        server.setIsOnline(this.getDataFromEndpoint() != null);
        return server;
    }

    public VelorenAuthServer pingAndStoreData() {
        VelorenAuthServer result = this.getObjectFromRedis();
        if (result == null) {
            VelorenAuthServer server = this.ping();
            this.storeObjectInRedis(server);
            return server;
        }
        return result;
    }

    private String getDataFromEndpoint() {
        try {
            WebClient webClient = WebClient.create(endpointUrl);
            return webClient.get()
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(3000))
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public void storeObjectInRedis(VelorenAuthServer server) {
        repository.storeObjectInRedis(server);
    }

    public VelorenAuthServer getObjectFromRedis() {
        return repository.getObjectFromRedis();
    }
}
