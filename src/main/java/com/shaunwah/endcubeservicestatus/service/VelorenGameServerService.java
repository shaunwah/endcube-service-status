package com.shaunwah.endcubeservicestatus.service;

import com.shaunwah.endcubeservicestatus.model.VelorenGameServer;
import com.shaunwah.endcubeservicestatus.repository.VelorenGameServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class VelorenGameServerService {
    @Autowired
    VelorenGameServerRepository repository;

    @Value("${endcube.service.veloren.game.server.url}")
    private String endpointUrl;

    public VelorenGameServer ping() {
        VelorenGameServer server = new VelorenGameServer();
        String data = this.getDataFromEndpoint();

        if (data == null) {
            return server;
        }

        AtomicReference<Integer> participantsConnected = new AtomicReference<>(0);
        AtomicReference<Integer> participantsDisconnected = new AtomicReference<>(0);

        this.getDataFromEndpoint()
                .lines()
                .forEach(v -> {
                    if (v.startsWith("participants_connected_total")) {
                        participantsConnected.set(Integer.valueOf(v.replace("participants_connected_total ", "")));
                    }

                    if (v.startsWith("participants_disconnected_total")) {
                        participantsDisconnected.set(Integer.valueOf(v.replace("participants_disconnected_total ", "")));
                    }

                    if (v.startsWith("veloren_build_info")) {
                        server.setBuildInfo(v.replaceAll(".+=\"(.{8}).+", "$1"));
                    }
                });
        server.setUsersOnline(participantsConnected.get() - participantsDisconnected.get());
        server.setIsOnline(true);
        return server;
    }

    public VelorenGameServer pingAndStoreData() {
        VelorenGameServer result = this.getObjectFromRedis();
        if (result == null) {
            VelorenGameServer server = this.ping();
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

    public void storeObjectInRedis(VelorenGameServer server) {
        repository.storeObjectInRedis(server);
    }

    public VelorenGameServer getObjectFromRedis() {
        return repository.getObjectFromRedis();
    }
}
