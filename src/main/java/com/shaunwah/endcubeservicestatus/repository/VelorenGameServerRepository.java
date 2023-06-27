package com.shaunwah.endcubeservicestatus.repository;

import com.shaunwah.endcubeservicestatus.model.VelorenGameServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VelorenGameServerRepository {
    @Autowired
    RedisTemplate<String, Object> template;

    public void storeObjectInRedis(VelorenGameServer server) {
        template.opsForValue().set(this.getClass().getSimpleName(), server);
    }

    public VelorenGameServer getObjectFromRedis() {
        if (template.opsForValue().getOperations().hasKey(this.getClass().getSimpleName())) {
            return (VelorenGameServer) template.opsForValue().get(this.getClass().getSimpleName());
        }
        return null;
    }
}
