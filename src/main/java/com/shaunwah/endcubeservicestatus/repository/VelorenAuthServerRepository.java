package com.shaunwah.endcubeservicestatus.repository;

import com.shaunwah.endcubeservicestatus.model.VelorenAuthServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VelorenAuthServerRepository {
    @Autowired
    RedisTemplate<String, Object> template;

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
