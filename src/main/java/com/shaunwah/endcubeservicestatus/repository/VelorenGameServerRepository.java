package com.shaunwah.endcubeservicestatus.repository;

import com.shaunwah.endcubeservicestatus.model.VelorenGameServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VelorenGameServerRepository {
    @Autowired
    RedisTemplate<String, Object> template;
    String className = this.getClass().getSimpleName();

    public void storeObjectInRedis(VelorenGameServer server) {
        template.opsForValue().set(className, server);
    }

    public VelorenGameServer getObjectFromRedis() {
        Boolean hasKey = template.opsForValue().getOperations().hasKey(className);
        if (hasKey != null && hasKey) {
            return (VelorenGameServer) template.opsForValue().get(className);
        }
        return null;
    }
}
