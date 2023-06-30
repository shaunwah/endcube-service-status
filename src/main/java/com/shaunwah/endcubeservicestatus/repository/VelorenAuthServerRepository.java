package com.shaunwah.endcubeservicestatus.repository;

import com.shaunwah.endcubeservicestatus.model.VelorenAuthServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VelorenAuthServerRepository {
    @Autowired
    RedisTemplate<String, Object> template;
    String className = this.getClass().getSimpleName();

    public void storeObjectInRedis(VelorenAuthServer server) {
        template.opsForValue().set(className, server);
    }

    public VelorenAuthServer getObjectFromRedis() {
        Boolean hasKey = template.opsForValue().getOperations().hasKey(className);
        if (hasKey != null && hasKey) {
            return (VelorenAuthServer) template.opsForValue().get(className);
        }
        return null;
    }
}
