package com.shaunwah.endcubeservicestatus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VelorenGameServer {
    private Boolean isOnline = false;
    private String buildInfo = "unavailable";
    private Integer participantsConnected = 0;
    private Integer participantsDisconnected = 0;
}
