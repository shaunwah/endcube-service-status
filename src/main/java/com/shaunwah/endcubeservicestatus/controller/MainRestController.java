package com.shaunwah.endcubeservicestatus.controller;

import com.shaunwah.endcubeservicestatus.model.VelorenAuthServer;
import com.shaunwah.endcubeservicestatus.model.VelorenGameServer;
import com.shaunwah.endcubeservicestatus.service.VelorenAuthServerService;
import com.shaunwah.endcubeservicestatus.service.VelorenGameServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainRestController {
    @Autowired
    VelorenGameServerService velorenGameServerService;
    @Autowired
    VelorenAuthServerService velorenAuthServerService;

    @GetMapping("/veloren/game-server")
    public ResponseEntity<VelorenGameServer> getVelorenGameServer() {
        VelorenGameServer velorenGameServer = velorenGameServerService.pingAndHandleData();
        if (velorenGameServer.getIsOnline()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(velorenGameServer);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new VelorenGameServer());
    }

    @GetMapping("/veloren/auth-server")
    public ResponseEntity<VelorenAuthServer> getVelorenAuthServer() {
        VelorenAuthServer velorenAuthServer = velorenAuthServerService.pingAndHandleData();
        if (velorenAuthServer.getIsOnline()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(velorenAuthServer);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new VelorenAuthServer());
    }
}
