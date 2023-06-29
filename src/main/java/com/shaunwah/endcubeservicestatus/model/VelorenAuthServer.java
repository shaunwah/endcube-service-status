package com.shaunwah.endcubeservicestatus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VelorenAuthServer implements Serializable {
    private final String name = "Veloren Auth Server";
    private Boolean isOnline = false;
    private Long timeStamp = Instant.now().getEpochSecond();
}
