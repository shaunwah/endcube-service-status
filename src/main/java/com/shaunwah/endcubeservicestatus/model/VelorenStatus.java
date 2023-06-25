package com.shaunwah.endcubeservicestatus.model;

import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

@Data
public class VelorenStatus {
    private final String METRICS_ENDPOINT = "http://veloren.endcube.net:14005/metrics";
    private String velorenBuildInfo;
    private Integer clientsConnected = 0;
    private Integer clientsDisconnected = 0;

    public VelorenStatus() throws Exception {
        URL url = URI.create(METRICS_ENDPOINT).toURL();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        url.openConnection().getInputStream()
                )
        );

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith("clients_connected")) {
                this.clientsConnected = Integer.parseInt(line.replace("clients_connected ", ""));
            }

            if (line.startsWith("clients_disconnected")) {
                this.clientsDisconnected += Integer.parseInt(line.replaceAll("clients_disconnected\\{reason=.+} ", ""));
            }

            if (line.startsWith("veloren_build_info")) {
                this.velorenBuildInfo = line.replace("veloren_build_info ", "");
            }
        }
    }
}
