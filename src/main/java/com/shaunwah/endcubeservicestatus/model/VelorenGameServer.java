package com.shaunwah.endcubeservicestatus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VelorenGameServer implements Serializable {
    private final String name = "Veloren Game Server";
    private Boolean isOnline = false;
    private String buildInfo = "unavailable";
    private Integer participantsConnected = 0;
    private Integer participantsDisconnected = 0;
    private Date timeStamp = new Date();
}
