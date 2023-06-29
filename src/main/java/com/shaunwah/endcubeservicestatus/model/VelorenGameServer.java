package com.shaunwah.endcubeservicestatus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VelorenGameServer implements Serializable {
    private final String name = "Veloren Game Server";
    private Boolean isOnline = false;
    private String buildInfo = "unavailable";
    private Integer usersOnline = 0;
    private Long timeStamp = Instant.now().getEpochSecond();
}
