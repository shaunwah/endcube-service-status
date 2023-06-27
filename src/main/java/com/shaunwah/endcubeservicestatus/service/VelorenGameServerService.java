package com.shaunwah.endcubeservicestatus.service;

import com.shaunwah.endcubeservicestatus.model.VelorenGameServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class VelorenGameServerService {
    @Autowired
    RedisTemplate<String, Object> template;

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
                    .timeout(Duration.ofMillis(3000))
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public VelorenGameServer pingAndHandleData() {
        VelorenGameServer result = this.getObjectFromRedis();
        if (result == null) {
            VelorenGameServer server = this.ping();
            this.storeObjectInRedis(server);
            return server;
        }
        return result;
    }

    public void storeObjectInRedis(VelorenGameServer server) {
        template.opsForValue().set(this.getClass().getSimpleName(), server, Duration.ofMinutes(5));
    }

    public VelorenGameServer getObjectFromRedis() {
        if (template.opsForValue().getOperations().hasKey(this.getClass().getSimpleName())) {
            return (VelorenGameServer) template.opsForValue().get(this.getClass().getSimpleName());
        }
        return null;
    }
}
