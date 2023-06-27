package com.shaunwah.endcubeservicestatus.service;

import com.shaunwah.endcubeservicestatus.model.VelorenAuthServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class VelorenAuthServerService {
    @Autowired
    RedisTemplate<String, Object> template;

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
                    .timeout(Duration.ofMillis(3000))
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public VelorenAuthServer pingAndHandleData() {
        VelorenAuthServer result = this.getObjectFromRedis();
        if (result == null) {
            VelorenAuthServer server = this.ping();
            this.storeObjectInRedis(server);
            return server;
        }
        return result;
    }

    public void storeObjectInRedis(VelorenAuthServer server) {
        template.opsForValue().set(this.getClass().getSimpleName(), server);
    }

    public VelorenAuthServer getObjectFromRedis() {
        if (template.opsForValue().getOperations().hasKey(this.getClass().getSimpleName())) {
            return (VelorenAuthServer) template.opsForValue().get(this.getClass().getSimpleName());
        }
        return null;
    }
}
